package com.rd.hnlf.module.user.dataModel.submit;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/26 14:50
 * <p/>
 * Description:
 */
public class BankcardSub {
    /** 账户名称 */
    private String accountName;
    /** 银行账号 */
    private String bankAccount;
    /** 开户银行code */
    private String bankCode;
    /** 银行卡信息id（新增不需要传，修改需要） */
    private String id;
    /** 开户行行号 */
    private String subbranchBankCode;
    /** 开户行名称 */
    private String subbranchBankName;
    /** 账户类型 */
    private String type;

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSubbranchBankCode(String subbranchBankCode) {
        this.subbranchBankCode = subbranchBankCode;
    }

    public void setSubbranchBankName(String subbranchBankName) {
        this.subbranchBankName = subbranchBankName;
    }

    public void setType(String type) {
        this.type = type;
    }
}