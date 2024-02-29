package com.news_web.controller;

import com.news_web.models.*;
import com.news_web.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") //大路由组
public class MainController {

    @Autowired
    private UserController userController;
    @PostMapping(value = "/user/register")
    public Result register(@RequestBody User.RegisterForm registerForm) {
        return userController.register(registerForm);
    }
    @PostMapping("/user/login")
    public Result login(@RequestBody User.LoginForm LoginForm) {
        return userController.login(LoginForm);
    }
    @PostMapping("/user/change_info")
    public Result changeInfo(@RequestBody User.ChangeInfoForm changeInfoForm) {
        return userController.changeInfo(changeInfoForm);
    }
    @PostMapping("/user/change_password")
    public Result changePwd(@RequestBody User.ChangePasswordForm changePasswordForm) {
        return userController.changePwd(changePasswordForm);
    }
    @PostMapping("/user/create_fav")
    public Result createFav(@RequestBody UserFav.NewsUserFav newsUserFav) {
        return userController.createFav(newsUserFav);
    }
    @PostMapping("/user/isFaves")
    public Result isFav(@RequestBody UserFav.NewsUserFav newsUserFav) {
        return userController.isFav(newsUserFav);
    }
    @PostMapping("/user/dis_fav")
    public Result disFav(@RequestBody UserFav.NewsUserFav newsUserFav) {
        return userController.disFav(newsUserFav);
    }


    @Autowired
    private PersonalController personalController;
    @GetMapping("/personal/comments")
    public Result personalCommentsList(@RequestParam("user_id") String userId){
        return personalController.personalCommentsList(userId);
    }
    @GetMapping("/personal/likes")
    public Result personalLikesList(@RequestParam("user_id") String userId){
        return personalController.personalLikesList(userId);
    }
    @GetMapping("/personal/replies")
    public Result personalRepliesList(@RequestParam("user_id") String userId){
        return personalController.personalRepliesList(userId);
    }
    @GetMapping("/personal/faves")
    public Result getFavesList(@RequestParam("user_id") String userId){
        return personalController.getFavesList(userId);
    }
    @DeleteMapping("/personal/del_comment")
    public Result deleteComments(@RequestParam("id") String commentId){
        return personalController.deleteComments(commentId);
    }


    @Autowired
    private NewsController newsController;
    @PostMapping("/news/store_news")
    public Result storeNews(@RequestBody News[] news){
        return newsController.storeNews(news);
    }
    @GetMapping("/news/get_news")
    public Result getNews(@RequestParam("title") String title){
        return newsController.getNews(title);
    }


    @Autowired
    private CommentController commentController;
    @GetMapping("/comment/comment_list")
    public Result commentList(@RequestParam("title") String title){
        return commentController.commentList(title);
    }
    @PostMapping("/comment/parent_comment")
    public Result parentComment(@RequestBody Comment.ParentComment parentComment) {
        return commentController.parentComment(parentComment);
    }
    @PostMapping("/comment/child_comment")
    public Result childComment(@RequestBody Comment.ChildComment childComment){
        return commentController.childComment(childComment);
    }
    @PostMapping("/comment/likes")
    public Result like(@RequestBody Likes.CommentLike commentLike){
        return commentController.like(commentLike);
    }
    @PostMapping("/comment/is_liked")
    public Result isLiked(@RequestBody Likes.CommentLike commentLike){
        return commentController.isLiked(commentLike);
    }
}
