package com.zs.user01.test;

/**
 * @Auther: curry.zhang
 * @Date: 2019/8/15 09:38
 * @Description: 类加载顺序测试，详见 https://blog.csdn.net/java_zhangshuai/article/details/99621988
 */
public class ClassLoderTest {
    public static void main(String[] args) {
//        staticFun();
    }

    static ClassLoderTest clt = new ClassLoderTest();

    static {
        System.out.println(1);
    }
    {
        System.out.println(2);
    }
    ClassLoderTest(){
        System.out.println(3);
        System.out.println("a="+a);
        System.out.println("b="+b);
    }
    public static void staticFun(){
        System.out.println(4);
    }
    int a = 11;
    static int b = 12;
}
