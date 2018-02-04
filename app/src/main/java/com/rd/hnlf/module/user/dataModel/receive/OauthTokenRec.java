package com.rd.hnlf.module.user.dataModel.receive;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/18 下午3:15
 * <p/>
 * Description: 登录信息
 */
public class OauthTokenRec {
    /** token */
    private String token;
    /** 刷新 token 值 */
    private String refreshToken;
    /** 用户 ID */
    private String userId;
    /** 用户名 */
    private String userName;
    /** 用户注册时使用的手机号 */
    private String mobile;
    /** 用户头像地址 */
    private String avatar;
    /**
     * 用户类型
     * 10 - 普通用户
     * 20 - 代理商
     * 30 - 会员用户(管理员)
     * 40 - 会员用户(普通)
     */
    private String userType;
    /** 角色 10 - 经办，20 - 复核，30 - 管理员 */
    private String role;

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserType() {
        return userType;
    }

    public String getUserTypeStr() {
        String result;
        if ("10".equals(userType)) {
            result = "普通用户";
        } else if ("20".equals(userType)) {
            result = "代理商";
        } else {
            result = "会员用户";
        }
        return result;
    }

    public String getRole() {
        return role;
    }

    /**
     * 是否是普通用户
     */
    public boolean isNormal() {
        return "10".equals(userType);
    }

    /**
     * 是否是代理商
     */
    public boolean isAgent() {
        return "20".equals(userType);
    }

    /**
     * 是否是会员用户
     */
    public boolean isVIP() {
        return "30".equals(userType) || "40".equals(userType);
    }

    /**
     * 是否是经办
     */
    public boolean isHandle() {
        return role.contains("10");
    }

    /**
     * 是否能复核
     */
    public boolean isReview() {
        return role.contains("20");
    }

    /**
     * 是否是管理员
     */
    public boolean isAdministrator() {
        return role.contains("30");
    }
}