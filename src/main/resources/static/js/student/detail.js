window.onload=function(){
    var male = document.getElementById("male");
    var female =document.getElementById("female");
    if(male.value == 1){
        male.checked = true;
    }
    if(female.value == 2){
        female.checked = true;
    }
}
goBack = function (currentPageNumber) {
    location.href = "/findStudent/" + currentPageNumber;
};
update = function (currentPageNumber) {
    if (!checkForm()) {
        return false;
    }
    var dataId = document.getElementById("dataId").value;
    var stuNo = document.getElementById("stuNo").innerText;
    var stuName = document.getElementById("stuName").value;
    var male = document.getElementById("male");
    var female = document.getElementById("female");
    var age = document.getElementById("age").value;
    var sex;
    if (male.checked == true) {
        sex = 1;
    }
    if (female.checked == true) {
        sex = 2;
    }
    var student = {dataId:dataId, stuNo: stuNo, stuName: stuName, sex: sex, age: age};
    console.log(student);
    $.ajax({
        url: "/updateStudent/" + currentPageNumber,
        contentType: "application/json;charset = utf-8",
        data: JSON.stringify(student),
        type: "POST",
        async: true,
        error: function () {
            alert("Update Error !");
        },
        success: function (result) {
            console.log(result);
            if (result.isUpdate == "301") {
                alert("Update Success !");
                location.href = "/findStudent/" + currentPageNumber;
            } else {
                alert("Update Failed !");
            }
        }

    });

};
checkStuName = function () {
    var stuName = document.getElementById("stuName");
    var stuNameError = document.getElementById("stuNameError");
    var pattern = /^([A-Za-z]+|[\u4E00-\u9FA5]+)$/;
    if (stuName.value.length == 0) {
        stuNameError.innerHTML = "StuName Not Null !";
        return false;
    } else if (!pattern.test(stuName.value)) {
        stuNameError.innerHTML = "StuName Must Be English letters Or Chinese !";
        return false;
    } else {
        stuNameError.innerHTML = "OK";
        return true;
    }
};
checkAge = function () {
    var age = document.getElementById("age");
    var ageError = document.getElementById("ageError");
    var pattern = /^([1-9]?\d|0|100)$/;
    if (age.value.length == 0) {
        ageError.innerHTML = "Age Not Null !";
        return true;
    } else if (!pattern.test(age.value)) {
        ageError.innerHTML = "Age Must Be Number !";
        return false;
    } else {
        ageError.innerHTML = "OK";
        return true;
    }
};
window.onchange  = function(){
    if(checkForm()){
        document.getElementById("update").style.display="block";
        document.getElementById("goBack").style.display="none";
    }
}
checkForm = function () {
    var stuNameTips = checkStuName();
    var ageTips = checkAge();
    if ( stuNameTips && ageTips) {
        return true;
    } else {
        return false;
    }
};
