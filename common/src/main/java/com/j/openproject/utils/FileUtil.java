package com.j.openproject.utils;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Joyuce
 * @Type FileUtil
 * @Desc
 * @date 2019年12月06日
 * @Version V1.0
 */
@Slf4j
public class FileUtil {

    /**
     * 文件或文件夹不存在则创建
     *
     * @param dir      文件夹
     * @param filepath 文件名
     */
    public static void createFile(String dir, String filepath) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(dir + filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("文件创建失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件，如果文件夹不存在将被创建
     *
     * @param destFileName 文件路径
     */
    public static File createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            return null;
        }
        if (destFileName.endsWith(File.separator)) {
            return null;
        }
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                return null;
            }
        }
        try {
            if (file.createNewFile()) {
                return file;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
