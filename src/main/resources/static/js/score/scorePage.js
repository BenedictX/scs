
pageSizeChange = function () {
    var pageSize = document.getElementById("pageSize");
    $.ajax({
        type: "post",
        url: "/scorePageSizeChange",
        async: true,
        dataType: "json",
        data: {pageSize: pageSize.value},
        error: function () {
            alert("PageSize Change Error");
        },
        success: function (result) {
            if (result.pageSizeChange == "OK") {
                var currentPageNumber = 1;
                findAsync(currentPageNumber);
            } else {
                alert("PageSize Change Failed");
            }
        }
    });
}
pageToolBar = function (pageBean) {
    removeChilds();
    console.log(pageBean);
    var currentPageNumber = pageBean.currentPageNumber;
    var totalPages = pageBean.totalPages;
    var pageSize = pageBean.pageSize;
    document.getElementById("currentPageNumber").innerText = currentPageNumber;
    document.getElementById("totalPages").innerText = totalPages;
    document.getElementById("inputPageNumber").value= currentPageNumber;
    document.getElementById("pageSize").value = pageSize;
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
    for (var i = begin; i <= end; i++) {
        var td =document.createElement("td");
        var a = document.createElement("a");
        if(i==currentPageNumber){

        }else{
            a.href = "javascript:"+"findAsync("+i+")";
        }
        a.innerHTML = "" + i;
        a.class="list";
        td.appendChild(a);
        pageCodeDiv.appendChild(td);

    }
    console.log(pageCodeDiv);
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
findAsync = function(index){

    var showAll = document.getElementById("showAll");
    if (showAll.style.display == "none") {

        findScore("/findScore/",index);
    } else {
        queryScore(index);
    }
}
removeChilds = function () {
    var pageCodeDiv = document.getElementById('pageCode');
    var count = pageCodeDiv.childElementCount;
    console.log(count);
    var childs = pageCodeDiv.childNodes;
    console.log(childs);
    for(var i=(childs.length-1);i>=0;i--){
        pageCodeDiv.removeChild(childs[i]);
    }
}
jump =function () {
    var inputPageNumber = document.getElementById("inputPageNumber").value;
    findAsync(inputPageNumber);
}
Home=function(){
    findAsync(1);
}
Up=function(){
    var currentPageNumber =document.getElementById("currentPageNumber").innerText;
    findAsync(Number(currentPageNumber)-1);
}
Next=function () {
    var currentPageNumber =document.getElementById("currentPageNumber").innerText;
    console.log(pageSize);
    findAsync(Number(currentPageNumber)+1);
}
End =function () {
    var totalPages =document.getElementById("totalPages").innerText;
    findAsync(Number(totalPages));
}

