package com.monkeyshen.appframe.appframelibrary.api;

import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.utils.AppUtils;
import com.blankj.utilcode.utils.DeviceUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.monkeyshen.appframe.appframelibrary.network_engine.NetworkEngineManager;
import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.CallBack;
import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.UpLoadFileCallBack;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 基础页面模块api
 * 基类调用基础
 */
public abstract class BaseServerApi {


    /**
     * @param url      url
     * @param params   params
     * @param callback callback
     */
    protected void doGet(String url, Map<String, String> params, CallBack callback) {
        //如果有带http 开头的 就不加 geturl()
        if (url.startsWith("http://") || url.startsWith("https://")) {
            NetworkEngineManager.getInstance().getNetworkEngine().doGet(getUrlByGet(url, params), callback);
        } else {
            NetworkEngineManager.getInstance().getNetworkEngine().doGet(getUrlByGet(getUrl() + url, params), callback);
        }

    }

    /**
     * 发送请求
     *
     * @param url      url
     * @param params   params
     * @param callback callback
     */
    protected void doPost(String url, Map<String, String> params, CallBack callback) {
        NetworkEngineManager.getInstance().getNetworkEngine().doPost(getUrl() + url, requestMap(params), callback);
    }

    /**
     * up load file
     *
     * @param url      url
     * @param file     file
     * @param params   params
     * @param callback callback
     */
    protected void doPost(String url, File file, Map<String, String> params, UpLoadFileCallBack callback) {
        NetworkEngineManager.getInstance().getNetworkEngine().doPost(url, file, requestMap(params), callback);
    }


    /**
     * 获取url--返回测试地址还是正式地址
     *
     * @ return return
     */
    public abstract String getUrl();

    /**
     * 获取get形式的url地址
     *
     * @param url    url
     * @param params params
     * @return url
     */
    public String getUrlByGet(String url, Map<String, String> params) {
        StringBuilder builder = new StringBuilder(url);
        //这里判断url 后面是否有问号  有就不加了
        if (url != null && url.length() > 1) {
            if (!url.contains("?")) {
                builder.append("?");
            } else {
                builder.append("&");
            }
        } else {
            builder.append("?");
        }
        builder.append(joinParams(requestMap(params)));
        return builder.toString();
    }


    /**
     * get方法进行添加
     *
     * @param paramsMap paramsMap
     * @return return
     */
    public String joinParams(Map<String, String> paramsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : paramsMap.keySet()) {
            stringBuilder.append(key);
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(paramsMap.get(key) + "", "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringBuilder.append("&");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * 统一处理json
     *
     * @param request request
     * @return return
     */
    public Map<String, String> requestMap(Map<String, String> request) {
        //统一处理
        request.put("_os", "android");
        request.put("_manufacturer", Build.MANUFACTURER);
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        request.put("_model", model);
        request.put("_version", AppUtils.getAppVersionName(Utils.getContext()) + "");
        request.put("_version_os", DeviceUtils.getSDKVersion() + "");
        request.put("_language", Locale.getDefault().getLanguage() + "");
        request.put("ts", System.currentTimeMillis() + "");

//        if (UserManager.getInstance().getUser() != null && !TextUtils.isEmpty(UserManager.getInstance().getUser().getSskey())) {
//            request.put("sskey", UserManager.getInstance().getUser().getSskey());
//            request.put("uid", UserManager.getInstance().getUser().getUid());
//        }
        LogUtils.e("tag", "xxx--" + request.toString());
        return request;
    }


    /**
     * 解决 gson 转map 会把int 转为double
     */
    protected Gson gson = new GsonBuilder()
            .registerTypeAdapter(
                    new TypeToken<TreeMap<String, Object>>() {
                    }.getType(),
                    new JsonDeserializer<TreeMap<String, Object>>() {
                        @Override
                        public TreeMap<String, Object> deserialize(
                                JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {

                            TreeMap<String, Object> treeMap = new TreeMap<>();
                            JsonObject jsonObject = json.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                            for (Map.Entry<String, JsonElement> entry : entrySet) {
                                Object ot = entry.getValue();
                                if (ot instanceof JsonPrimitive) {
                                    treeMap.put(entry.getKey(), ((JsonPrimitive) ot).getAsString());
                                } else {
                                    treeMap.put(entry.getKey(), ot);
                                }
                            }
                            return treeMap;
                        }
                    }).create();
    /**
     * fromJson 转map  传 typeToken 解决int 转double bug
     */
    protected Type typeToken = new TypeToken<TreeMap<String, Object>>() {
    }.getType();
}
