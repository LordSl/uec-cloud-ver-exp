package com.uec.ws1.config;

import com.uec.ws1.util.GlobalJedis;
import com.uec.ws1.util.GlobalLogger;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {
    @Autowired
    GlobalJedis jedis;
    @Autowired
    GlobalLogger logger;

    @Autowired
    public void init() throws Exception {
        regInGateHash();
        logger.log("config finish");
    }

    private void regInGateHash() throws Exception{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        String uriStr = "http://localhost:8083";
        HttpGet httpGet = new HttpGet("http://localhost:8089/hash/addEndPoint?uriStr="+uriStr);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            logger.log("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                logger.log("响应内容长度为:" + responseEntity.getContentLength());
                logger.log("响应内容为:" + EntityUtils.toString(responseEntity));
                logger.log("已在gate注册");
            }
        } finally {
            if(httpClient!=null) httpClient.close();
            if(response!=null) response.close();
        }
    }
}
