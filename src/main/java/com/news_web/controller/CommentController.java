package com.news_web.controller;


import com.news_web.models.Comment;
import com.news_web.models.Likes;
import com.news_web.response.Result;
import com.news_web.service.CommentService;
import com.news_web.service.LikesService;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikesService likesService;

    @GetMapping("/comment_list")
    @ResponseBody
    public Result commentList(String title) {
        return commentService.commentList(title);
    }

    @PostMapping("/parent_comment")
    @ResponseBody
    public Result parentComment(Comment.ParentComment parentComment) {
        return commentService.parentComment(parentComment);
    }

    @PostMapping("/child_comment")
    @ResponseBody
    public Result childComment(Comment.ChildComment childComment) {
        return commentService.childComment(childComment);
    }

    @PostMapping("/likes")
    @ResponseBody
    public Result like(Likes.CommentLike commentLike) {
        return likesService.like(commentLike);
    }

    @PostMapping("/is_liked")
    @ResponseBody
    public Result isLiked(Likes.CommentLike commentLike) {
        return likesService.isLiked(commentLike);
    }
}

