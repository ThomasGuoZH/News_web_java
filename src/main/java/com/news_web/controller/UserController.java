package com.news_web.controller;



import com.news_web.models.User;
import com.news_web.models.UserFav;
import com.news_web.response.Result;
import com.news_web.service.UserFavService;
import com.news_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GuoZhaoHao
 * @since 2023-07-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFavService userFavService;

    @PostMapping("/register")
    @ResponseBody
    public Result  register(User.RegisterForm registerForm){
        return userService.register(registerForm);
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(User.LoginForm loginForm){
        return userService.login(loginForm);
    }

    @PostMapping("/user/change_info")
    @ResponseBody
    public Result changeInfo(User.ChangeInfoForm changeInfoForm) {
        return userService.changeInfo(changeInfoForm);
    }

    @PostMapping("/user/change_password")
    @ResponseBody
    public Result changePwd(User.ChangePasswordForm changePasswordForm) {
        return userService.changePwd(changePasswordForm);
    }

    @PostMapping("/user/create_fav")
    @ResponseBody
    public Result createFav(UserFav.NewsUserFav newsUserFav) {
        return userFavService.createFav(newsUserFav);
    }

    @PostMapping("/user/isFaves")
    @ResponseBody
    public Result isFav(UserFav.NewsUserFav newsUserFav) {
        return userFavService.isFav(newsUserFav);
    }

    @PostMapping("/user/dis_fav")
    @ResponseBody
    public Result disFav(UserFav.NewsUserFav newsUserFav) {
        return userFavService.disFav(newsUserFav);
    }



}

