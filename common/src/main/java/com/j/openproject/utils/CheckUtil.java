package com.j.openproject.utils;

import org.apache.commons.lang3.StringUtils;

import com.j.openproject.code.CommonRsCode;
import com.j.openproject.exception.CommonException;

/**
 * @author shenxiaodong
 * @Type CheckUtil
 * @Desc 校验工具类
 * @date 2019年08月07日
 * @Version V1.0
 */
public class CheckUtil {

    public static void checkInsert(int r) throws CommonException {
        if (r == 0) {
            throw new CommonException(CommonRsCode.DB_INSERT_ERROR);
        }
    }

    public static void checkString(String... strings) throws CommonException {
        for (String e : strings) {
            if (StringUtils.isBlank(e)) {
                throw new CommonException(CommonRsCode.VALID_ERROR);
            }
        }
    }

    public static boolean checkStringTrue(String... strings) {
        for (String e : strings) {
            if (StringUtils.isBlank(e)) {
                return false;
            }
        }
        return true;
    }

    public static void checkUpdate(int r) throws CommonException {
        if (r == 0) {
            throw new CommonException(CommonRsCode.DB_UPDATE_ERROR);
        }
    }

    public static void checkInteger(Integer... nums) throws CommonException {
        for (Integer e : nums) {
            if (e == null || e < 0) {
                throw new CommonException(CommonRsCode.VALID_ERROR);
            }
        }

    }

    public static boolean checkIntegerTrue(Integer... nums) {
        for (Integer e : nums) {
            if (e == null || e < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkStartsWith(String URI, String[] noAuthStartsWithUrls) {
        for (String url : noAuthStartsWithUrls) {
            if (URI.startsWith(url)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkContains(String URI, String[] noAuthContainsUrls) {
        for (String url : noAuthContainsUrls) {
            if (URI.contains(url)) {
                return true;
            }
        }
        return false;
    }

}
