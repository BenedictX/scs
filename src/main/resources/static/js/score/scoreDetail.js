checkCourseScore = function(){
    var courseScore = document.getElementById("courseScore").value;
    var courseScoreError=document.getElementById("courseScoreError");
    var pattern = /^([+]{0,1}(\d+))$|^([+]{0,1}(\d+\.\d+))$/;
    if (courseScore <= 100.000) {
        if (!pattern.test(courseScore)) {
            courseScoreError.innerHTML = "Not Null And Must Be 0-100";
            document.getElementById("update").style.display="none";
            document.getElementById("goBack").style.display="block";
            return false;
        } else {
            courseScoreError.innerHTML = "OK";
            document.getElementById("update").style.display="block";
            document.getElementById("goBack").style.display="none";
            return true;
        }
        return true;
    } else {
        document.getElementById("update").style.display="none";
        document.getElementById("goBack").style.display="block";
        courseScoreError.innerHTML = "Max is 100";
        return false;
    }

}
goBack = function(){
    location.href = "/findScoreIndex";
}
update = function (currentPageNumber) {
    if (!checkCourseScore()) {
        return false;
    }
    var dataId  = document.getElementById("dataId").value;
    var courseScore  = document.getElementById("courseScore").value;
    var score = {dataId:dataId,courseScore:courseScore};
    console.log(score);
    $.ajax({
        url: "/updateScore" ,
        contentType: "application/json;charset = utf-8",
        data: JSON.stringify(score),
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