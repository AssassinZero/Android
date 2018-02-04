package com.rd.hnlf.module.eCommerce.dataModel.submit;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/9/13 11:01
 * <p/>
 * Description:
 */
public class ConditionSub {
    /** 票据类型 */
    private String type;
    /** 发布日期 */
    private String shelvesTime;
    /** 到期期限 */
    private String dueDate;
    /** 票面金额 */
    private String faceAmount;
    /** 所属企业 */
    private String enterpriseName;
    /** 当前页面 */
    private int    currentPage;

    public void setType(String type) {
        this.type = type;
    }

    public void setShelvesTime(String shelvesTime) {
        this.shelvesTime = shelvesTime;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setFaceAmount(String faceAmount) {
        this.faceAmount = faceAmount;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}