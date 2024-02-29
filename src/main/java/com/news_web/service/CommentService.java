package com.news_web.service;

import com.news_web.models.Comment;
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
public interface CommentService extends IService<Comment> {
    Result commentList(String title);
    Result parentComment(Comment.ParentComment parentComment);
    Result childComment(Comment.ChildComment childComment);
    Result personalCommentsList(String userId);
    Result personalRepliesList(String userId);
    Result deleteComments(String commentId);
}
