window.onload = function(){
    var url="/findScore/"
    var currentPageNumber = 1;
    findStuNo();
    findCourse();
    findScore(url,currentPageNumber);

}
ShowAll = function () {
    document.getElementById("resultOfQuery").style.display = "none";
    document.getElementById("showAll").style.display = "none";
    document.getElementById("queryCourseId").value="";
    document.getElementById("queryCourseName").value="";
    document.getElementById("pageDiv").style.display = "block";
    var url = "/findScore/";
    findScore(url,1);
};
findScore = function (url, currentPageNumber) {
    $.ajax({
        url: url+currentPageNumber,
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        async: true,
        error: function () {
            alert("Error!");
        },
        success: function (result) {

            var pageBean = result.pageBean;
            console.log(pageBean)
            var datas = pageBean.datas;
            var currentPageNumber = pageBean.currentPageNumber;
            var str = "";
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
            document.getElementById("pageDiv").style.display ="block";
            pageToolBar(pageBean);
        }

    });
};
deleteScore =function(currentPageNumber,dataId){
    $.ajax({
        url: "/deleteScoreByDataId/" + currentPageNumber,
        type: 'POST',
        data:{dataId:dataId},
        dataType:"json",
        async: true,
        error: function () {
            alert("Error!");
        },
        success: function (result) {
            if(result.isDelete == "201"){
                alert("Delete Success !");
                findScore("/findScore/",currentPageNumber);
            }else{
                alert("Delete Failed !");
            }
        }
    });
}
scoreDetail = function(currentPageNumber,dataId){
    location.href = "/findScoreByDataId/" + currentPageNumber + "/" + dataId;
}