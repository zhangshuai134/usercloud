package com.zs.demo;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/12 20:11
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        String old = "unknown";
        String newa = "\u0001unknown\u0001";
        String s = StringUtils.substringBetween(newa, "\u0001");
        String trim = s.trim();
        System.out.println(trim.equals("unknown"));
    }
}
