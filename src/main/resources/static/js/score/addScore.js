checkAddCourseScore = function () {
    var addCourseScore = document.getElementById("addCourseScore").value.trim();
    console.log(addCourseScore);
    var addCourseScoreError = document.getElementById("addCourseScoreError");
    var pattern = /^([+]{0,1}(\d+))$|^([+]{0,1}(\d+\.\d+))$/;

    if (addCourseScore <= 100.000) {
        if (!pattern.test(addCourseScore)) {
            addCourseScoreError.innerHTML = "Not Null And Must Be 0-100";
            return false;
        } else {
            addCourseScoreError.innerHTML = "OK";
            return true;
        }
        return true;
    } else {
        addCourseScoreError.innerHTML = "Max is 100";
        return false;
    }

}
findStuNo = function () {
    $.ajax({
        url: "/findStuNo",
        type: 'POST',
        dataType: "json",
        async: true,
        error: function () {
            alert("FindStuNo Error !")
        },
        success: function (result) {
            var stuNos = result.stuNos;
            var select = document.getElementById("addStuNo");
            select.innerHTML = '<option value ="" >NumberOfStudent</option>';
            for (var i = 0; i < stuNos.length; i++) {
                var option = document.createElement("option");
                option.value = stuNos[i];
                var text = document.createTextNode(stuNos[i]);
                option.appendChild(text);
                select.appendChild(option);
            }
        }
    });
}
findStuName = function () {
    var stuNo = document.getElementById("addStuNo").value;
    $.ajax({
        url: "/findStuNameByStuNo/" + stuNo,
        type: 'POST',
        dataType: "json",
        async: true,
        error: function () {
            alert("FindStuName Error !")
        },
        success: function (result) {
            document.getElementById("addStuName").innerHTML = result.stuName;
        }
    });
}
findCourse = function () {
    $.ajax({
        url: "/findCourse",
        type: 'POST',
        dataType: "json",
        async: true,
        error: function () {
            alert("FindCourse Error !")
        },
        success: function (result) {
            var courses = result.courses;
            var select = document.getElementById("addCourseName");
            select.innerHTML = '<option value ="" >courseName</option>';
            for (var i = 0; i < courses.length; i++) {
                var option = document.createElement("option");
                option.value = courses[i];
                var text = document.createTextNode(courses[i]);

                option.appendChild(text);
                select.appendChild(option);
            }
        }
    });
}
findCourseId = function () {
    var courseName = document.getElementById("addCourseName").value;
    $.ajax({
        url: "/findCourseIdByCourseName/" + courseName,
        type: 'POST',
        dataType: "json",
        async: true,
        error: function () {
            alert("FindStuName Error !")
        },
        success: function (result) {
            document.getElementById("addCourseId").innerHTML = result.courseId;
        }
    });

}
addScore = function () {
    if (!checkAddCourseScore()) {
        alert("Can`t Add !");
        return false;
    }
    var stuNo = document.getElementById("addStuNo").value;
    var courseId = document.getElementById("addCourseId").innerHTML;
    var courseScore = document.getElementById("addCourseScore").value;
    var student = {stuNo: stuNo};
    var course = {courseId: courseId};
    var score = {student: student, course: course, courseScore: courseScore};
    var addCourseScoreError = document.getElementById("addCourseScoreError");
    $.ajax({
        url: "/addScore",
        type: 'POST',
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(score),
        async: true,
        error: function () {
            alert(" Add Error !");
        },
        success: function (result) {
            console.log(result);
            if (result.isAdd == "101") {
                alert("Add Success !")
                var totalPages = result.totalPages;
                findScore("/findScore/", totalPages);
                addCourseScoreError.innerHTML = "";
                $("#currentPageNumber").text(totalPages);
            } else {
                alert(" Do Not Add repeatedly !")
            }
        }
    });
    ;
}