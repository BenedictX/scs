queryCourse = function (index) {
    var courseId = document.getElementById("queryCourseId").value;
    var courseName = document.getElementById("queryCourseName").value;
    var course = {courseId:courseId,courseName:courseName};
    queryAsync("/queryCourse/",index,course);

}
show = function (){
    document.getElementById("resultOfQuery").style.display = "block";
    document.getElementById("showAll").style.display = "block";
}
queryAsync = function(url,currentPageNumber,data){
    if(!checkQueryCourse()){
        return false;
    }
    $.ajax({
        url: url + currentPageNumber ,
        type: "POST",
        data:JSON.stringify(data),
        contentType: "application/json;charset = utf-8",
        async: true,
        error: function () {
            alert("Query Failed !");
        },
        success: function (result) {
            var pageBean = result.pageBean;
            var datas = pageBean.datas;
            var str = "";
            if(datas.length==0){
                str="<td colspan=\"3\" align=\'" + "center" + "\'>"+"No Query Results !"+"</td>"
                $("#tbody").html(str);
                removeChilds();
                show();
                document.getElementById("pageDiv").style.display="none";
            }else{
                for (var i = 0; i < datas.length; i++) {
                    str += "<tr>" +
                        "<td align=\'" + "center" + "\'>" + datas[i].courseId + "</td>" +
                        "<td align=\'" + "center" + "\'>" + datas[i].courseName + "</td>" +
                        "<td>" +
                        "  <button onclick=\"" + "deleteCourse(\'" + currentPageNumber + "\'" + "," + datas[i].courseId + ")" + "\">" + "delete" + "</button>" +
                        "</td>" +
                        "<td>" +
                        "  <button onclick=\"" + "courseDetail(\'" + currentPageNumber + "\'" + "," + datas[i].courseId + ")" + "\">" + "Change" + "</button>" +
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
checkQueryCourseName = function(){
    var queryCourseName  = document.getElementById("queryCourseName").value;
    var queryCourseNameError = document.getElementById("queryCourseNameError");
    var pattern = /^([A-Za-z]*|[\u4E00-\u9FA5]*)$/;
    if(!pattern.test(queryCourseName)){
        queryCourseNameError.innerHTML = "CourseName Must Be English letters Or Chinese !";
        return false;
    }else{
        queryCourseNameError.innerHTML ="";
        return true;
    }
}
checkQueryCourseId = function(){
    var queryCourseId = document.getElementById("queryCourseId").value;
    var queryCourseIdError = document.getElementById("queryCourseIdError");
    var pattern =/^([0-9]*)$/;
    console.log(queryCourseId);
    if(!pattern.test(queryCourseId)){
        queryCourseIdError.innerHTML = "CourseId Must Be Number !";
        return false;
    }else{
        queryCourseIdError.innerHTML="";
        return true;
    }
}
checkQueryCourse =function(){
    var courseIdTips = checkQueryCourseId();
    var courseNameTips = checkQueryCourseName();
    if(courseIdTips&&courseNameTips){
        return true;
    }else{
        return false;
    }
}