pageSizeChange = function () {
    var pageSize = document.getElementById("pageSize").value;
    var currentPageNumber = document.getElementById("currentPageNumber").innerText;
    $.ajax({
        type: "post",
        url: "/coursePageSizeChange",
        async: true,
        dataType: "json",
        data: {pageSize: pageSize},
        error: function () {
            alert("PageSize Change Error");
        },
        success: function (resultData) {
            if (resultData.pageSizeChange == "OK") {
                findAsync("/findCourse/", currentPageNumber);
            } else {
                alert("PageSize Change Failed");
            }
        }
    });
}
pageToolBar = function (pageBean) {
    removeChilds();
    var currentPageNumber = pageBean.currentPageNumber;
    var totalPages = pageBean.totalPages;
    document.getElementById("currentPageNumber").innerText = currentPageNumber;
    document.getElementById("totalPages").innerText = totalPages;
    document.getElementById("inputPageNumber").value = currentPageNumber;

    var Home = document.getElementById("home");
    var Up = document.getElementById("up");
    var Next = document.getElementById("next");
    var End = document.getElementById("end");

    if (currentPageNumber == 1) {
        Home.innerHTML = "";
        Up.innerHTML = "";
    } else {
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
        var td = document.createElement("td");
        var a = document.createElement("a");
        if (i == currentPageNumber) {

        } else {
            a.href = "javascript:" + "find(" + i + ")";
        }
        a.innerHTML = "" + i;
        a.class = "list";
        td.appendChild(a);
        pageCodeDiv.appendChild(td);

    }
    if (currentPageNumber === totalPages) {
        Next.innerHTML = "";
        End.innerHTML = ""
    } else {
        Next.innerHTML = "Next";
        End.innerHTML = "End";
    }

}
find = function (index) {
    var showAll = document.getElementById("showAll");
    if (showAll.style.display == "none") {
        findAsync("/findCourse/", index);
    } else {
        queryCourse(index);
    }
}
removeChilds = function () {
    var pageCodeDiv = document.getElementById('pageCode');
    var childs = pageCodeDiv.childNodes;
    for (var i = (childs.length - 1); i >= 0; i--) {
        pageCodeDiv.removeChild(childs[i]);
    }
}
jump = function () {
    var inputPageNumber = document.getElementById("inputPageNumber").value;
    find(inputPageNumber);
}
Home = function () {
    find(1);
}
Up = function () {
    var currentPageNumber = document.getElementById("currentPageNumber").innerText;
    find(Number(currentPageNumber) - 1);
}
Next = function () {
    var currentPageNumber = document.getElementById("currentPageNumber").innerText;
    find(Number(currentPageNumber) + 1);
}
End = function () {
    var totalPages = document.getElementById("totalPages").innerText;
    find(Number(totalPages));
}

