package com.rd.logic.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rd.logic.greendao.dao.DaoMaster;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/6/6 10:35
 * Description: 封装的DaoMaster.OpenHelper
 * 默认的DaoMaster.OpenHelper在数据库升级的时候会删除旧的表再创建新的表，这样就会导致旧表的数据全部丢失了
 */
@SuppressWarnings("unused")
public class RDOpenHelper extends DaoMaster.OpenHelper {
    private DatabaseUpgrade databaseUpgrade;

    public RDOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, DatabaseUpgrade upgrade) {
        super(context, name, factory);
        if (null != upgrade) {
            databaseUpgrade = upgrade;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (null != databaseUpgrade) {
            databaseUpgrade.onUpgrade(db, oldVersion, newVersion);
        }
    }

    public interface DatabaseUpgrade {
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }
}