package com.j.openproject.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author joyuce
 * @Type CommPageRq
 * @Desc 通用分页请求头
 * @date 2019年07月01日
 * @Version V1.0
 */
@Getter
@Setter
@ApiModel(value = "通用分页请求头")
public class CommPageRq extends CommonRq {

    private static final long serialVersionUID = 6245299817274735431L;

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("页大小")
    private Integer pageSize;

}
