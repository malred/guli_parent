package com.malguy.commonutils;

import java.util.UUID;

/**
 * @author malguy-wang sir
 * @create ---
 */
public class UploadUtils {
    /**
     * 获取随机名称
     *
     * @param realName 真实名称
     * @return uuid 随机名称
     */
    public static String getUUIDName(String realName) {
        //realname  可能是  1sfasdf.jpg   也可能是 1sfasdf 1
        //获取后缀名
        int index = realName.lastIndexOf(".");
        if (index == -1) {//如果没有后缀
            return UUID.randomUUID().toString().replace("-", "").toUpperCase();
        } else {
            return UUID.randomUUID().toString().replace("-", "")
                    .toUpperCase() + realName.substring(index);
        }
    }
}
