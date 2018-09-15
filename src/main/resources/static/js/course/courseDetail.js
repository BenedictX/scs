checkCourseName = function(){
    var courseName  = document.getElementById("courseName").value;
    var courseNameError = document.getElementById("courseNameError");
    var pattern = /^([A-Za-z]*|[\u4E00-\u9FA5]*)$/;
    if(!pattern.test(courseName)){
        courseNameError.innerHTML = "CourseName Must Be English letters Or Chinese !";
        return false;
    }else{;
        $.ajax({
            url:"/findCourseByName",
            type: 'POST',
            dataType: "json",
            async: true,
            data:{courseName:courseName},
            error:function(){
                alert("FindCourseByName Error !");
            },
            success:function(result){
                if(result.isExistence == "501"){
                    courseNameError.innerHTML = "This CourseName Is Existence !";
                }else{
                    courseNameError.innerHTML = "OK";

                    document.getElementById("update").style.display="block";
                    document.getElementById("goBack").style.display="none";
                }
            }
        });

        if(courseNameError.innerHTML == "OK"){
            return true;
        }else{
            return false;
        }

    }
}
goBack = function () {
    location.href = "/findCourseIndex";
};
update = function (currentPageNumber) {
    if (!checkCourseName()) {
        return false;
    }
    var courseId  = document.getElementById("courseId").innerText;
    var courseName  = document.getElementById("courseName").value;
    var course = {courseId:courseId,courseName:courseName};
    console.log(course);
    $.ajax({
        url: "/updateCourse" ,
        contentType: "application/json;charset = utf-8",
        data: JSON.stringify(course),
        type: "POST",
        async: true,
        error: function () {
            alert("Update Error !");
        },
        success: function (result) {
            console.log(result);
            if (result.isUpdate == "301") {
                alert("Update Success !");
                goBack();
            } else {
                alert("Update Failed !");
            }
        }

    });

};