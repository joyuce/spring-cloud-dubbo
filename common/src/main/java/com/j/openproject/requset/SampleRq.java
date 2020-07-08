package com.j.openproject.requset;

import com.j.openproject.base.CommonRq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joyuce
 * @Type SampleRq
 * @Desc
 * @date 2019年11月22日
 * @Version V1.0
 */
@Getter
@Setter
@ApiModel(value = "样例")
public class SampleRq extends CommonRq {

    private static final long serialVersionUID = -9092966661916829885L;

    @ApiModelProperty(value = "样例", required = false)
    private String sample;
}
