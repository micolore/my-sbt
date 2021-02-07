package com.kubrick.jsbt.sso.server.config;

import com.kubrick.jsbt.sso.client.sso.client.SmartContainer;
import com.kubrick.jsbt.sso.client.sso.client.filter.LoginFilter;
import com.kubrick.jsbt.sso.client.sso.client.filter.LogoutFilter;
import com.kubrick.jsbt.sso.client.sso.client.listener.LogoutListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionListener;

/**
 * @author k
 * @version 1.0.0
 * @ClassName SsoConfig
 * @description: TODO
 * @date 2021/2/7 下午5:18
 */
@Configuration
public class SsoConfig {
    @Value("${sso.server.url}")
    private String serverUrl;
    @Value("${sso.app.id}")
    private String appId;
    @Value("${sso.app.secret}")
    private String appSecret;

    /**
     * 单实例方式注册单点登出Listener
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> LogoutListener() {
        ServletListenerRegistrationBean<HttpSessionListener> listenerRegBean = new ServletListenerRegistrationBean<>();
        LogoutListener logoutListener = new LogoutListener();
        listenerRegBean.setListener(logoutListener);
        return listenerRegBean;
    }

    /**
     * 分布式redis方式注册单点登出Listener
     *
     * 注：
     * 1.需注入RedisSessionMappingStorage
     * 2.需要使用Spring方式注入LogoutListener，使用ServletListenerRegistrationBean方式不生效
     */
//	@Autowired
//	private SessionMappingStorage sessionMappingStorage;
//
//	@Bean
//	public SessionMappingStorage sessionMappingStorage() {
//		return new RedisSessionMappingStorage();
//	}
//
//	@Bean
//	public ApplicationListener<AbstractSessionEvent> LogoutListener() {
//		List<HttpSessionListener> httpSessionListeners = new ArrayList<>();
//		LogoutListener logoutListener = new LogoutListener();
//		logoutListener.setSessionMappingStorage(sessionMappingStorage);
//		httpSessionListeners.add(logoutListener);
//		return new SessionEventHttpSessionListenerAdapter(httpSessionListeners);
//	}

    /**
     * 单点登录Filter容器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<SmartContainer> smartContainer() {
        SmartContainer smartContainer = new SmartContainer();
        smartContainer.setServerUrl(serverUrl);
        smartContainer.setAppId(appId);
        smartContainer.setAppSecret(appSecret);

        // 忽略拦截URL,多个逗号分隔
        smartContainer.setExcludeUrls("/login,/logout,/oauth2/*,/custom/*,/assets/*");

        smartContainer.setFilters(new LogoutFilter(), new LoginFilter());

        FilterRegistrationBean<SmartContainer> registration = new FilterRegistrationBean<>();
        registration.setFilter(smartContainer);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        registration.setName("smartContainer");
        return registration;
    }
}

