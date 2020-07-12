package com.atguigu.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;

public class ShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo:"+token);
        System.out.println("2.token:"+token.hashCode());
        //将AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if("unknown".equals(username)){
            throw new UnknownAccountException("用户名不存在");
        }
        if("monster".equals(username)){
            throw new LockedAccountException("用户被锁定");
        }
        //以下信息是从数据库中获取出来的
        //principal可以是用户名，也可以是用户对象
        //credentials数据表中获取的密码
        //realmName 当前对象realm的name，调用父类对象的getName()方法即可
        Object principal = username;
        Object credentials = "";
        String realmName = super.getName();
        if("admin".equals(username)){
            credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
        }else if("user".equals(username)){
            credentials = "098d2c478e9c11555ce2823231e02ec1";
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "123456";
        Object salt = "user";
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName,credentials,salt,hashIterations);
        System.out.println(result);
    }

    //用于授权的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("doGetAuthorizationInfo...");
        //1.从PrincipalCollection中获取用户登录信息,跟realm的配置顺序有关，只取出第一个realm(ShiroRealm.doGetAuthenticationInfo())返回的值
        Object principal = principalCollection.getPrimaryPrincipal();
        //2.利用登录信息来获取用户对应角色或权限
        HashSet<String> roles = new HashSet<String>();
        roles.add("user");
        if("admin".equals(principal)){
            roles.add("admin");
        }
        //3.创建SimpleAuthorizationInfo，并设置其roles属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }
}
