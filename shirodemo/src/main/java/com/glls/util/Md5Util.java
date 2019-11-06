package com.glls.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author glls
 * @email 524840158@qq.com
 * @create 2019-03-11 23:11
 */
public class Md5Util {
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("111111");
        System.out.println(md5Hash.toString());
       // System.out.println(md5Hash.toBase64());

        // 加盐
        md5Hash = new Md5Hash("test","gy2",2);
        System.out.println(md5Hash.toString());

        // 迭代次数   加密之后 再加密
        md5Hash = new Md5Hash("456","xiaoming",2);

        System.out.println(md5Hash.toString());

    }
}
