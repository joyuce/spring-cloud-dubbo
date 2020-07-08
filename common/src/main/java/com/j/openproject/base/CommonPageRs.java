package com.j.openproject.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joyuce
 * @Type CommonPageRs
 * @Desc 通用分页
 * @date 2019年11月21日
 * @Version V1.0
 */
@Getter
@Setter
@ApiModel(value = "通用分页")
public class CommonPageRs<T> extends CommonRs<T> {

    private static final long serialVersionUID = 4906634340948239347L;

    /**
     * 总共记录数
     */
    @ApiModelProperty("总共记录数")
    private int totalSize;

    public CommonPageRs(T t, int totalSize) {
        super(t);
        this.totalSize = totalSize;
    }

    public static <T> CommonPageRs<T> createSuccessRs(T data, int totalSize) {
        return new CommonPageRs<>(data, totalSize);
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
