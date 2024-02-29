package com.news_web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.news_web.jwt.JwtUtil;
import com.news_web.models.User;
import com.news_web.mapper.UserMapper;
import com.news_web.response.Result;
import com.news_web.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GuoZhaoHao
 * @since 2023-07-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result register(User.RegisterForm registerForm){
        System.out.println(registerForm);
        QueryWrapper<User> findUser =new QueryWrapper<>();
        findUser.eq("user_name",registerForm.getUsername());
        int count=this.count(findUser);
        if(count==1){
            return Result.fail("用户名已被使用!");
        }
        User user=new User();
        user.setUserName(registerForm.getUsername())
                .setPassword(registerForm.getPassword())
                .setEmail(registerForm.getEmail())
                .setPhone(registerForm.getPhone())
                .setSex(registerForm.getSex());
        boolean flag=this.save(user);
        if(flag){
            return Result.success("注册成功!",null);
        }else{
            return Result.fail("注册失败!");
        }
    }

    @Override
    public Result login(User.LoginForm loginForm){
        System.out.println(loginForm);
        QueryWrapper<User> findUser=new QueryWrapper<>();
        findUser.eq("user_name",loginForm.getUsername());
        int count=this.count(findUser);
        if(count==0){
            return Result.fail("用户不存在!");
        }
        User user=this.getOne(findUser);
        if(!user.getPassword().equals(loginForm.getPassword())){
            return Result.fail("密码错误!");
        }
        String token= JwtUtil.createToken(user);
        Map<String,Object> data=new HashMap<>();
        data.put("username",user.getUserName());
        data.put("userId",user.getId().toString());
        data.put("sex",user.getSex());
        data.put("email",user.getEmail());
        data.put("phone",user.getPhone());
        data.put("token",token);
        return Result.success("登录成功!",data);
    }

    @Override
    public Result changeInfo(User.ChangeInfoForm changeInfoForm){
        QueryWrapper<User> findUser1=new QueryWrapper<>();
        findUser1.eq("id",Long.parseLong(changeInfoForm.getId()));
        int count=this.count(findUser1);
        if(count==0){
            return Result.fail("没有找到用户！");
        }
        User user=this.getOne(findUser1);
        User.ChangeInfoForm oldInfo=new User.ChangeInfoForm();
        oldInfo.setUsername(user.getUserName())
                .setEmail(user.getEmail())
                .setPhone(user.getPhone())
                .setSex(user.getSex())
                .setId((user.getId()).toString());
        System.out.println(oldInfo);
        System.out.println(changeInfoForm);
        if(oldInfo.equals(changeInfoForm)){
            return Result.fail("用户信息不能相同");
        }else {
            QueryWrapper<User> findUser2=new QueryWrapper<>();
            findUser2.eq("user_name",changeInfoForm.getUsername());
            int count2=this.count(findUser2);
            if(count2==1){
                return Result.fail("用户名已存在");
            }else{
                user.setUserName(changeInfoForm.getUsername())
                        .setPhone(changeInfoForm.getPhone())
                        .setSex(changeInfoForm.getSex())
                        .setEmail(changeInfoForm.getEmail());
                boolean flag=this.updateById(user);
                if(flag){
                    String token= JwtUtil.createToken(user);
                    Map<String,Object> data=new HashMap<>();
                    data.put("username",user.getUserName());
                    data.put("userId",(user.getId()).toString());
                    data.put("sex",user.getSex());
                    data.put("email",user.getEmail());
                    data.put("phone",user.getPhone());
                    data.put("token",token);
                    return Result.success(("保存成功!"),data);
                }else{
                    return Result.fail("更改用户信息失败");
                }
            }
        }
    }

    @Override
    public Result changePwd(User.ChangePasswordForm changePasswordForm){
        QueryWrapper<User> findUser=new QueryWrapper<>();
        findUser.eq("id",Long.parseLong(changePasswordForm.getId()));
        int count=this.count(findUser);
        if(count==0){
            return Result.fail("用户不存在");
        }
        User user=this.getOne(findUser);
        if(!changePasswordForm.getOldPassword().equals(user.getPassword())){
            return Result.fail("旧密码错误!");
        }
        if(changePasswordForm.getOldPassword().equals(changePasswordForm.getNewPassword())){
            return Result.fail("新旧密码不能相同");
        }
        user.setPassword(changePasswordForm.getNewPassword());
        boolean flag=this.updateById(user);
        return Result.success("密码修改成功!",null);

    }
}
