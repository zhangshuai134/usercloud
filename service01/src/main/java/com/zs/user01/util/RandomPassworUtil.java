package com.zs.user01.util;


import java.util.Random;
import java.util.regex.Pattern;

public class RandomPassworUtil {

    public static final char[] CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'G', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    public static String generated(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) { // 生成六个字符
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        String result = buffer.toString();
        if(Pattern.matches("[\\d]", result) || Pattern.matches("[A-Z]+", result)){
            return generated(size);
        }else{
            return result;
        }
    }

    public static void main(String[] args){
        System.out.println(RandomPassworUtil.generated(6));
    }

}
