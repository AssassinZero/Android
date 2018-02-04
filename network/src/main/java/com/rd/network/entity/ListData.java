package com.rd.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/5/11 11:26
 * <p/>
 * Description: 反序列化带page的List数据
 */
public class ListData<T> {
    /** list数据 */
    @SerializedName(Params.LIST)
    private List<T> list;
    @SerializedName(Params.PAGE)
    private PageMo  page;

    public List<T> getList() {
        return list;
    }

    public PageMo getPage() {
        return page;
    }
}