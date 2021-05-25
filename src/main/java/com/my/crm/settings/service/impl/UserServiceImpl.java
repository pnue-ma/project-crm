package com.my.crm.settings.service.impl;

import com.my.crm.exceptions.UserLoginException;
import com.my.crm.settings.dao.UserDao;
import com.my.crm.settings.domain.User;
import com.my.crm.settings.service.UserService;
import com.my.crm.utils.DateTimeUtil;
import com.my.crm.utils.MD5Util;
import com.my.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    //接下来利用dao对象对数据库进行curd等操作
    public User userLogin(String uname, String upass, String uip) throws UserLoginException {
        //首先将uname与upass传递给dao层，调用dao层对象查询数据库中是否有匹配的用户记录
//        userDao.selectUserLogin(uname,upass);     使用mybatis的dao层能够接收的参数只能是一个，所以如果想要向dao层传递多个数据，则需要将多个数据打包为一个对象，或者存入一个map集合中进行传递
        //这里选择将uname与upass存放在一个map集合中传递给dao层
        //存
        Map<String,String> map=new HashMap<String, String>();
        map.put("uname",uname);
        map.put("upass",MD5Util.getMD5(upass));         //注意在service层接受到upass后对其进行加密，然后再传递给dao层,让dao层进行查询操作，因为数据库中保存的密码就是加密过的。让密文与密文比较。
        map.put("uip",uip);
        //传
        User user=userDao.selectUserLogin(map);
        //得到查询结果，处理查询结果
        if (null == user){
            throw new UserLoginException("用户名或密码错误");
        }else{
            if (ifExpire(user.getExpireTime())){
                throw new UserLoginException("对不起，您的账户已过期。请联系系统管理员处理。");
            }
            if (ifLocked(user.getLockState())){
                throw new UserLoginException("对不起，您的账号已被锁定，请联系管理员解锁。");
            }
            if (ifIpInvalid(user.getAllowIps(),uip)){
                throw new UserLoginException("对不起，您所在的ip禁止登录。");
            }
        }

        return user;
    }

    private boolean ifIpInvalid(String allowIps,String uip) {
        if (null==allowIps || "".equals(allowIps)){
            return false;
        }else {
            return !(allowIps.contains(uip));
        }
    }

    private boolean ifLocked(String lockState) {
        return "0".equals(lockState);
    }

    //到底为什么这里不加static上面不创建对象调用方法也不报错啊？难道在一个类中的一个方法中调用该类的另一个方法，可以省去主语this，是this吗？
    private boolean ifExpire(String expireTime) {
        return (DateTimeUtil.getSysTime().compareTo(expireTime))<0?false:true;
    }


}
