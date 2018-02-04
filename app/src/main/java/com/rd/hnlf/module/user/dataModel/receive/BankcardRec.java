package com.rd.hnlf.module.user.dataModel.receive;

import com.google.gson.annotations.SerializedName;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/8/21 15:31
 * <p/>
 * Description:
 */
public class BankcardRec {
    /** 银行卡ID */
    private String id;
    /** 账户名称 */
    @SerializedName(value = "accountName", alternate = "holdAccountName")
    private String accountName;
    /** 银行账号 */
    private String bankAccount;
    /** 总行名称 */
    @SerializedName(value = "bankName", alternate = "bankNoName")
    private String bankName;
    /** 总行代码 */
    private String bankNo;
    /** 开户行名称 */
    @SerializedName(value = "openingBank", alternate = "subbranchBankName")
    private String openingBank;
    /** 开户行行号 */
    @SerializedName(value = "bankCode", alternate = "subbranchBankCode")
    private String bankCode;
    /**
     * 账户类型:
     * 10 - 普通账户
     * 20 - 签约账户
     */
    private String type;
    /**
     * 账户状态：
     * 10 - 启用
     * 20 - 禁用
     */
    private String state;
    /**
     * 是否已删除
     * 10 - 已删除
     * 20 - 未删除
     */
    private String isDelete;

    public String getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public String getIsDelete() {
        return isDelete;
    }
}