package cn.util.sendCode;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.HashMap;
import java.util.Map;

public class SendCodeUtil {

    public static final Map<String, Object> sendCode(String phone){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FwKbBGscdoMcvQfpZk5", "rMEQdvYDs8mm0vWwVev0Glo1xmjdEJ");
        IAcsClient client = new DefaultAcsClient(profile);
        Map<String,Object> map=new HashMap<>();
        double random = Math.random();
        String s = String.valueOf(random * 100000);
        String code = s.substring(0, s.lastIndexOf("."));
        map.put("code","111");
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "飞虎教育");
        request.putQueryParameter("TemplateCode", "SMS_180755701");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
            String data = response.getData();
            JSONObject parse = (JSONObject) JSONObject.parse(data);
            String code1 = (String) parse.get("Code");
            if (code1.equals("OK")){
                map.put("message","发送成功");
                map.put("code",200);
                map.put("data","111");
            }else{
                map.put("message",parse.get("Message"));
                map.put("code",300);
            }
        } catch (ClientException e) {
            e.printStackTrace();
            map.put("code",500);
            map.put("message",e.getErrMsg());
        }
            return map;
        }

}