import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;


public class HttpsClientAuthTest {


    private static final String TEST_URL = "https://dev.htcf.com:8443/dam/swagger-ui.html";

    public static void main(String str[])
    {
        try {
            getHKVesselTrip();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getHKVesselTrip() throws Exception {
        // 客户端证书类型
        //KeyStore clientStore = KeyStore.getInstance("PKCS12");
        KeyStore clientStore = KeyStore.getInstance("JKS");
        // 加载客户端证书，即自己的私钥
        clientStore
                .load(new FileInputStream("D:/JKS/127/client.jks"),
                        "123456".toCharArray());
        // 创建密钥管理工厂实例
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        // 初始化客户端密钥库
        kmf.init(clientStore, "123456".toCharArray());
        KeyManager[] kms = kmf.getKeyManagers();
        // 创建信任库管理工厂实例
        TrustManagerFactory tmf = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // 信任库类型
        KeyStore trustStore = KeyStore.getInstance("JKS");
        // 加载信任库，即服务端公钥
        trustStore.load(new FileInputStream("D:/JKS/127/client.jks"),
                "123456".toCharArray());
        // 初始化信任库
        tmf.init(trustStore);
        TrustManager[] tms = tmf.getTrustManagers();
        // 建立TLS连接
        SSLContext sslContext = SSLContext.getInstance("TLS");
        // 初始化SSLContext
        sslContext.init(kms, tms, new SecureRandom());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpGet httpget = new HttpGet(TEST_URL);
            System.out.println("executing request" + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println(EntityUtils.toString(entity));
                }
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
