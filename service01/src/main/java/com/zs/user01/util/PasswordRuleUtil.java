package com.zs.user01.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordRuleUtil {
    //
    private static final String PASSWORD_RULE="^(?![\\d]+$)(?![a-z]+$)(?![A-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$";

    public static boolean validate(String password){
        Pattern pattern=Pattern.compile(PASSWORD_RULE);
        Matcher matcher=pattern.matcher(password);
        return matcher.matches();
    }

    public static void main(String[] args){
       System.out.println(PasswordRuleUtil.validate("12341aaa"));
    }


}
