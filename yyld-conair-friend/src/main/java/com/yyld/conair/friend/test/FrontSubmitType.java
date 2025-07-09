package com.yyld.conair.friend.test;

/**
 * 前置执行类型
 * <pre>
 * 开发公司：深圳文思海辉信息科技有限公司
 * 开发人员：tangzongzhong
 * 邮箱地址：zongzhong.tang@jgdt.com
 * 创建时间：2022/1/17 16:17
 * </pre>
 *
 * @author tzz
 */
public class FrontSubmitType {

    //保存
    public final static String SAVE_TYPE = "save";
    //提交
    public final static String SUBMIT_TYPE = "submit";
    //审核
    public final static String APPROVE_TYPE = "approve";
    //检查
    public final static String CHECK_TYPE = "check";

    private final String type;

    FrontSubmitType(String type) {
        this.type = type;
    }

    /**
     * 取得key值
     * @return
     */
    public String getType() {
        return this.type;
    }

}
