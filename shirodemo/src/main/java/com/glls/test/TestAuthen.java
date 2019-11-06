package com.glls.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * @author glls
 * @email 524840158@qq.com
 * @create 2019-11-05 15:10
 */
public class TestAuthen {
    @Test
    public void test1(){
        //构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_hello.ini");

        // 通过工厂创建SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 使用SecurityUtils将securityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建一个Subject实例，该实例认证要使用上边创建的securityManager进行
        Subject subject = SecurityUtils.getSubject();

        // 创建token令牌，记录用户认证的身份和凭证即账号和密码
        UsernamePasswordToken token = new UsernamePasswordToken("zss","1232");

        // 用户登录
        subject.login(token);   // 一单认证失败  就抛出异常
        // 用户认证状态
        Boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态：" + isAuthenticated);
        // 退出登录
        subject.logout();
        isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态：" + isAuthenticated);

    }
    @Test
    public void test2(){
        //构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_jdbc.ini");

        // 通过工厂创建SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 使用SecurityUtils将securityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建一个Subject实例，该实例认证要使用上边创建的securityManager进行
        Subject subject = SecurityUtils.getSubject();

        // 创建token令牌，记录用户认证的身份和凭证即账号和密码
        UsernamePasswordToken token = new UsernamePasswordToken("glls","glls");

        // 用户登录
        subject.login(token);   // 一单认证失败  就抛出异常
        // 用户认证状态
        Boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态：" + isAuthenticated);
        // 退出登录
        subject.logout();
        isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态：" + isAuthenticated);

    }
    @Test
    public void test3(){
        //构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_my.ini");

        // 通过工厂创建SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 使用SecurityUtils将securityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建一个Subject实例，该实例认证要使用上边创建的securityManager进行
        Subject subject = SecurityUtils.getSubject();

        // 创建token令牌，记录用户认证的身份和凭证即账号和密码
        UsernamePasswordToken token = new UsernamePasswordToken("admin","admin");

        // 用户登录
        subject.login(token);   // 一单认证失败  就抛出异常
        System.out.println(subject.getPrincipals());
        // 用户认证状态
        Boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态：" + isAuthenticated);
        // 退出登录
        subject.logout();
        isAuthenticated = subject.isAuthenticated();
        System.out.println("用户认证状态：" + isAuthenticated);

    }


}
