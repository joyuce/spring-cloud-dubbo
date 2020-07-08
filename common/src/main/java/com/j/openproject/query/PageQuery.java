package com.j.openproject.query;

/**
 * @author shenxiaodong
 * @Type PageQuery
 * @Desc
 * @date 2019年07月31日
 * @Version V1.0
 */
public class PageQuery extends BaseQuery {

    public PageQuery(Integer pageNum, Integer pageSize) {
        super(pageNum, pageSize);
    }
}
