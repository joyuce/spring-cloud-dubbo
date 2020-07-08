package com.j.openproject.base;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * @author joyuce
 * @Type ToString
 * @Desc
 * @date 2019年07月01日
 * @Version V1.0
 */
public class ToString implements Serializable {

    private static final long serialVersionUID = -3898186760262960397L;

    protected ToString() {
    }

    @Override
    public String toString() {
        return toString(this);
    }

    private static String toString(Object obj) {
        return JSONObject.toJSONString(obj);
    }
}
