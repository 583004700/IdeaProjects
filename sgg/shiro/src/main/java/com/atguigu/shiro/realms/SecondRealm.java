package com.atguigu.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class SecondRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("SecondRealm.doGetAuthenticationInfo():"+token);
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
            credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06---";
        }else if("user".equals(username)){
            credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718---";
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("SecondRealm",credentials,credentialsSalt,realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "SHA1";
        Object credentials = "123456";
        Object salt = "admin";
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName,credentials,salt,hashIterations);
        System.out.println(result);
    }

}
