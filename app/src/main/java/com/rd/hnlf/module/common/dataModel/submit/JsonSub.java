package com.rd.hnlf.module.common.dataModel.submit;

import com.google.gson.Gson;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/12 10:52
 * <p/>
 * Description: Json 格式提交数据
 */
public class JsonSub {
    private String formData;
    private String dataMsg;
    private String tradeInfo;
    private String accountInfo;
    private String bankAccount;

    public JsonSub setFormData(Object formData) {
        this.formData = new Gson().toJson(formData);
        return this;
    }

    public JsonSub setDataMsg(Object dataMsg) {
        this.dataMsg = new Gson().toJson(dataMsg);
        return this;
    }

    public JsonSub setTradeInfo(Object tradeInfo) {
        this.tradeInfo = new Gson().toJson(tradeInfo);
        return this;
    }

    public JsonSub setAccountInfo(Object accountInfo) {
        this.accountInfo = new Gson().toJson(accountInfo);
        return this;
    }

    public JsonSub setBankAccount(Object bankAccount) {
        this.bankAccount = new Gson().toJson(bankAccount);
        return this;
    }
}