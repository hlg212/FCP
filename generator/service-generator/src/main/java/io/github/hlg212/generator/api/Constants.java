package io.github.hlg212.generator.api;

/**
 * @author ww
 */
public class Constants {
    public final static String XXL_PATH = "${htcf.feign.xxl.name:xxl-job-admin}";


    public final static String APP_APIGATEWAY_XXL = "${htcf.feign."+ XXL_PATH + ".gateway:apiGateway}";

    public static class AppFeignUrl
    {
        public final static String XXL_JOB =  "${htcf.feign."+ XXL_PATH + ".url:}";

    }

    public static class ApiMapping
    {
        public final static String INFO = "/jobinfo";
        public final static String LOG = "/joblog";
    }

}
