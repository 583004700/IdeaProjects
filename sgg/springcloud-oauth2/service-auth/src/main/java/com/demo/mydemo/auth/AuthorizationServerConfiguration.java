package com.demo.mydemo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String finalSecret = passwordEncoder.encode("123456");

        /*
            支持的grant_type
            authorization_code
            refresh_token
            implicit
            client_credentials
            password
         */

        /**
         * 授权码模式,先拿到code
         * http://localhost:9098/oauth/authorize?client_id=client_3&response_type=code&scope=server&redirect_uri=https://www.baidu.com
         *
         */

        // 配置两个客户端，一个用于password认证一个用于client认证
        clients.inMemory().withClient("client_1")
                .resourceIds("1", "2")
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("oauth2")
                .secret(finalSecret)
                .and().withClient("client_2")
                .resourceIds("res1", "res2")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("server")
                .authorities("oauth2")
                .secret(finalSecret)
                .and().withClient("client_3")
                .resourceIds("res1","res2")
                // 跳转到授权页面
                .autoApprove(false)
                .authorizedGrantTypes("authorization_code","refresh_token")
                .scopes("server")
                .authorities("oauth2")
                .secret(finalSecret)
                .resourceIds("res1","res2")
                .redirectUris("https://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new MyRedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("permitAll");
    }
}
