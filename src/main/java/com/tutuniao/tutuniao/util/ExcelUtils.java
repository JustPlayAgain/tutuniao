package com.tutuniao.tutuniao.util;

public final class ExcelUtils {

    /**
     * 判断当前excel版本是否为03版本
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 判断当前excel版本是否为07版本
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
