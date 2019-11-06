package com.glls.realms;

import com.glls.util.JdbcUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author glls
 * @email 524840158@qq.com
 * @create 2019-11-05 16:28
 */
public class MyRealm extends AuthorizingRealm {


    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //  根据用户  去数据库查询对应的权限信息

        String account = principalCollection.getPrimaryPrincipal().toString();
        Set<String> rolesSet = getRoles(account);
        Set<String> permisssionsSet = getPermissions(account);
        
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(rolesSet);
        simpleAuthorizationInfo.addStringPermissions(permisssionsSet);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissions(String account) {
        Set<String> permisssionsSet = new HashSet<>();
        String sql="select s.resource_permission from sys_user u,sys_user_role ur,sys_role r,sys_resource s,sys_role_resource rr" +
                " where u.user_id= ur.user_id and ur.role_id = r.role_id and r.role_id = rr.role_id and " +
                " rr.resource_id = s.resource_id and u.account = ?";
        ResultSet resultSet = JdbcUtil.executeQuery(sql, account);
        try {
            while (resultSet.next()){
                permisssionsSet.add(resultSet.getString("resource_permission"));
            }

            return permisssionsSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Set<String> getRoles(String account)  {
        String sql = "select role_name from sys_user u,sys_user_role ur,sys_role r where " +
                " u.user_id= ur.user_id and ur.role_id = r.role_id and u.account = ? ";
        ResultSet resultSet = JdbcUtil.executeQuery(sql, account);
        Set<String> rolesSet = new HashSet<>();
        try {
            while (resultSet.next()){
                rolesSet.add(resultSet.getString("role_name"));
            }
            return rolesSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //String userName = (String) authenticationToken.getPrincipal();
        String account = (String) authenticationToken.getPrincipal();

        //根据用户名 去查密码
        String sql="select * from sys_user where account= ?";
        ResultSet rs =  JdbcUtil.executeQuery(sql,account);
        try {
            String password="";
            String salt="";
            while(rs.next()){
                password = rs.getString("password");
                salt= rs.getString("salt");
            }
            System.out.println(password);
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(account+"@163.com",password,ByteSource.Util.bytes(salt),getName());
            return simpleAuthenticationInfo;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
