package com.my.settings.exceptions;

import com.my.crm.utils.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        ifExpire();
        ifLocked("0");
        ifIpLegal("192.10.01.02");
    }

    //验证账户是否过期。返回bollean类型，0表示否，1表示是
    public static void ifExpire(){
        String expireTime="2021-03-15 08:56:09";
        //以下三句是获取系统的当前时间，并将其按照一定格式转换成相应的字符串，将这几句封装到工具类中。
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date=new Date();
//        String dateStr=simpleDateFormat.format(date);
        String dateStr= DateTimeUtil.getSysTime();
        if (expireTime.compareTo(dateStr)<0){
            System.out.println("对不起，您的账号已过期！");
        }
    }

    //验证账户是否被冻结
    public static void ifLocked(String lockState){
        if ("0".equals(lockState)){
            System.out.println("对不起，您的账号已被冻结！");
        }
    }

    //验证账户登录的ip地址是否合法
    public static void ifIpLegal(String ip){
        String ipLegal="192.10.01.01,192.10.01.03";
        if (!ipLegal.contains(ip)){
            System.out.println("对不起，您的ip地址不允许登录该系统！");
        }
    }
}
