package com.better517na.JavaServiceRouteHelper.thrift.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.better517na.JavaServiceRouteHelper.business.servlet.xmlAnalyze.XmlStringAnalyze;
import com.better517na.JavaServiceRouteHelper.thrift.invoke.ThriftUtils;
import com.better517na.JavaServiceRouteHelper.util.JsonUtil;
import com.better517na.logcompontent.business.LogBusiness;

/**
 * @author guangyin
 *
 */
@Aspect
public class LoggingAspect {
    /**
     * @日志
     */
    private LogBusiness logBusiness;

    // /**
    // * @切面
    // * @param pjp 切入点
    // * @return 返回值
    // * @throws Throwable 异常
    // */
    // // @Around("@annotation(com.better517na.JavaServiceRouteHelper.thrift.log.ThriftServerACCLog)")
    // @Around("@within(com.better517na.JavaServiceRouteHelper.thrift.log.ThriftServerACCLog)")
    // public Object logACCServerAround(ProceedingJoinPoint pjp) throws Throwable {
    //
    // Object object = pjp.proceed();
    // String methodName = pjp.getSignature().getName();
    // Object[] args = pjp.getArgs();
    // String paramArgs = "";
    // if (args != null) {
    // paramArgs = JsonUtil.toJson(args);
    // }
    // ThriftUtils.get().put(XmlStringAnalyze.MAPKEY_PARAMETERS, paramArgs);
    // ThriftUtils.get().put(XmlStringAnalyze.MAPKEY_METHODNAME, methodName);
    // ThriftLogOperate.writeServerAccLog(ThriftUtils.get(), object);
    // return object;
    // }

    /**
     * @切面
     * @param pjp
     *            切入点
     * @return 返回值
     * @throws Throwable
     *             异常
     */
    // @Around("@annotation(com.better517na.JavaServiceRouteHelper.thrift.log.ThriftServerACCLog)")
    @Around("@within(com.better517na.JavaServiceRouteHelper.thrift.log.ThriftServerACCLog)")
    public Object logACCServerAround(ProceedingJoinPoint pjp) throws Throwable {
        Object object = null;
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        String paramArgs = "";
        ThriftUtils.get().put(XmlStringAnalyze.MAPKEY_METHODNAME, methodName);
        if (args != null) {
            paramArgs = JsonUtil.toJson(args);
        }
        ThriftUtils.get().put(XmlStringAnalyze.MAPKEY_PARAMETERS, paramArgs);
        try {
            object = pjp.proceed();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            ThriftLogOperate.writeServerAccLog(ThriftUtils.get(), object);
        }
    }
    /**
     * @get日志业务类方法
     * @return 返回日志业务对象
     */
    public LogBusiness getLogBusiness() {
        return logBusiness;
    }

    /**
     * @set方法
     * @param logBusiness
     *            业务对象
     */
    public void setLogBusiness(LogBusiness logBusiness) {
        this.logBusiness = logBusiness;
    }

}
