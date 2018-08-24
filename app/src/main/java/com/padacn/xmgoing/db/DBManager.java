package com.padacn.xmgoing.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.padacn.xmgoing.dao.DaoMaster;
import com.padacn.xmgoing.dao.DaoSession;

/**
 * Created by Administrator on 2018/5/29 0029.
 */

public class DBManager {

    // 是否加密
    public static final boolean ENCRYPTED = true;

    private static final String DB_NAME = "hotsearch.db";
    private static DBManager mDbManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private Context mContext;

    private DBManager(Context context) {
        this.mContext = context;
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        getDaoMaster(context);
        getDaoSession(context);
    }

    public static DBManager getInstance(Context context) {
        if (null == mDbManager) {
            synchronized (DBManager.class) {
                if (null == mDbManager) {
                    mDbManager = new DBManager(context);
                }
            }
        }
        return mDbManager;
    }


    /**
     * 获取可读数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }

        return mDevOpenHelper.getWritableDatabase();
    }


    /**
     * 获取DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (null == mDaoMaster) {
            synchronized (DBManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase(context));
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * 获取DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (null == mDaoSession) {
            synchronized (DBManager.class) {
                mDaoSession = getDaoMaster(context).newSession();
            }
        }
        return mDaoSession;
    }

    //    /**
//     * 获取DaoMaster
//     *
//     * 判断是否存在数据库，如果没有则创建数据库
//     * @param context
//     * @return
//     */
//    public static DaoMaster getDaoMaster(Context context) {
//        if (null == mDaoMaster) {
//            synchronized (DbManager.class) {
//                if (null == mDaoMaster) {
//                    MyOpenHelper helper = new MyOpenHelper(context,DB_NAME,null);
//                    mDaoMaster = new DaoMaster(helper.getWritableDatabase());
//                }
//            }
//        }
//        return mDaoMaster;
//    }
}
