package com.rd.hnlf.module.user.dataModel.submit;

import com.rd.hnlf.module.user.dataModel.receive.AccountInfoVIPRec;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/6 15:47
 * <p/>
 * Description:
 */
public class AccountInfoVIPSub extends AccountInfoVIPRec {
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public void setCorporateIdentityCard(String corporateIdentityCard) {
        this.corporateIdentityCard = corporateIdentityCard;
    }
}