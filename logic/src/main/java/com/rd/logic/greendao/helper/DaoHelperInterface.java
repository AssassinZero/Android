package com.rd.logic.greendao.helper;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/6/6 10:45
 * Description: 数据库DAO操作声明
 */
@SuppressWarnings("unused")
public interface DaoHelperInterface {
    <T> void add(T bean);

    <T> void addAll(List<T> list);

    <T> T getById(String id);

    List getAll();

    void deleteById(String id);

    void deleteAll();

    boolean hasKey(String id);

    long getTotalCount();
}