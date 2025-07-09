package com.yyld.conair.friend.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 保存/提交/审核的前置切面
 *
 * @author tzz
 * @Aspect:作用是把当前类标识为一个切面供容器读取
 * @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
 * @Around：环绕增强，相当于MethodInterceptor
 * @AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
 * @Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
 * @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
 * @After: final增强，不管是抛出异或者正常退出都会执行
 * <pre>
 * 开发公司：深圳文思海辉信息科技有限公司
 * 开发人员：tangzongzhong
 * 邮箱地址：zongzhong.tang@jgdt.com
 * 创建时间：2022/1/17 14:05
 * </pre>
 */
@Component
@Aspect
public class FrontSubmitAspect {



    /**
     * 定义切入点，使用了 @RemoveInVoice 的方法
     */
    @Pointcut("@annotation(com.yyld.conair.friend.test.FrontSubmit)")
    public void frontSubmit() {

    }

    /**
     * 执行之前拦截，提前执行操作
     *
     * @param point ProceedingJoinPoint
     */
    @Before("frontSubmit()")
    public void execute(JoinPoint point) {

        System.out.println("222222222222222222222222");


    }

    /**
     * 执行后置拦截配置【后置增强，相当于AfterReturningAdvice，方法正常退出时执行】
     *
     * @param point
     * @param result
     */
    @AfterReturning(value = "frontSubmit()", returning = "result")
    public void afterReturning(JoinPoint point, Object result) {

        System.out.println("000000000000000000000");
    }

    /**
     * 执行后置拦截配置【final增强，不管是抛出异或者正常退出都会执行】
     *
     * @param point
     */
    @After("frontSubmit()")
    public void doAfter(JoinPoint point) {
        System.out.println("88888888888888888");
    }

}
