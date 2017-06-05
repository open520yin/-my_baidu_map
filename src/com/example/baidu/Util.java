package com.example.baidu;

import android.os.Handler;  
import android.os.Message;  
  
import org.json.JSONArray;  
import org.json.JSONException;  
import org.json.JSONObject;  
  
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.util.ArrayList;  
import java.util.List;  
  
public class Util {  
    /**  
     * @发送消息到消息队列中  
     * @param hander  
     * @param msg  
     * @param data  
     */  
    public static void sendMsg(Handler hander,Message msg,String data){  
        msg.what =1;  
        msg.obj = data;  
        hander.sendMessage(msg);  
    }  
    /**  
     * @发送消息到消息队列中  
     * @param hander  
     * @param msg  
     * @param data  
     */  
    public static void sendMsg(Handler hander,Message msg,List data){  
        msg.what =1;  
        msg.obj = data;  
        hander.sendMessage(msg);  
    }  
  
    /**  
     * @获取post请求  
     * @param url  
     * @param id  
     * @return  
     */  
    public static String httpPost(String url,int id){  
        String params = "act=1";  
        params = params +"&vid="+id;  
        String data = null;  
        HttpURLConnection conn = null;  
        try{  
            //get request  
            URL address = new URL(url);  
            conn = (HttpURLConnection) address.openConnection();  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");  
            conn.setRequestProperty("Content-Length", String.valueOf(params.getBytes().length));  
            conn.setDoInput(true);  
            conn.setDoOutput(true);  
            conn.getOutputStream().write(params.getBytes());//将参数写入输出流  
            //get outinput  
            StringBuilder sb = new StringBuilder();  
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String msg = "";  
            while((msg = br.readLine())!=null){  
                sb.append(msg);  
            }  
            data = sb.toString();  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally {  
            if(conn != null){  
                conn.disconnect();  
            }  
        }  
        System.out.println("获取结果为：" + data);  
        return data;  
    }  
  
    /**  
     * @get请求  
     * @param url  
     * @return  
     */  
    public static String httpGet(String url){  
        String data = null;  
        HttpURLConnection conn = null;  
        try{  
            URL address = new URL(url);  
            conn = (HttpURLConnection) address.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setDoInput(true);  
            StringBuilder sb = new StringBuilder();  
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String msg = "";  
            while((msg = br.readLine())!=null){  
                sb.append(msg);  
            }  
            data = sb.toString();  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally {  
            if(conn != null){  
                conn.disconnect();  
            }  
        }  
        return data;  
    }  
  
    /**  
     * @解析json存为字符串  
     * @param data  
     * @return  
     */  
    public static String  parseJson2String(String data){  
        String result = "";  
        try{  
            JSONObject object = new JSONObject(data);  
            int status  = object.getInt("status");  
            if(status == 200){  
                JSONArray item = object.getJSONArray("result");  
              for(int i=0;i<item.length();i++){  
                  JSONObject tmpObj = item.getJSONObject(i);  
                  String name = tmpObj.getString("name");  
                  int age = tmpObj.getInt("age");  
                  String address = tmpObj.getString("address");  
                  JSONObject study = tmpObj.getJSONObject("study");  
                  String no = study.getString("no");  
                  String teacher = study.getString("teacher");  
                  result += "姓名："+name+" 年纪："+age+" 地址："+address+" 学号："+no+" 老师："+teacher+"\n";  
              }  
  
            }else{  
                result = "获取结果失败！";  
            }  
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
     
}  