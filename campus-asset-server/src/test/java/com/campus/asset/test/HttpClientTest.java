package com.campus.asset.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Disabled("Manual HTTP client smoke test; requires an already running backend on localhost:8080.")
public class HttpClientTest {
    /**
    *测试通过httpclient发送GET方式请求
     **/
    @Test
    public void testGET() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("服务端返回数据{}"+body);

        response.close();
        httpClient.close();

    }

    @Test
    public void testPOST() throws IOException, JSONException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");

        //设置post的参数和前端一样的格式
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username","admin");
        jsonObject.put("password","123456");
        //将json对象转换成字符串，http请求只能传字符串或字节流
        StringEntity entity = new StringEntity(jsonObject.toString());
        //设置请求编码，防止搞错
        entity.setContentEncoding("utf-8");
        entity.setContentType("application/json");
        //处理好的塞进post请求中
        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);

        HttpEntity entity1 = response.getEntity();
        String string = EntityUtils.toString(entity1);
        System.out.println(string);
        response.close();
    }
}
