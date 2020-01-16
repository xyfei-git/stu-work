package cn.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpclientUtils {

    //不变的是声明http的客户端
    private static CloseableHttpClient httpClient;

    static {
        //创建httpclient，代码只执行一次。减少客户端创建的频率，节省服务器资源。
        //你是连接另一个服务器，连接超时设置。目的是不回因为接口调不通造成大量的线程挂起，最总造成堵塞，tomcat
        //直接崩溃。
        //setConnectionRequestTimeout:设置与服务器连接的超时时间
        //setSocketTimeout:设置访问接口的超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
        //创建Http请求的客户端
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 封装的post请求方法
     *
     * @param url
     * @param parameterMap
     * @return
     */
    public static Map<String,Object> doPost(String url, Map<String, String> parameterMap) {
        //声明http请求的方式
        HttpPost httpPost = new HttpPost(url);
        Map<String,Object> map=new HashMap<>();
        //处理参数
        if (parameterMap != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            parameterMap.entrySet().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                list.add(new BasicNameValuePair(key, value));
            });
            try {
                //处理参数
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
                //将参数加入到http请求中
                httpPost.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "UTF-8");
            map = (Map<String, Object>) JSONObject.parse(s);
            return map;
        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            //服务器连接超时异常
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * PUT
     * @param url
     * @param parameterMap
     * @return
     */
    public static Map<String,Object> doPut(String url, Map<String, String> parameterMap) {
        //声明http请求的方式
        HttpPut httpPut = new HttpPut(url);
        Map<String,Object> map=new HashMap<>();
        //处理参数
        if (parameterMap != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            parameterMap.entrySet().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                list.add(new BasicNameValuePair(key, value));
            });
            try {
                //处理参数
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
                //将参数加入到http请求中
                httpPut.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "UTF-8");
            map.put("data",s);
            map.put("code",200);
            map.put("message","ok");
            return map;
        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            //服务器连接超时异常
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * GET
     * @param url
     * @return
     */
    public static Map<String,Object> doGet(String url){
        HttpGet httpGet=new HttpGet(url);
        Map<String,Object> map=new HashMap<>();
        //执行请求

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "UTF-8");
            map.put("code",200);
            map.put("data",s);
            map.put("message","ok");
            return map;
        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            //服务器连接超时异常
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * DELETE
     * @param url
     * @return
     */
    public static Map<String,Object> doDelete(String url){
        HttpDelete httpDelete=new HttpDelete(url);
        Map<String,Object> map=new HashMap<>();
        //执行请求

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "UTF-8");
            map.put("code",200);
            map.put("data",s);
            map.put("message","ok");
            return map;
        } catch (HttpHostConnectException e) {
            e.printStackTrace();
            //服务器连接超时异常
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getCause());
            return map;
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
