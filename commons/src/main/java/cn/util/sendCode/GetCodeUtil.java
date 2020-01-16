package cn.util.sendCode;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class GetCodeUtil {

private static String curTime=String.valueOf((new Date()).getTime() / 1000L);

private static String url="https://api.netease.im/sms/sendcode.action";

private static String appKey="b088afec7a30108796b4b85fe4355118";

private static String contentType="application/x-www-form-urlencoded";

private static String nonce=UUID.randomUUID().toString().replace("-","");

private static String checkSum= CheckSumBuilder.getCheckSum("73721cd8a9b1",nonce,String.valueOf(curTime));

    //创建httpClent 客户端
 private static CloseableHttpClient httpClient;
static{
    //创建httpclient，代码只执行一次。减少客户端创建的频率，节省服务器资源。
    //你是连接另一个服务器，连接超时设置。目的是不回因为接口调不通造成大量的线程挂起，最总造成堵塞，tomcat
    //直接崩溃。
    //setConnectionRequestTimeout:设置与服务器连接的超时时间
    //setSocketTimeout:设置访问接口的超时时间
    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
    //创建Http请求的客户端
    httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
}


public static String sendMessage(String authCode,String mobile){
    HttpPost post=new HttpPost(url);
    List<NameValuePair> list=new ArrayList<>();
    list.add(new BasicNameValuePair("mobile",mobile));
    list.add(new BasicNameValuePair("authCode",authCode));
    UrlEncodedFormEntity entity=null;
    try {
        entity=new UrlEncodedFormEntity(list,"UTF-8");
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
    post.setEntity(entity);
    post.setHeader("AppKey",appKey);
    post.setHeader("Nonce",nonce);
    post.setHeader("CurTime",String.valueOf(curTime));
    post.setHeader("Content-Type",contentType);
    post.setHeader("CheckSum", checkSum);
    String s=null;
    try {
        CloseableHttpResponse execute = httpClient.execute(post);
        s = EntityUtils.toString(execute.getEntity());
    } catch (IOException e) {
        e.printStackTrace();
    }
    return s;
}

}
