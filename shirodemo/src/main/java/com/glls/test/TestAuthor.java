package com.glls.test;

import com.glls.util.ShiroUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author glls
 * @email 524840158@qq.com
 * @create 2019-11-06 09:34
 */
public class TestAuthor {
    @Test
    public void test1(){
        // 基于角色的授权控制
        Subject subject = ShiroUtil.getSubject("classpath:shiro_my.ini");

        UsernamePasswordToken token = new UsernamePasswordToken("test","test");

        subject.login(token);

        List<String> list = new ArrayList<>();
        list.add("teacher");
        list.add("student");
        list.add("admin");
       // boolean hasRole = subject.hasRole("admin");  //  是否具有某个角色
        // boolean[] booleans = subject.hasRoles(list);
        //boolean b = subject.hasAllRoles(list);  // 拥有集合中的所有角色才返回true
        //System.out.println("是否有这个角色"+hasRole);
        //System.out.println("老师：学生：管理员"+Arrays.toString(booleans));

        //subject.checkRole("student");  // 检查有没有这个角色  如果有 一切正常 如果没有 抛出异常
        //subject.checkRoles(list);  //  检查是否拥有集合中的所有角色 如果都有  一切正常  否则 抛出异常
        subject.checkRoles("vip0");
    }



    @Test
    public void test2() {
        // 基于权限的授权控制
        Subject subject = ShiroUtil.getSubject("classpath:shiro_my.ini");

        //UsernamePasswordToken token = new UsernamePasswordToken("zs", "123");
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");

        subject.login(token);

        boolean permitted = subject.isPermitted("user:delete");
        boolean[] permitted1 = subject.isPermitted("student:*", "user:select");
        boolean permittedAll = subject.isPermittedAll("student:*", "user:select", "user:delete");
        System.out.println(permitted);
        System.out.println(Arrays.toString(permitted1));
        System.out.println(permittedAll);
    }

}
