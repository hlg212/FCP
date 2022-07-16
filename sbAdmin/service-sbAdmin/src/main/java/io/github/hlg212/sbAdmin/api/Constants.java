package io.github.hlg212.sbAdmin.api;

/**
 * @author ww
 */
public class Constants {


    public final static String APP_SBADMIN_PATH = "${fcf.feign.sbadmin.name:sbadmin}";

    public final static String APP_APIGATEWAY_SBADMIN = "${fcf.feign."+ APP_SBADMIN_PATH + ".gateway:apiGateway}";


    public static class AppFeignUrl
    {
        public final static String APP_SBADMIN  =  "${fcf.feign."+ APP_SBADMIN_PATH + ".url:}";

    }

    public static class ApiMapping
    {
    }
}
