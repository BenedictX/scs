window.onload = function () {
    document.getElementById("showAll").style.display = "none";
    document.getElementById("resultOfQuery").style.display = "none";
    var url = "/findCourse/";
    findAsync(url,1);
}
findAsync = function ( url,currentPageNumber) {
    $.ajax({
        url: url+currentPageNumber,
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        async: true,
        error: function () {
            alert("Request Failed !");
        },
        success: function (result) {
            var pageBean = result.pageBean;
            var datas = pageBean.datas;
            var currentPageNumber = pageBean.currentPageNumber;
            document.getElementById("pageSize").value=pageBean.pageSize;
            console.log(pageBean);
            console.log(datas);
            var str = "";
            for (var i = 0; i < datas.length; i++) {
                str += "<tr>" +
                    "<td align=\'" + "center" + "\'>" + datas[i].courseId + "</td>" +
                    "<td align=\'" + "center" + "\'>" + datas[i].courseName + "</td>" +
                    "<td>" +
                    "  <button onclick=\"" + "deleteCourse(\'" + currentPageNumber + "\'" + ","  + datas[i].courseId + ")" + "\">" + "delete" + "</button>" +
                    "</td>" +
                    "<td>" +
                    "  <button onclick=\"" + "courseDetail(\'" + currentPageNumber + "\'" + "," + datas[i].courseId + ")" + "\">" + "Change" + "</button>" +
                    "</td>" +
                    "</tr>";
                $("#tbody").html(str);
            }
            pageToolBar(pageBean);
        }

    });
};
ShowAll = function () {
    document.getElementById("resultOfQuery").style.display = "none";
    document.getElementById("showAll").style.display = "none";
    document.getElementById("queryCourseId").value="";
    document.getElementById("queryCourseName").value="";
    document.getElementById("pageDiv").style.display = "block";
    var url = "/findCourse/";
    findAsync(url,1);
};
deleteCourse = function(currentPageNumber,courseId){
    if (confirm("是否确认删除?")) {
        $.ajax({
            url: "/deleteCourseByCourseId" + "/"  + courseId,
            type: 'POST',
            contentType: 'application/json;charset=utf-8',
            async: true,
            error: function () {
                alert("Request Failed !");
            },
            success: function (data) {
                if (data.isDelete == "1") {
                    alert("Delete Success !");
                    find("/findCourse/",1);
                } else {
                    alert("No This Course !");
                }
            }

        });
    }
}
courseDetail = function(currentPageNumber,courseId){
    location.href = "/findCourseByCourseId/" + currentPageNumber + "/" + courseId;
}