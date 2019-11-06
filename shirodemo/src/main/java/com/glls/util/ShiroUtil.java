package com.glls.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @author glls
 * @email 524840158@qq.com
 * @create 2019-11-06 09:36
 */
public class ShiroUtil {
    public static Subject getSubject(String cfglocation) {
        //构建SecurityManager工厂，IniSecurityManagerFactory可以从ini文件中初始化SecurityManager环境
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(cfglocation);

        // 通过工厂创建SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 使用SecurityUtils将securityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        // 创建一个Subject实例，该实例认证要使用上边创建的securityManager进行
        Subject subject = SecurityUtils.getSubject();

        return subject;
    }
}
