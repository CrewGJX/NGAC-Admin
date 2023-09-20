package com.phor.ngac.config;

public class AuthUtils {
    private static final ThreadLocal<String> loginUserNameThreadLocal = new ThreadLocal<>();

    public static String getLoginUserName() {
        String loginUserName = loginUserNameThreadLocal.get();
        loginUserNameThreadLocal.remove();
        return loginUserName;
    }

    public static void setLoginUserName(String userName) {
        loginUserNameThreadLocal.set(userName);
    }
}
