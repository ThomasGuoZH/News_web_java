package com.news_web.service;

import com.news_web.models.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.news_web.response.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuoZhaoHao
 * @since 2023-07-28
 */
public interface UserService extends IService<User> {
    Result register(User.RegisterForm registerForm);
    Result login(User.LoginForm loginForm);
    Result changeInfo(User.ChangeInfoForm changeInfoForm);
    Result changePwd(User.ChangePasswordForm changePasswordForm);
}
