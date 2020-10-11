package com.jason.blog.domain;

/**
 * 翻页
 */
public class MyPage {

    private int curPage = 1;        //当前页
    private int totalPage;      //总页数
    private int nextPage;       //下一页
    private int prePage;        //上一页
    private boolean next;       //是否有下一页
    private boolean previous;   //是否有上一页

    @Override
    public String toString() {
        return "MyPage{" +
                "curPage=" + curPage +
                ", totalPage=" + totalPage +
                ", nextPage=" + nextPage +
                ", prePage=" + prePage +
                ", next=" + next +
                ", previous=" + previous +
                '}';
    }

    public void init() {
        getNextPage();
        getPrePage();
        isPrevious();
        isNext();
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getNextPage() {
        if (getCurPage() < getTotalPage())
            this.nextPage = getCurPage() + 1;
        else
            this.nextPage = getTotalPage();
        return nextPage;
    }

    public int getPrePage() {
        if (getCurPage() <= 1)
            this.prePage = 1;
        else
            this.prePage = getCurPage() - 1;
        return prePage;
    }

    public boolean isNext() {
        if (getCurPage() == getTotalPage())
            this.next = false;
        else
            this.next = true;
        return next;
    }

    public boolean isPrevious() {
        if (getCurPage() == 1)
            this.previous = false;
        else
            this.previous = true;
        return previous;
    }
}
