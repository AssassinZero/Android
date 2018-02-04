package com.rd.hnlf.module.user.dataModel.receive;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/6 11:24
 * <p/>
 * Description:
 */
public class AccountInfoVIPRec {
    /** 企业名称 */
    protected String enterpriseName;
    /** 社会信用代码 */
    protected String socialCreditCode;
    /** 联系地址 */
    protected String contactAddress;
    /** 办公电话 */
    protected String officePhone;
    /** 法人姓名 */
    protected String legalPersonName;
    /** 法人身份证 */
    protected String corporateIdentityCard;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public String getCorporateIdentityCard() {
        return corporateIdentityCard;
    }
}