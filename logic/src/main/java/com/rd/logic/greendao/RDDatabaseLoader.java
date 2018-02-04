package com.rd.logic.greendao;

import android.database.sqlite.SQLiteDatabase;

import com.rd.logic.greendao.dao.DaoMaster;
import com.rd.logic.greendao.dao.DaoSession;
import com.rd.tools.utils.ContextHolder;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/6/6 10:33
 * <p/>
 * Description: 数据库加载
 * 在Application中调用 RDDatabaseLoader.init(...)方法
 */
@SuppressWarnings("unused")
public class RDDatabaseLoader {
    private static DaoSession daoSession;

    public static void init(String databaseName, com.rd.logic.greendao.RDOpenHelper.DatabaseUpgrade upgrade) {
        com.rd.logic.greendao.RDOpenHelper helper    = new com.rd.logic.greendao.RDOpenHelper(ContextHolder.getContext(), databaseName, null, upgrade);
        SQLiteDatabase                             db        = helper.getWritableDatabase();
        DaoMaster                                  daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}