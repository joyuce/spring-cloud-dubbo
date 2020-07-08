package com.j.openproject.code;

/**
 * @author shenxiaodong
 * @Type ResultCode
 * @Desc 通过状态码
 * @date 2019年07月01日
 * @Version V1.0
 */
public interface ResultCode {

    /**
     * 获取状态码
     *
     * @return
     */
    String getCode();

    /**
     * 获取中文信息
     *
     * @return
     */
    String getCnMsg();

    /**
     * 获取英文信息
     *
     * @return
     */
    String getEnMsg();

}
