package com.padacn.xmgoing.model;

import java.util.List;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class CommonDataBean {

    /**
     * msg : 列表获取出错，原因：
     ### Error querying database. Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 'p.city_id' in 'where clause'
     ### The error may exist in cn/xst/pandagroup/core/mapper/ProductMapper.xml
     ### The error may involve cn.xst.pandagroup.core.mapper.ProductMapper.selectListProduct-Inline
     ### The error occurred while setting parameters
     ### SQL: select p.pro_id pid, p.pro_sn, p.pro_name pname, p.pro_num, p.sale_num, p.pro_intro, p.isRecommend, p.recommend_sort, p.city, p.place, p.pro_type, p.type_name, p.use_the_day, p.isBanner, p.seller_id, p.seller_name,p.travel_number, p.statu, p.start_day,p.end_day, p.day_remarks, p.view_count, p.column_id, p.column_name, p.use_IDCard,p.start_place,p.age_mark,p.max_buy,p.pro_theme, p.theme_name,p.theme_mark,p.type_mark,p.price,p.home_mark,p.city_id,p.isReference,p.reference_price,p.type_parent_id,p.destination,p.refund_time,p.sale_time,p.trip_mode, p.sale_price,p.line_price,p.subheading,p.isPersonData,p.duration,p.duration_name,p.price_range,p.range_name, i.pro_pic_id, i.pro_name iname, i.pro_id iid, i.path, i.pic_key,i.height,i.width, i.pic_create_time, l.pro_id lid,l.label_id,l.label_name , p.pro_detail from (SELECT * FROM xst_product WHERE p.city_id=? limit ?,?) p LEFT JOIN xst_pro_pic i on p.pro_id=i.pro_id LEFT JOIN xst_pro_type t ON p.pro_type=t.pro_type_id LEFT JOIN xst_pro_label_login l on p.pro_id=l.pro_id WHERE p.city_id=? GROUP BY p.pro_id
     ### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 'p.city_id' in 'where clause'
     ; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 'p.city_id' in 'where clause'
     * result : 0
     * data : []
     */

    private String msg;
    private int result;
    private List<?> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
