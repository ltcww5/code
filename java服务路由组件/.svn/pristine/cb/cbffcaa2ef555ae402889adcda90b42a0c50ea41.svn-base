package com.better517na.JavaServiceRouteHelper.business.spring;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.better517na.JavaServiceRouteHelper.business.IServiceRouteBusiness;
import com.better517na.JavaServiceRouteHelper.dao.IServiceRouteDao;
import com.better517na.JavaServiceRouteHelper.service.IServiceRouteService;
import com.better517na.logcompontent.business.LogBusiness;

/**
 * 加载spring配置文件.
 * 
 * @author yishao
 *
 */
public class LoadSpring {
    /**
     * spring加载类.
     */
    public static volatile ClassPathXmlApplicationContext context = null;

    /**
     * TODO 获取context.
     * 
     * @return context
     */
    public static ClassPathXmlApplicationContext getContext() {
        springRunChcek();
        return context;
    }

    /**
     * TODO 获取服务bean.
     * 
     * @return 服务bean
     */
    public static IServiceRouteService getIServiceRouteServiceBean() {
        springRunChcek();
        return (IServiceRouteService) context.getBean("serviceRouteService");
    }

    /**
     * TODO 获取业务bean.
     * 
     * @return 业务bean
     */
    public static IServiceRouteBusiness getIServiceRouteBusinessBean() {
        springRunChcek();
        return (IServiceRouteBusiness) context.getBean("serviceRouteBusiness");
    }

    /**
     * TODO 获取daoBean.
     * 
     * @return daoBean
     */
    public static IServiceRouteDao getIServiceRouteDaoBean() {
        springRunChcek();
        return (IServiceRouteDao) context.getBean("serviceRouteDao");
    }

    /**
     * TODO 获取sqlSession bean.
     * 
     * @return sqlSession bean
     */
    public static SqlSessionTemplate getSqlSessionRead() {
        springRunChcek();
        return (SqlSessionTemplate) context.getBean("sqlSessionRead");
    }

    /**
     * . 从外部服务的spring容器中引入日志组件
     * 
     * @return LogBusiness
     */
    public static LogBusiness getLogBusiness() {
        springRunChcek();
        return (LogBusiness) context.getBean("logBusiness");
    }

    /**
     * . 用于保证所有的动作spring能够处理.
     */
    private static void springRunChcek() {
        if (context == null) {
            synchronized (LoadSpring.class) {
                if (context == null) {
                    // 加载配置文件
                    // "com/better517na/config/spring/app-context-service.xml",
                    context = new ClassPathXmlApplicationContext(new String[]{
                        "com/better517na/serviceRoute/config/spring/app-context.xml",
                        "com/better517na/serviceRoute/config/spring/app-context-business.xml", 
                        "com/better517na/serviceRoute/config/spring/app-context-dao.xml",
                        "com/better517na/serviceRoute/config/spring/app-context-mybatis.xml" });
                    context.start();
                }
            }
        }
    }
}
