package com.rd.logic.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/15 16:39
 * <p/>
 * Description: 关联用户ID的手势密码
 */
@SuppressWarnings("unused")
@Entity
public class GestureBean {
    /** 自增长主键 */
    @Id(autoincrement = true)
    private Long    id;
    /** 用户ID */
    private String  userId;
    /** 加密后的手势密码 */
    private String  password;
    /** 错误次数 */
    private int     errorCount;
    /** 是否启用手势密码功能 */
    private boolean enable;

    @Generated(hash = 2146160940)
    public GestureBean(Long id, String userId, String password, int errorCount, boolean enable) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.errorCount = errorCount;
        this.enable = enable;
    }

    @Generated(hash = 438617647)
    public GestureBean() {
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean getEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}