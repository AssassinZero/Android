package com.rd.logic.greendao.helper;

import android.text.TextUtils;

import com.rd.logic.greendao.RDDatabaseLoader;
import com.rd.logic.greendao.dao.GestureBeanDao;
import com.rd.logic.greendao.entity.GestureBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/6/6 18:15
 * <p/>
 * Description: 手势密码 CRUD 操作
 */
@SuppressWarnings("unused")
public class GestureDaoHelper implements DaoHelperInterface {
    private GestureBeanDao dao;

    private GestureDaoHelper() {
        dao = RDDatabaseLoader.getDaoSession().getGestureBeanDao();
    }

    public static GestureDaoHelper getInstance() {
        return GesturePasswordHelperInstance.instance;
    }

    private static class GesturePasswordHelperInstance {
        static GestureDaoHelper instance = new GestureDaoHelper();
    }

    @Override
    public <T> void add(T bean) {
        if (null != dao && null != bean && bean instanceof GestureBean) {
            GestureBean gestureBean = (GestureBean) bean;
            GestureBean temp        = getByUserId(gestureBean.getUserId());
            if (null != temp) {
                gestureBean.setId(temp.getId());
            }
            dao.insertOrReplace(gestureBean);
        }
    }

    @Override
    public <T> void addAll(List<T> list) {
    }

    @Override
    public <T> T getById(String id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public void deleteAll() {
        if (null != dao) {
            dao.deleteAll();
        }
    }

    @Override
    public boolean hasKey(String id) {
        return false;
    }

    @Override
    public long getTotalCount() {
        return 0;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 特有操作
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 根据用户ID获取手势密码
     */
    public GestureBean getByUserId(String userId) {
        if (null != dao && !TextUtils.isEmpty(userId)) {
            QueryBuilder<GestureBean> qb = dao.queryBuilder();
            qb.where(GestureBeanDao.Properties.UserId.eq(userId));
            return qb.unique();
        } else {
            return null;
        }
    }

    /**
     * 根据用户ID删除手势密码
     */
    public void deleteByUserId(String userId) {
        if (null != dao && !TextUtils.isEmpty(userId)) {
            QueryBuilder<GestureBean> qb = dao.queryBuilder();
            qb.where(GestureBeanDao.Properties.UserId.eq(userId));
            qb.buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }
}