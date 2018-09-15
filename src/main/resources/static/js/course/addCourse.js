addCourse = function () {
    if (!checkCourseName()) {
        return false;
    }
    var courseName = document.getElementById("addCourseName").value;
    var currentPageNumber = document.getElementById("currentPageNumber").innerText;
    var addCourseNameError = document.getElementById("addCourseNameError");
    $.ajax({
        url: "/addCourse/",
        type: 'POST',
        dataType: "json",
        async: true,
        data: {courseName: courseName},
        error: function () {
            alert(" Add Error !");
        },
        success: function (result) {
            var url = result.url;
            if (result.isAdd == "101") {
                addCourseNameError.innerHTML = "";
                var totalPages = result.totalPages;
                findAsync(url, totalPages);
                $("#currentPageNumber").text(totalPages);
            } else {
                alert("Add Failed !");
                findAsync(url, currentPageNumber);
            }
        }
    });

}

checkCourseName = function(){
    var addCourseName  = document.getElementById("addCourseName").value;
    var addCourseNameError = document.getElementById("addCourseNameError");
    var pattern = /^([A-Za-z]+|[\u4E00-\u9FA5]+)$/;
    if(addCourseName.length == 0){
        addCourseNameError.innerHTML = "CourseName Not Null !";
        return false;
    }else if(!pattern.test(addCourseName)){
        addCourseNameError.innerHTML = "CourseName Must Be English letters Or Chinese !";
        return false;
    }else{
        alert("1024");
        $.ajax({
            url:"/findCourseByName",
            type: 'POST',
            dataType: "json",
            async: true,
            data:{courseName:addCourseName},
            error:function(){
                alert("FindCourseByName Error !");
            },
            success:function(result){
                if(result.isExistence == "501"){
                    addCourseNameError.innerHTML = "This CourseName Is Existence !";
                }else{
                    addCourseNameError.innerHTML = "OK";
                }
            }
        });

        if(addCourseNameError.innerHTML == "OK"){
            return true;
        }else{
            return false;
        }

    }
}