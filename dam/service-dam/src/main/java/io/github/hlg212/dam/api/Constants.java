package io.github.hlg212.dam.api;

/**
 * @author ww
 */
public class Constants {


    public static class ApiUrl {
        public final static String OrgTreeApi = "${fcf.feign.OrgTreeApi.url:}";
    }


    public static class ApiName
    {
        public final static String OrgTreeApi = "${fcf.feign.OrgTreeApi.name:apiGateway}";
    }


    public static class ApiPath
    {
        public final static String OrgTreeApi = "${fcf.feign.OrgTreeApi.path:basic}";
    }

    public static class ApiMapping
    {
        public final static String OrgTreeApi =  "/orgTree";

    }


    public static class ApiContextId
    {
        public final static String OrgTreeApi = "OrgTreeApi";
    }


}
