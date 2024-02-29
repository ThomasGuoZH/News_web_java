package com.news_web.service;

import com.news_web.models.Likes;
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
public interface LikesService extends IService<Likes> {
    Result like(Likes.CommentLike commentLike);
    Result isLiked(Likes.CommentLike commentLike);
    Result personalLikesList(String userId);
}
