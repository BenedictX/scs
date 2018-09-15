queryStudent = function (location) {
    if (!checkQueryForm()) {
        return false;
    }

    var queryStuNo = document.getElementById("queryStuNo").value;
    var queryStuName = document.getElementById("queryStuName").value;
    var queryAge = document.getElementById("queryAge").value;
    var queryMale = document.getElementById("queryMale");
    var queryFemale = document.getElementById("queryFemale");
    var startDate = document.getElementById("dateStart").value.trim();
    var endDate = document.getElementById("dateEnd").value.trim();
    var pageSize = document.getElementById("pageSize").value;
    var currentPageNumber = document.getElementById("currentPageNumber").innerText;
    var querySex = 0;
    if (queryMale.checked == true) {
        querySex = 1;
    }

    if (queryFemale.checked == true) {
        querySex = 2;
    }
    var student = {stuNo: queryStuNo, stuName: queryStuName, sex: querySex, age: queryAge};
    var queryStudent = {student: student, startDate: startDate, endDate: endDate};
    console.log(queryStudent);
    if (location == "0") {
        currentPageNumber = 1;
    }
    $.ajax({
        url: "/queryStudent/" + currentPageNumber + "/" + pageSize,
        type: "POST",
        data: JSON.stringify(queryStudent),
        contentType: "application/json;charset = utf-8",
        async: true,
        error: function () {
            alert("Query Failed !");
        },
        success: function (result) {
            var pageBeanAsync = result.pageBeanAsync;
            var students = result.pageBeanAsync.datas;
            var pageSize = result.pageBeanAsync.pageSize;
            var currentPageNumber = result.pageBeanAsync.currentPageNumber;
            var str = "";
            var gender;
            if (students.length == 0) {
                str = str = "<td colspan=\"3\" align=\'" + "center" + "\'>" + "No Query Results !" + "</td>"
                $("#tbody").html(str);
                removeChilds();
                show();
                document.getElementById("pageDiv").style.display = "none";
            } else {
                for (var i = 0; i < students.length; i++) {
                    if (students[i].sex == "1") {
                        gender = "男";
                    } else if (students[i].sex == "2") {
                        gender = "女";
                    }
                    str += "<tr>" +
                        "<td align=\'" + "center" + "\'>" + students[i].dataId + "</td>" +
                        "<td align=\'" + "center" + "\'>" + students[i].stuNo + "</td>" +
                        "<td align=\'" + "center" + "\'>" + students[i].stuName + "</td>" +
                        "<td align=\'" + "center" + "\'>" + gender + "</td>" +
                        "<td align=\'" + "center" + "\'>" + students[i].age + "</td>" +
                        "<td align=\'" + "center" + "\'>" + students[i].createDate + "</td>" +
                        "<td>" +
                        "  <button onclick=\"" + "proDelete(\'" + currentPageNumber + "\'" + "," + students[i].dataId + ")" + "\">" + "delete" + "</button>" +
                        "</td>" +
                        "<td>" +
                        "  <button onclick=\"" + "studentDetail(\'" + currentPageNumber + "\'" + "," + students[i].dataId + ")" + "\">" + "Change" + "</button>" +
                        "</td>" +
                        "</tr>";
                    $("#tbody").html(str);
                }
                pageToolBar(pageBeanAsync);
            }
        }
    });
    show();
}
show = function () {
    document.getElementById("resultOfQuery").style.display = "block";
    document.getElementById("showAll").style.display = "block";
}
checkQueryForm = function () {

    var stuNoTips = checkQueryStuNo();
    var stuNameTips = checkQueryStuName();
    var ageTips = checkQueryAge();
    var startDateTips = checkQueryDateStart();
    var endDateTips = checkQueryDateEnd();
    console.log(stuNoTips, stuNameTips, ageTips, startDateTips, endDateTips)
    if (stuNoTips && stuNameTips && ageTips && startDateTips && endDateTips) {
        return true;
    } else {
        alert("check fail !");
        return false;
    }
}
checkQueryStuNo = function () {
    var queryStuNo = document.getElementById("queryStuNo").value;
    var queryStuNoError = document.getElementById("queryStuNoError");
    var pattern = /^([0-9]*)$/;
    if (!pattern.test(queryStuNo)) {
        queryStuNoError.innerHTML = "QueryStuNo Must Be Number !";
        return false;
    } else if (queryStuNo.length > 10) {
        queryStuNoError.innerHTML = "Length of stuNo Should Not Greater Than 10 ! ";
        return false;
    } else {
        queryStuNoError.innerHTML = "";
        return true;
    }
}
checkQueryStuName = function () {
    var queryStuName = document.getElementById("queryStuName").value;
    var queryStuNameError = document.getElementById("queryStuNameError");
    var pattern = /^([A-Za-z]*|[\u4E00-\u9FA5]*)$/;
    if (queryStuName == "" || queryStuName == null) {
        queryStuNameError.innerHTML = "";
        return true;
    } else if (!pattern.test(queryStuName)) {
        queryStuNameError.innerHTML = "StuName Must Be English letters Or Chinese !";
        return false;
    } else {
        queryStuNameError.innerHTML = "";
        return true;

    }
}
checkQueryAge = function () {
    var queryAge = document.getElementById("queryAge").value;
    var queryAgeError = document.getElementById("queryAgeError");
    var pattern = /^([1-9]*\d|1|100)$/;
    if (queryAge == "" || queryAge == null) {
        queryAgeError.innerHTML = "";
        return true;
    } else if (!pattern.test(queryAge)) {
        queryAgeError.innerHTML = "Age Must Be Number !";
        return false;
    } else {
        queryAgeError.innerHTML = "";
        return true;
    }

}
checkQueryDateStart = function () {
    var startDate = document.getElementById("dateStart").value.trim();
    var endDate = document.getElementById("dateEnd").value.trim();
    var startDateError = document.getElementById("startDateError");
    var today = getCurrentDate(new Date());
    if (!compareDate(startDate, today)) {
        startDateError.innerHTML = "StartDate Great than Today !";
        return false;
    } else {
        if (startDate != "" && endDate != "") {
            if (!compareDate(startDate, endDate)) {
                startDateError.innerHTML = "StartDate Great than EndDate !";
                return false;
            } else {
                startDateError.innerHTML = "";
                return true;
            }
            return true;
        }
        return true;
    }
}
checkQueryDateEnd = function () {
    var startDate = document.getElementById("dateStart").value.trim();
    var endDate = document.getElementById("dateEnd").value.trim();
    var endDateError = document.getElementById("endDateError");
    var today = getCurrentDate(new Date());
    if (!compareDate(endDate, today)) {
        endDateError.innerHTML = "endDate Great than Today !";
        return false;
    } else {
        if (startDate != "" && endDate != "") {
            if (!compareDate(startDate, endDate)) {
                endDateError.innerHTML = "EndDate less than  StartDate!";
                return false;
            } else {
                endDateError.innerHTML = "";
                return true;
            }
            return true;
        }
        return true;
    }
}
getCurrentDate = function (date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate() + 1;
    var newDate = year + "-" + month + "-" + day;
    return newDate
}
compareDate = function (date1, data2) {
    var arrayDate1 = date1.split("-");
    var date11 = new Date(arrayDate1[0], arrayDate1[1], arrayDate1[2]);
    var date1Times = date11.getTime();
    var arrayDate2 = data2.split("-");
    var date22 = new Date(arrayDate2[0], arrayDate2[1], arrayDate2[2]);
    var date2Times = date22.getTime();
    if (date1Times > date2Times) {
        return false;
    } else {
        return true;
    }

}
queryStudentAsync = function (currentPageNumber, pageSize) {
    if (!checkQueryForm()) {
        return false;
    }

    var queryStuNo = document.getElementById("queryStuNo").value;
    var queryStuName = document.getElementById("queryStuName").value;
    var queryAge = document.getElementById("queryAge").value;
    var queryMale = document.getElementById("queryMale");
    var queryFemale = document.getElementById("queryFemale");
    var startDate = document.getElementById("dateStart").value.trim();
    var endDate = document.getElementById("dateEnd").value.trim();
    var querySex = 0;
    if (queryMale.checked == true) {
        querySex = 1;
    }

    if (queryFemale.checked == true) {
        querySex = 2;
    }
    var student = {stuNo: queryStuNo, stuName: queryStuName, sex: querySex, age: queryAge};
    var queryStudent = {student: student, startDate: startDate, endDate: endDate};
    $.ajax({
        url: "/queryStudent/" + currentPageNumber + "/" + pageSize,
        type: "POST",
        data: JSON.stringify(queryStudent),
        contentType: "application/json;charset = utf-8",
        async: true,
        error: function () {
            alert("Query Failed !");
        },
        success: function (result) {
            var pageBeanAsync = result.pageBeanAsync;
            var students = result.pageBeanAsync.datas;
            var pageSize = result.pageBeanAsync.pageSize;
            var currentPageNumber = result.pageBeanAsync.currentPageNumber;
            var str = "";
            var gender;
            for (var i = 0; i < students.length; i++) {
                if (students[i].sex == "1") {
                    gender = "男";
                } else if (students[i].sex == "2") {
                    gender = "女";
                }
                str += "<tr>" +
                    "<td align=\'" + "center" + "\'>" + students[i].dataId + "</td>" +
                    "<td align=\'" + "center" + "\'>" + students[i].stuNo + "</td>" +
                    "<td align=\'" + "center" + "\'>" + students[i].stuName + "</td>" +
                    "<td align=\'" + "center" + "\'>" + gender + "</td>" +
                    "<td align=\'" + "center" + "\'>" + students[i].age + "</td>" +
                    "<td align=\'" + "center" + "\'>" + students[i].createDate + "</td>" +
                    "<td>" +
                    "  <button onclick=\"" + "proDelete(\'" + currentPageNumber + "\'" + "," + students[i].dataId + ")" + "\">" + "delete" + "</button>" +
                    "</td>" +
                    "<td>" +
                    "  <button onclick=\"" + "studentDetail(\'" + currentPageNumber + "\'" + "," + students[i].dataId + ")" + "\">" + "Change" + "</button>" +
                    "</td>" +
                    "</tr>";
                $("#tbody").html(str);
            }
            pageToolBar(pageBeanAsync);
        }
    });
    show();
}