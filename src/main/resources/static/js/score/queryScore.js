checkQueryStuNo = function () {
    var queryStuNo = document.getElementById("queryStuNo").value.trim();
    var queryStuNoError = document.getElementById("queryStuNoError");
    var pattern = /^([0-9]*)$/;
    if (!pattern.test(queryStuNo)) {
        queryStuNoError.innerHTML = "QueryStuNo Must Be Integer !";
        return false;
    } else {
        queryStuNoError.innerHTML = "";
        return true;
    }
}
checkQueryCourseId = function () {
    var queryCourseId = document.getElementById("queryCourseId").value.trim();
    var queryCourseIdError = document.getElementById("queryCourseIdError");
    var pattern = /^([0-9]*)$/;
    if (!pattern.test(queryCourseId)) {
        queryCourseIdError.innerHTML = "QueryCourseId Must Be Integer !"
        return false;
    } else {
        queryCourseIdError.innerHTML = "";
        return true;
    }
}
checkQueryCourseName = function () {
    var queryCourseName = document.getElementById("queryCourseName").value.trim();
    var queryCourseNameError = document.getElementById("queryCourseNameError");
    var pattern = /^([A-Za-z]*|[\u4E00-\u9FA5]*)$/;
    if (!pattern.test(queryCourseName)) {
        queryCourseNameError.innerHTML = "CourseName Must Be English letters Or Chinese !";
        return false;
    } else {
        queryCourseNameError.innerHTML = "";
        return true;
    }
}
checkQueryCourseScoreMin = function () {
    var queryCourseScoreMin = document.getElementById("queryCourseScoreMin").value.trim();
    var queryCourseScoreMax = document.getElementById("queryCourseScoreMax").value.trim();
    var queryCourseScoreError = document.getElementById("queryCourseScoreError");
    var pattern = /^(^$)|([0-9]+([\.]{1}[0-9]+){0,1})$/;
    if (!pattern.test(queryCourseScoreMin)) {
        queryCourseScoreError.innerHTML = " Min Must Be 0-100";
        return false;
    } else {
        if (queryCourseScoreMin.length > 0 && queryCourseScoreMax.length > 0) {
            console.log(queryCourseScoreMin + "" + queryCourseScoreMax);
            if (queryCourseScoreMin > queryCourseScoreMax) {
                queryCourseScoreError.innerHTML = "Min Can`t Great Than Max"
                return false;
            }
            else {
                queryCourseScoreError.innerHTML = "";
                return true;
            }
            return true;
        }
        return true;
    }
}
checkQueryCourseScoreMax = function () {
    var queryCourseScoreMin = document.getElementById("queryCourseScoreMin").value.trim();
    var queryCourseScoreMax = document.getElementById("queryCourseScoreMax").value.trim();
    var queryCourseScoreError = document.getElementById("queryCourseScoreError");
    var pattern = /^(^$)|([0-9]+([\.]{1}[0-9]+){0,1})$/;
    if (!pattern.test(queryCourseScoreMax)) {
        queryCourseScoreError.innerHTML = "Max Must Be 0-100";
        return false;
    } else {
        if (queryCourseScoreMin.length > 0 && queryCourseScoreMax.length > 0) {
            console.log(queryCourseScoreMin + "" + queryCourseScoreMax);
            if (queryCourseScoreMin > queryCourseScoreMax) {
                queryCourseScoreError.innerHTML = "Min Can`t Great Than Max"
                return false;
            } else {
                queryCourseScoreError.innerHTML = "";
                return true;
            }
            return true;
        }
        return true;
    }

}
checkQueryScore = function () {
    var queryStuNoTips = checkQueryStuNo();
    var queryCourseIdTips = checkQueryCourseId();
    var queryCourseNameTips = checkQueryCourseName();
    var queryCourseScoreMinTips = checkQueryCourseScoreMin();
    var queryCourseScoreMaxTips = checkQueryCourseScoreMax();

    if (queryStuNoTips && queryCourseIdTips && queryCourseNameTips && queryCourseScoreMinTips && queryCourseScoreMaxTips) {
        return true;
    } else {
        return false;
    }
}
show = function () {
    document.getElementById("resultOfQuery").style.display = "block";
    document.getElementById("showAll").style.display = "block";
}
queryScore = function (index) {
    if (!checkQueryScore()) {
        alert("Check Failed !")
        return false;
    }
    var queryStuNo = document.getElementById("queryStuNo").value.trim();
    var queryCourseId = document.getElementById("queryCourseId").value.trim();
    var queryCourseName = document.getElementById("queryCourseName").value.trim();
    var queryCourseScoreMin = document.getElementById("queryCourseScoreMin").value.trim();
    var queryCourseScoreMax = document.getElementById("queryCourseScoreMax").value.trim();

    var queryStudent = {stuNo: queryStuNo};
    var queryCourse = {courseId: queryCourseId, courseName: queryCourseName};
    var score = {student: queryStudent, course: queryCourse};
    var queryScore = {score: score, min: queryCourseScoreMin, max: queryCourseScoreMax};
    console.log(queryScore);
    $.ajax({
        url: "/queryScore/" + index,
        type: 'POST',
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(queryScore),
        async: true,
        error: function () {
            alert("QueryScore Error !")
        },
        success: function (result) {
            var pageBean = result.pageBean;
            var datas = pageBean.datas;
            var currentPageNumber = pageBean.currentPageNumber;
            var str = "";
            if (datas.length == 0) {
                str = "<td colspan=\"8\"  align=\'" + "center" + "\'>" + "No Query Results !" + "</td>"
                $("#tbody").html(str);
                removeChilds();
                show();
                document.getElementById("pageDiv").style.display = "none";
            } else {
                for (var i = 0; i < datas.length; i++) {
                    str += "<tr>" +
                        "<td align=\'" + "center" + "\'>" + datas[i].dataId + "</td>" +
                        "<td align=\'" + "center" + "\'>" + datas[i].student.stuNo + "</td>" +
                        "<td align=\'" + "center" + "\'>" + datas[i].student.stuName + "</td>" +
                        // "<td align=\'" + "center" + "\'>" + gender + "</td>" +
                        "<td align=\'" + "center" + "\'>" + datas[i].course.courseId + "</td>" +
                        "<td align=\'" + "center" + "\'>" + datas[i].course.courseName + "</td>" +
                        "<td align=\'" + "center" + "\'>" + datas[i].courseScore + "</td>" +
                        "<td>" +
                        "  <button onclick=\"" + "deleteScore(\'" + currentPageNumber + "\'" + "," + datas[i].dataId + ")" + "\">" + "delete" + "</button>" +
                        "</td>" +
                        "<td>" +
                        "  <button onclick=\"" + "scoreDetail(\'" + currentPageNumber + "\'" + "," + datas[i].dataId + ")" + "\">" + "Change" + "</button>" +
                        "</td>" +
                        "</tr>";
                    $("#tbody").html(str);
                }
                show();
                pageToolBar(pageBean);
            }
        }
    });

}