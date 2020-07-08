package com.j.openproject.base;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joyuce
 * @Type CommonRq
 * @Desc 通用请求头
 * @date 2019年11月21日
 * @Version V1.0
 */
@Getter
@Setter
@ApiModel(value = "通用请求头")
public class CommonRq extends ToString {

    /**
     * 序列化对象
     */
    private static final long serialVersionUID = -6513466202703193417L;

    @NotNull(message = "from不为null")
    @ApiModelProperty(value = "来源 0微信小程序", required = true)
    private Integer from;

    @NotBlank(message = "timestamp不为空")
    @ApiModelProperty(value = "时间戳", required = true)
    public String timestamp;

    @NotBlank(message = "signature不为空")
    @ApiModelProperty(value = "签名", required = true)
    public String signature;

    @NotBlank(message = "token不能为空")
    @ApiModelProperty(value = "登录口令", required = true)
    public String token;

    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("请求id")
    private String requestId;

}
