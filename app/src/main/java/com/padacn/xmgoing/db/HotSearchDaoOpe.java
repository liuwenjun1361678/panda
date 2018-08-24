package com.padacn.xmgoing.db;

import android.content.Context;

import com.padacn.xmgoing.bean.HotSearch;
import com.padacn.xmgoing.dao.HotSearchDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29 0029.
 */

public class HotSearchDaoOpe {

    /**
     * 添加数据至数据库
     *
     * @param context
     * @param hotSearch
     */
    public static void insertData(Context context, HotSearch hotSearch) {
        DBManager.getDaoSession(context).getHotSearchDao().insert(hotSearch);
    }

    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertData(Context context, List<HotSearch> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DBManager.getDaoSession(context).getHotSearchDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param hotSearch
     */
    public static void saveData(Context context, HotSearch hotSearch) {
        DBManager.getDaoSession(context).getHotSearchDao().save(hotSearch);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param hotSearch 删除具体内容
     */
    public static void deleteData(Context context, HotSearch hotSearch) {
        DBManager.getDaoSession(context).getHotSearchDao().delete(hotSearch);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        DBManager.getDaoSession(context).getHotSearchDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DBManager.getDaoSession(context).getHotSearchDao().deleteAll();
    }

    /**
     * 更新数据库
     *
     * @param context
     * @param hotSearch
     */
    public static void updateData(Context context, HotSearch hotSearch) {
        DBManager.getDaoSession(context).getHotSearchDao().update(hotSearch);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<HotSearch> queryAll(Context context) {
        QueryBuilder<HotSearch> builder = DBManager.getDaoSession(context).getHotSearchDao().queryBuilder();

        return builder.build().list();
    }


    /**
     * 分页加载
     *
     * @param context
     * @param pageSize 当前第几页(程序中动态修改pageSize的值即可)
     * @param pageNum  每页显示多少个
     * @return
     */
    public static List<HotSearch> queryPaging(int pageSize, int pageNum, Context context) {
        HotSearchDao studentDao = DBManager.getDaoSession(context).getHotSearchDao();
        List<HotSearch> listMsg = studentDao.queryBuilder()
                .offset(pageSize * pageNum).limit(pageNum).list();
        return listMsg;
    }


}
