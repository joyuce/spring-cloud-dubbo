package com.j.openproject.query;

import java.io.Serializable;

import lombok.Data;

/**
 * @author shenxiaodong
 * @Type BaseQuery
 * @Desc
 * @date 2019年07月01日
 * @Version V1.0
 */
@Data
public abstract class BaseQuery implements Serializable {

    /**
     * 分页页码
     */
    protected Integer pageNum;

    /**
     * 分页页大小
     */
    protected Integer pageSize;

    public int getStart() {
        return pageNum * pageSize;
    }

    public int getEnd() {
        return pageSize;
    }

    public BaseQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.resetPage();
    }

    private void resetPage() {
        pageNum = setNum(pageNum, 0);
        pageSize = setNum(pageSize, 20);
        if (pageSize > 200) {
            pageSize = 200;
        }
    }

    private Integer setNum(Integer num, int defult) {
        if (num == null || num < 0) {
            return defult;
        } else {
            return num;
        }
    }

}
