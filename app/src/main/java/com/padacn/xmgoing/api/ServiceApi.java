package com.padacn.xmgoing.api;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class ServiceApi {
    //    public static final String ip = "http://192.168.0.10:8081/";
    public static final String ip = "http://app.padacn.com/";
    //用戶IP
//    public static final String ip_login = "http://192.168.0.10:8080/";
    public static final String ip_login = ip;
    public static final String HOME_URL = ip + "home";
    public static final String aboutMe = ip + "user/aboutMe";
    //產品list
    public static final String HOME_SECOND_URL = ip + "product/list";
    public static final String HOME_STRATEGY_URL = ip + "strategy/home";
    public static final String STRATEGY_DETAIL = ip + "strategy/detail";

    //產品詳情
    public static final String PRODUCT_PRODUCTDETAIL = ip + "product/ProductDetail";

    //商品排序
    public static final String PRODUCT_SORT = ip + "product/sort";

    //热门
    public static final String HOTSEARCH_LIST = ip + "hotSearch/list";
    //攻略收拾
    public static final String STRATEGY_SEARCH = ip + "strategy/search";

    public static final String ActivityList = ip + "product/recommond";


    /****************特惠中心***********/
    //特惠
    public static final String PRODUCT_REFERENCE = ip + "product/reference";
    //优惠卷中心
    public static final String COUPON_LIST = ip + "coupon/list";
    //领取优惠卷
    public static final String gain = ip + "userCoupon/gain";
    //检查库存
    public static final String checkStore = ip + "product/checkStore";

    //获取用户优惠卷
    public static final String couponUseable = ip + "coupon/useable";
    //获取用户最优优惠卷
    public static final String couponOptimal = ip + "coupon/optimal";

    /****************购物车************/
    //添加购物车buyCard/add

    public static final String addBuyCard = ip + "buyCard/add";
    //获取当前用户购物车
    public static final String userBuyCar = ip + "buyCard/userBuyCar";

    //删除购物车
    public static final String delete = ip + "buyCard/delete";


    //通过商品直接生成订单
    public static final String createOrder = ip + "order/createOrder";
    //通过购物车生成订单
    public static final String createOrderByCars = ip + "order/createOrderByCars";

    /****************个人中心************/
    //评论
    public static final String review = ip + "product/review";

    //全部订单
    public static final String userOrders = ip + "order/userOrders";
    //待支付的订单
    public static final String noPayOrders = ip + "order/noPay";
    //待出行的訂單
    public static final String unuseOrders = ip + "order/unuse";
    //待评论的订单
    public static final String noCommentOrders = ip + "order/noComment";
    //退款訂單
    public static final String refundOrders = ip + "order/refundOrders";
    //刪除訂單
    public static final String deleteOrders = ip + "order/delete";
    //订单数量角标
    public static final String orderCount = ip + "order/orderCount";
    //用戶足迹
    public static final String footprint = ip + "order/footprint";
    //訂單詳情
    public static final String orderDetail = ip + "order/detail";
    //微信支付
    public static final String wechat = ip + "pay/wechat";
    //支付寶支付
    public static final String ali = ip + "pay/ali";
    //生成退款訂單
    public static final String refundOrder = ip + "order/refundOrder";


    public static final String reviewList = ip + "product/reviewList";


    //核銷
    public static final String verify = ip + "systemOrder/verify";

    //获取常用出行人的信息
    public static final String queryContacts = ip_login + "user/queryContacts";
    //添加常用联系人
    public static final String addContacts = ip_login + "user/addContacts";

    //删除常用联系人
    public static final String delContacts = ip_login + "user/delContacts";


    //注册
    public static final String LOGIN__REGISTER = ip_login + "user/registerUser";
    public static final String VerificationCode = ip_login + "user/verificationCode";

    public static final String USER_LOGIN = ip_login + "user/login";
    //1、手机注册验证，2、手机号验证码登录，3、等三方登录绑定，
    // 4、绑定新手机号码，5设置密码和忘记密码，6、修改电话号码
    public static final String Phone_register = "1";
    public static final String Phone_code_login = "2";
    public static final String other_login = "3";
    public static final String new_phone = "4";
    public static final String setting_pass = "5";
    public static final String modify_phone = "6";


    /****用户相关接口***/

    //设置新密码
    public static final String revisePassWord = ip_login + "user/revisePassWord";
    //登錄跳轉設置密碼
    public static final String loginingSetPassWord = ip_login + "user/loginingSetPassWord";
    //设置里边设置密码和忘记密码接口API
    // 1为设置里边的密码（会验证登录状态，所以header除了pc的dev_id以外必填）    2为忘记密码按钮
    public static final String setPassWord = ip_login + "user/setPassWord";

    //修改手机号
    public static final String revisePhoneNumber = ip_login + "user/revisePhoneNumber";
    //用户基本信息
    public static final String UserInfo = ip_login + "user/queryUserAndChildInfo";

    //保存用户信息
    public static final String SaveUserInfo = ip_login + "user/updateUserInfoAndChild";

    //用户优惠卷列表
    public static final String coupons_user = ip + "coupon/user";

    //平台消息接口
    public static final String listAllNews = ip_login + "news/listAllNews";
    //用户上传图片公共接口
    public static final String uploadPicToAli = ip_login + "user/uploadPicToAli";

    //商家主页
    public static final String sellerDetail = ip + "seller/detail";

    //商家商品列表
    public static final String sellerList = ip + "product/list";


    //app协议
    public static final String protocol_app = ip + "html/protocol_app.html";
    //预订需知
    public static final String clause_app = ip + "html/clause_app.html";
    //预订条款
    public static final String reserve_app = ip + "html/reserve_app.html";

}
