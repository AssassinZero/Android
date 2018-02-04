package com.rd.hnlf.module.user.dataModel.receive;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/6 10:48
 * <p/>
 * Description:
 */
public class AccountInfoRec {
    /** 企业名称 */
    protected String enterpriseName;
    /** 企业地址 */
    protected String enterpriseAddress;
    /** 个人姓名 */
    protected String name;
    /** 性别 10男 20 女 */
    protected String sex;
    /** 生日 */
    protected String birthday;
    /** 邮箱 */
    protected String email;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public String getEnterpriseAddress() {
        return enterpriseAddress;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }
}