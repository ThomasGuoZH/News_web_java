package com.news_web.controller;

import com.news_web.response.Result;
import com.news_web.service.CommentService;
import com.news_web.service.LikesService;
import com.news_web.service.UserFavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GuoZhaoHao
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/personnal")
public class PersonalController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private UserFavService userFavService;

    @GetMapping("/comments")
    @ResponseBody
    public Result personalCommentsList(String userId){
        return commentService.personalCommentsList(userId);
    }

    @GetMapping("/likes")
    @ResponseBody
    public Result personalLikesList(String userId){
        return likesService.personalLikesList(userId);
    }

    @GetMapping("/replies")
    @ResponseBody
    public Result personalRepliesList(String userId){
        return commentService.personalRepliesList(userId);
    }

    @GetMapping("/faves")
    @ResponseBody
    public Result getFavesList(String userId){
        return userFavService.getFavesList(userId);
    }

    @GetMapping("/del_comment")
    public Result deleteComments(String commentId){
        return commentService.deleteComments(commentId);
    }
}

