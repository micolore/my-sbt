package com.kubrick.jsbt.sso.client.sso.client.util;

import com.kubrick.jsbt.sso.client.sso.client.constant.Oauth2Constant;
import com.kubrick.jsbt.sso.client.sso.client.enums.GrantTypeEnum;
import com.kubrick.jsbt.sso.client.sso.client.rpc.Result;
import com.kubrick.jsbt.sso.client.sso.client.rpc.RpcAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author k
 * @version 1.0.0
 * @ClassName Oauth2Utils
 * @description: TODO
 * @date 2021/2/7 下午3:35
 */
public class Oauth2Utils {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2Utils.class);

    /**
     * 获取accessToken（密码模式，app通过此方式由客户端代理转发http请求到服务端获取accessToken）
     *
     * @param serverUrl
     * @param appId
     * @param appSecret
     * @param username
     * @param password
     * @return
     */
    public static Result<RpcAccessToken> getAccessToken(String serverUrl, String appId, String appSecret, String username,
                                                        String password) {
        Map<String, String> paramMap = new HashMap<>(5);
        paramMap.put(Oauth2Constant.GRANT_TYPE, GrantTypeEnum.PASSWORD.getValue());
        paramMap.put(Oauth2Constant.APP_ID, appId);
        paramMap.put(Oauth2Constant.APP_SECRET, appSecret);
        paramMap.put(Oauth2Constant.USERNAME, username);
        paramMap.put(Oauth2Constant.PASSWORD, password);
        return getHttpAccessToken(serverUrl + Oauth2Constant.ACCESS_TOKEN_URL, paramMap);
    }

    /**
     * 获取accessToken（授权码模式）
     *
     * @param serverUrl
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    public static Result<RpcAccessToken> getAccessToken(String serverUrl, String appId, String appSecret, String code) {
        Map<String, String> paramMap = new HashMap<>(4);
        paramMap.put(Oauth2Constant.GRANT_TYPE, GrantTypeEnum.AUTHORIZATION_CODE.getValue());
        paramMap.put(Oauth2Constant.APP_ID, appId);
        paramMap.put(Oauth2Constant.APP_SECRET, appSecret);
        paramMap.put(Oauth2Constant.AUTH_CODE, code);
        return getHttpAccessToken(serverUrl + Oauth2Constant.ACCESS_TOKEN_URL, paramMap);
    }

    /**
     * 刷新accessToken
     *
     * @param serverUrl
     * @param appId
     * @param refreshToken
     * @return
     */
    public static Result<RpcAccessToken> refreshToken(String serverUrl, String appId, String refreshToken) {
        Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put(Oauth2Constant.APP_ID, appId);
        paramMap.put(Oauth2Constant.REFRESH_TOKEN, refreshToken);
        return getHttpAccessToken(serverUrl + Oauth2Constant.REFRESH_TOKEN_URL, paramMap);
    }

    private static Result<RpcAccessToken> getHttpAccessToken(String url, Map<String, String> paramMap) {
        String jsonStr = HttpUtils.get(url, paramMap);
        if (jsonStr == null || jsonStr.isEmpty()) {
            logger.info("getHttpAccessToken exception, return null. url:{}", url);
            return null;
        }
        return JSONObject.parseObject(jsonStr, new TypeReference<Result<RpcAccessToken>>(){});
    }
}
