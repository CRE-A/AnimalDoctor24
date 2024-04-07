package com.jnb.animaldoctor24.global.common;

public class CommonDataHolder {
    private static final ThreadLocal<CommonData> context = new ThreadLocal<>();

    private CommonDataHolder() {
    }

    public static void resetCommonData() {
        context.remove();
    }

    public static void setCommonData(CommonData commonData) {
        if (commonData == null) {
            resetCommonData();
        } else {
            context.set(commonData);
        }
    }

    public static CommonData getCommonData() {
        return context.get();
    }
}
