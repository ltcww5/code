package com.better517na.JavaServiceRouteHelper.test;


/**
 * @author yishao
 */
public class ServiceRouteHelperTest {

    /**
     *  test.
     * @param args
     *            test
     */
    public static void main(String[] args) {

        //ServiceRouteHelper serviceRouteHelper = ServiceRouteHelper.getInstance();

        // JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

        // String invokes = serviceRouteHelper.invokes(ILoginService.class, "login", new String(), "xiangqigy", "111111");
        // factoryBean.setServiceClass(ILoginService.class);
        // factoryBean.setAddress("http://172.17.42.113:8088/KRBasicInfoJavaDataService/loginServiceImpl?wsdl");
        // // factoryBean.create(ILoginService.class);]
        //
        // ILoginService loginService = (ILoginService) factoryBean.create();
        // String login = loginService.login("xiangqigy", "111111");
        // System.out.println(login);

        // http://localhost:8088/AdsManageDataService/adManageDataExpandService?wsdl

        // factoryBean.setServiceClass(IAdManageDataService.class);
        // factoryBean.setAddress("http://localhost:8088/AdsManageDataService/adManageDataService");
        // IAdManageDataService adManageDataService = (IAdManageDataService) factoryBean.create();
        // AdVo advo = new AdVo();
        // BaseService baseService = new BaseService();
        // String json = baseService.toJson(advo);
        //
        // String selectAd = adManageDataService.selectAd(json);
        //
        // System.out.println(selectAd);

        // System.out.println(invokes);

        // 测试：连接服务端
        // String invokes = serviceRouteHelper.invokes(OmsBaseDataService.class, "getOMSBaseDataServiceInfo", "test");
        // String invokes2 = serviceRouteHelper.invokes(OmsBaseDataService.class, "getOMSBaseDataServiceInfo", new String(), "test");
        // System.out.println(invokes2);
        //
        // // // 测试在服务端的心跳检测
        // Boolean invokes = serviceRouteHelper.invokes(IHeartBeat.class, "", new Boolean(false));
        // factoryBean.set
        //
        // System.out.println(invokes2);
        //
        // String invokes3 =
        // ServiceRouteHelper.getInstance().invokes(ITest.class,
        // "test", "test");
        // System.out.println(invokes3);

        // String invokes3 = serviceRouteHelper.invokes(IncomeConfirmDataService.class, "selectIncomeCfmSysOrderByStatu", "daddad");
        // System.out.println(invokes3);

        // String invokes = serviceRouteHelper.invokes(ITicketSynLogDBSevice.class, "getFailedSynBatch");
        // System.out.println(invokes);

        // AdVo advo = new AdVo();
        //
        // BaseService baseService = new BaseService();
        // String json = baseService.toJson(advo);
        //
        // String invokes = ServiceRouteHelper.getInstance().invokes(IAdManageDataService.class, "selectAd", new String(), json);
        // System.out.println(invokes);

    }
}
