package com.yyld.conair.friend.test;

import java.lang.annotation.*;

/**
 * 保存/提交/审核的前置切面
 * <pre>
 * 开发公司：深圳文思海辉信息科技有限公司
 * 开发人员：tangzongzhong
 * 邮箱地址：zongzhong.tang@jgdt.com
 * 创建时间：2022/1/17 14:05
 * </pre>
 *
 * @author tzz
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FrontSubmit {

    //默认是之後操作
    String type() default FrontSubmitType.SAVE_TYPE;

}
