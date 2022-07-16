package io.github.hlg212.gatewayAdmin.api;

public class Constants {

    public final static String APP_RC_PATH = "${fcf.feign.log.name:rc}";

    public final static String APP_APIGATEWAY_RC = "${fcf.feign."+ APP_RC_PATH + ".gateway:apiGateway}";

    public static class AppFeignUrl
    {
        public final static String APP_RC =  "${fcf.feign."+ APP_RC_PATH + ".url:}";

    }

    public static class ApiMapping
    {
        public final static String eureka = "/eureka";
    }

}
