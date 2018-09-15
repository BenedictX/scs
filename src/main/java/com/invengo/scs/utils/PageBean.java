package com.invengo.scs.utils;

import java.util.List;

/**
 * Created By IntelliJ IDEA
 * User: Barney wong
 * Date: 2018/09/03
 * Time: 09:10
 */
public class PageBean<T> {
    private List<T> datas; //当前页记录数据
    private int totalRecord;//条件查询所得总记录数
    private int pageSize;//页面显示记录条数
    private int currentPageNumber;//当前页面页码
    private int totalPages;//总页面数

    private String url;

    public PageBean(int totalRecord, int pageSize, int currentPageNumber) {
        this.totalRecord = totalRecord;
        this.pageSize = pageSize;
        this.currentPageNumber = currentPageNumber;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }
    public int getTotalPages(){
        if((totalRecord%pageSize)!=0){
           totalPages = totalRecord/pageSize + 1;
        }else {
            totalPages = totalRecord / pageSize;
        }
        return totalPages;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "PageBean{" +
                "datas=" + datas +
                ", totalRecord=" + totalRecord +
                ", pageSize=" + pageSize +
                ", currentPageNumber=" + currentPageNumber +
                ", totalPages=" + totalPages +
                ", url='" + url + '\'' +
                '}';
    }
}
