
pageSizeChange = function () {
    var pageSize = document.getElementById("pageSize");
    $.ajax({
        type: "post",
        url: "/pageSizeChange",
        async: true,
        dataType: "json",
        data: {pageSize: pageSize.value},
        error: function () {
            alert("PageSize Change Error");
        },
        success: function (resultData) {
            if (resultData.pageSizeChange == "OK") {
                findStudent(1, pageSize.value);
            } else {
                alert("PageSize Change Failed");
            }
        }
    });
}
pageToolBar = function (pageBeanAsync) {
    removeChilds();
    console.log(pageBeanAsync);
    var currentPageNumber = pageBeanAsync.currentPageNumber;
    var totalPages = pageBeanAsync.totalPages;
    document.getElementById("currentPageNumber").innerText = currentPageNumber;
    document.getElementById("totalPages").innerText = totalPages;
    document.getElementById("inputPageNumber").value= currentPageNumber;

    var Home = document.getElementById("home");
    var Up = document.getElementById("up");

    if (currentPageNumber==1) {
        Home.innerHTML = "";
        Up.innerHTML = "";
    }else{
            Home.innerHTML = "Home";
            Up.innerHTML = "Up";
    }
    var begin;
    var end;
    if (totalPages <= 10) {
        begin = 1;
        end = totalPages;
    } else {
        begin = currentPageNumber - 4;
        end = currentPageNumber + 5;
        if (begin < 1) {
            begin = 1;
            end = 10;
        }
        if (end > totalPages) {
            begin = totalPages - 9;
            end = totalPages;
        }
    }
    var pageCodeDiv = document.getElementById('pageCode');
    var size = pageBeanAsync.pageSize;
    for (var i = begin; i <= end; i++) {
        var td =document.createElement("td");
        var a = document.createElement("a");
        if(i==currentPageNumber){

        }else{
            a.href = "javascript:"+"findAsync("+i+","+size+")";
        }
        a.innerHTML = "" + i;
        a.class="list";
        td.appendChild(a);
        pageCodeDiv.appendChild(td);

    }
    var Next = document.getElementById("next");
    var End = document.getElementById("end");

    if (currentPageNumber === totalPages) {
        Next.innerHTML = "";
        End.innerHTML = ""
    } else {
        Next.innerHTML = "Next";
        End.innerHTML = "End";
    }
}
findAsync = function(currentPageNumber,pageSize){

    var showAll = document.getElementById("showAll");
    if (showAll.style.display == "none") {
      findStudent(currentPageNumber,pageSize);
    } else {
        queryStudentAsync(currentPageNumber,pageSize);
    }
}
removeChilds = function () {
    var pageCodeDiv = document.getElementById('pageCode');
    var childs = pageCodeDiv.childNodes;
    for(var i=(childs.length-1);i>=0;i--){
        pageCodeDiv.removeChild(childs[i]);
    }
}
jump =function () {
    var inputPageNumber = document.getElementById("inputPageNumber").value;
    var pageSize= document.getElementById("pageSize").value;
    findAsync(inputPageNumber,pageSize);
}
Home=function(){
    var pageSize =document.getElementById("pageSize").value;
    findAsync(1,pageSize);
}
Up=function(){
    var currentPageNumber =document.getElementById("currentPageNumber").innerText;
    var pageSize =document.getElementById("pageSize").value;
    findAsync(Number(currentPageNumber)-1,pageSize);
}
Next=function () {
    var currentPageNumber =document.getElementById("currentPageNumber").innerText;
    var pageSize =document.getElementById("pageSize").value;
    findAsync(Number(currentPageNumber)+1,pageSize);
}
End =function () {
    var totalPages =document.getElementById("totalPages").innerText;
    var pageSize =document.getElementById("pageSize").value;
    findAsync(Number(totalPages),pageSize);
}

