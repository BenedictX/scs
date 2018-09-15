window.onload = function () {
    document.getElementById("showAll").style.display = "none";
    document.getElementById("resultOfQuery").style.display = "none";
    var pageSize = document.getElementById("pageSize").value;
    findStudent(1, pageSize);
};
ShowAll = function () {
    document.getElementById("resultOfQuery").style.display = "none";
    document.getElementById("showAll").style.display = "none";
    document.getElementById("queryStuNo").value="";
    document.getElementById("queryStuName").value="";
    document.getElementById("queryAge").value="";
    var pageSize = document.getElementById("pageSize").value;
    findStudent(1, pageSize);
};
proDelete = function (currentPageNumber ,dataId) {

    if (confirm("是否确认删除?")) {
        $.ajax({
            url: "/deleteStudentByDataId" +"/" + dataId,
            type: 'POST',
            contentType: 'application/json;charset=utf-8',
            async: true,
            error: function () {
                alert("Request Failed !");
            },
            success: function (data) {
                if (data.isDelete == "1") {
                    alert("Delete Success !");
                    var pageSize = document.getElementById("pageSize").value;
                    findAsync(1, pageSize);
                } else {
                    alert("No This Student !");
                }
            }

        });
    }
}
studentDetail = function (currentPageNumber, dataId) {
    location.href = "/findStudentByDataId/" + currentPageNumber + "/" + dataId;
}
checkForm = function () {
   var  stuNoTips = checkStuNo();
   var stuNameTips = checkStuName();
   var  ageTips = checkAge();
    console.log(stuNoTips, stuNameTips, ageTips);
    if (stuNoTips && stuNameTips && ageTips) {
        document.getElementById("addStuNoError").innerHTML = "";
        document.getElementById("addStuNameError").innerHTML = "";
        document.getElementById("addAgeError").innerHTML = "";
        return true;
    } else {
        alert("Can`t Add");
        document.getElementById("addStuNoError").innerHTML = "";
        document.getElementById("addStuNameError").innerHTML = "";
        document.getElementById("addAgeError").innerHTML = "";
        return false;
    }
};
checkStuNo = function () {
    var addStuNo = document.getElementById("addStuNo").value.trim();
    var addStuNoError = document.getElementById("addStuNoError");
    if (addStuNo.length === 0) {
        addStuNoError.innerHTML = "StuNo Not Null";
        return false;
    } else if (addStuNo.length !== 10) {
        addStuNoError.innerHTML = "Length of stuNo must be 10 ! ";
        return false;
    } else {
        var status = false;
        $.ajax({
            type: "post",
            url: "/findStudentByStuNo",
            async: false,
            dataType: "json",
            data: {stuNo: addStuNo},
            error: function () {
                alert("Error !");
            },
            success: function (resultData) {
                if (resultData.isExistence === "501") {
                    addStuNoError.innerHTML = "This stuNo Has Already Existed ! ";
                    status = false;
                } else {
                    addStuNoError.innerHTML = "OK";
                    status =true;
                }
            }
        });
        return status;
    }
};
checkStuName = function () {
    addStuName = document.getElementById("addStuName");
    addStuNameError = document.getElementById("addStuNameError");
    pattern = /^([A-Za-z]+|[\u4E00-\u9FA5]+)$/;
    if (addStuName.value.length === 0) {
        addStuNameError.innerHTML = "StuName Not Null !";
        return false;
    } else if (!pattern.test(addStuName.value)) {
        addStuNameError.innerHTML = "StuName Must Be English letters Or Chinese !";
        return false;
    } else {
        addStuNameError.innerHTML = "OK";
        return true;
    }
};
checkAge = function () {
    addAge = document.getElementById("addAge");
    addAgeError = document.getElementById("addAgeError");
    pattern = /^([1-9]*|1|100)$/;
    if (addAge.value.length === 0 || addAge.value == null) {
        addAgeError.innerHTML = "Age Not Null !";
        return false;
    } else if (!pattern.test(addAge.value)) {
        addAgeError.innerHTML = "Age Must Be Number !";
        return false;
    } else {
        addAgeError.innerHTML = "OK";
        return true;
    }


};
findStudent = function (currentPageNumber, pageSize) {
    $.ajax({
        url: "/findStudentAsync/"+currentPageNumber + "/" + pageSize,
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        async: true,
        error: function () {
            alert("Request Failed !");
        },
        success: function (result) {
            var students = result.pageBeanAsync.datas;
            var currentPageNumber = result.pageBeanAsync.currentPageNumber;
            var pageBeanAsync = result.pageBeanAsync;
            var str = "";
            var gender;
            for (var i = 0; i < students.length; i++) {
                if (students[i].sex == 1) {
                    gender = "男";
                } else if (students[i].sex == 2) {
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
            document.getElementById("pageDiv").style.display ="block";
            pageToolBar(pageBeanAsync);
        }

    });
};