package io.github.hlg212.task.api;


public class Constants {

    public final static String API_PREFIX = "";

    public static class ApiUrl {
        public final static String JobCenterApi = "${fcf.feign.JobCenterApi.url:}";
        public final static String JobLogCenterApi = "${fcf.feign.JobLogCenterApi.url:}";
        public final static String JobCenterLoginApi = "${fcf.feign.JobCenterLoginApi.url:}";
    }


    public static class ApiName
    {
        public final static String JobCenterApi = "${fcf.feign.JobCenterApi.name:apiGateway}";
        public final static String JobLogCenterApi = "${fcf.feign.JobLogCenterApi.name:apiGateway}";
        public final static String JobCenterLoginApi = "${fcf.feign.JobCenterLoginApi.name:apiGateway}";
    }


    public static class ApiPath
    {
        public final static String JobCenterApi = "${fcf.feign.JobCenterApi.path:xxl-job-admin}";
        public final static String JobLogCenterApi = "${fcf.feign.JobLogCenterApi.path:xxl-job-admin}";
        public final static String JobCenterLoginApi = "${fcf.feign.JobCenterLoginApi.path:xxl-job-admin}";
    }

    public static class ApiMapping
    {
        public final static String JobCenterApi =  "/jobinfo";
        public final static String JobLogCenterApi = "/joblog";
        public final static String JobCenterLoginApi = "/joblog";

    }


    public static class ApiContextId
    {
        public final static String JobCenterApi = "JobCenterApi";
        public final static String JobLogCenterApi = "JobLogCenterApi";
        public final static String JobCenterLoginApi = "JobCenterLoginApi";
    }

}
