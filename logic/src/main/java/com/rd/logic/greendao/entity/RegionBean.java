package com.rd.logic.greendao.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/15 16:40
 * <p/>
 * Description: 省市区
 */
@Entity(nameInDb = "REGION")
public class RegionBean implements IPickerViewData {
    /** 自增长主键 */
    @Id(autoincrement = true)
    private Long   id;
    /** 节点 */
    private long   nid;
    /** 父节点 */
    private long   pid;
    /** 名称 */
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPid() {
        return this.pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getNid() {
        return this.nid;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1168794884)
    public RegionBean(Long id, long nid, long pid, String name) {
        this.id = id;
        this.nid = nid;
        this.pid = pid;
        this.name = name;
    }

    @Generated(hash = 1853684008)
    public RegionBean() {
    }

    public String getPickerViewText() {
        // return name.replaceAll("(?:自治区|自治州)", "");
        return name;
    }
}