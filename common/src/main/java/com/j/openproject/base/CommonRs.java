package com.j.openproject.base;

import com.j.openproject.code.CommonRsCode;
import com.j.openproject.code.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author joyuce
 * @Type CommonRs
 * @Desc 通用结果
 * @date 2019年07月01日
 * @Version V1.0
 */
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "通用结果")
public class CommonRs<T> extends ToString {

    /**
     * 序列化对象
     */
    private static final long serialVersionUID = -8226113453013682250L;

    /**
     * data 数据
     */
    @ApiModelProperty("数据")
    private T data;
    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private String code;

    /**
     * 中文信息
     */
    @ApiModelProperty("中文信息")
    private String cnMsg;

    /**
     * 英文信息
     */
    @ApiModelProperty("英文信息")
    private String enMsg;

    public CommonRs() {
        this.code = CommonRsCode.SUCCESS.getCode();
        this.cnMsg = CommonRsCode.SUCCESS.getCnMsg();
        this.enMsg = CommonRsCode.SUCCESS.getEnMsg();
    }

    public CommonRs(T data) {
        this.data = data;
        this.code = CommonRsCode.SUCCESS.getCode();
        this.cnMsg = CommonRsCode.SUCCESS.getCnMsg();
        this.enMsg = CommonRsCode.SUCCESS.getEnMsg();
    }

    public static <T> CommonRs<T> createSuccessRs(T data) {
        CommonRs<T> rs = new CommonRs<>();
        rs.setData(data);
        rs.setCode(CommonRsCode.SUCCESS.getCode());
        rs.setCnMsg(CommonRsCode.SUCCESS.getCnMsg());
        rs.setEnMsg(CommonRsCode.SUCCESS.getEnMsg());
        return rs;
    }

    public static CommonRs createWithCode(ResultCode resultCode) {
        CommonRs rs = new CommonRs();
        rs.setCode(resultCode.getCode());
        rs.setCnMsg(resultCode.getCnMsg());
        rs.setEnMsg(resultCode.getEnMsg());
        return rs;
    }

}
