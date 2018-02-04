package com.rd.logic.greendao.helper;

import android.text.TextUtils;

import com.rd.logic.greendao.RDDatabaseLoader;
import com.rd.logic.greendao.dao.RegionBeanDao;
import com.rd.logic.greendao.entity.RegionBean;
import com.rd.tools.utils.ConverterUtil;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/6/6 11:36
 * <p/>
 * Description: 省市区 CRUD 操作
 */
@SuppressWarnings("unused")
public class RegionDaoHelper implements DaoHelperInterface {
    private RegionBeanDao dao;

    private RegionDaoHelper() {
        dao = RDDatabaseLoader.getDaoSession().getRegionBeanDao();
    }

    public static RegionDaoHelper getInstance() {
        return RegionBeanDaoHelperInstance.instance;
    }

    private static class RegionBeanDaoHelperInstance {
        static RegionDaoHelper instance = new RegionDaoHelper();
    }

    @Override
    public <T> void add(T bean) {
        if (null != dao && null != bean) {
            dao.insertOrReplace((RegionBean) bean);
        }
    }

    @Override
    public <T> void addAll(final List<T> list) {
        dao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (Object bean : list) {
                    add(bean);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public RegionBean getById(String id) {
        if (null != dao && !TextUtils.isEmpty(id)) {
            return dao.load(ConverterUtil.getLong(id));
        }
        return null;
    }

    @Override
    public List getAll() {
        if (null != dao) {
            return dao.loadAll();
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        if (null != dao && !TextUtils.isEmpty(id)) {
            dao.deleteByKey(ConverterUtil.getLong(id));
        }
    }

    @Override
    public void deleteAll() {
        if (null != dao) {
            dao.deleteAll();
        }
    }

    @Override
    public boolean hasKey(String id) {
        if (null == dao || TextUtils.isEmpty(id)) {
            return false;
        }
        QueryBuilder<RegionBean> qb = dao.queryBuilder();
        qb.where(RegionBeanDao.Properties.Id.eq(id));
        return qb.buildCount().count() > 0;
    }

    @Override
    public long getTotalCount() {
        if (null == dao) {
            return 0;
        }
        QueryBuilder<RegionBean> qb = dao.queryBuilder();
        return qb.buildCount().count();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 特有操作
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取所有省份
     */
    public ArrayList<RegionBean> getProvinces() {
        if (null == dao) {
            return null;
        }
        QueryBuilder<RegionBean> qb = dao.queryBuilder();
        qb.where(RegionBeanDao.Properties.Pid.eq("0"));
        qb.orderAsc(RegionBeanDao.Properties.Nid);
        return new ArrayList<>(qb.list());
    }

    /**
     * 获取所有市
     */
    public ArrayList<RegionBean> getCities() {
        if (null == dao) {
            return null;
        }
        Query<RegionBean> query = dao.queryRawCreate(", REGION B WHERE T.PID = B.NID AND B.PID = 0 ORDER BY T.PID ASC");
        return new ArrayList<>(query.list());
    }

    /**
     * 获取所有区
     */
    public ArrayList<RegionBean> getAreas() {
        if (null == dao) {
            return null;
        }
        Query<RegionBean> query = dao.queryRawCreate(", REGION B WHERE T.PID = B.NID AND B.PID != 0 ORDER BY T.PID ASC");
        return new ArrayList<>(query.list());
    }
}