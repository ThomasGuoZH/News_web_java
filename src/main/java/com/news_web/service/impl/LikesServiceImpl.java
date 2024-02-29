package com.news_web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.news_web.models.Comment;
import com.news_web.models.Likes;
import com.news_web.mapper.LikesMapper;
import com.news_web.models.User;
import com.news_web.response.Result;
import com.news_web.service.CommentService;
import com.news_web.service.LikesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GuoZhaoHao
 * @since 2023-07-28
 */
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Override
    public Result like(Likes.CommentLike likes){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("id",likes.getCommentId());
        Comment comment=commentService.getOne(commentQueryWrapper);
        QueryWrapper<Likes> likesQueryWrapper=new QueryWrapper<>();
        likesQueryWrapper.eq("comment_id",likes.getCommentId());
        likesQueryWrapper.eq("liker", likes.getLiker());
        int count=this.count(likesQueryWrapper);
        if(count>0){
            return Result.fail("你已点赞过该评论");
        }
        comment.setLikes(comment.getLikes()+1);
        commentService.updateById(comment);
        Likes likes1=new Likes();
        likes1.setCreatedAt(new Date())
                .setChannel(likes.getChannel())
                .setAuthor(likes.getAuthor())
                .setTitle(likes.getTitle())
                .setLiker(likes.getLiker())
                .setCommentId(likes.getCommentId())
                .setContent(likes.getContent());
        this.save(likes1);
        Map<String,Object> data=new HashMap<>();
        data.put("likes",comment.getLikes());
        data.put("liked",true);
        String time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(likes1.getCreatedAt());
        data.put("time",time);
        return Result.success("点赞成功",data);
    }

    @Override
    public Result isLiked(Likes.CommentLike likes){
        QueryWrapper<Likes> likesQueryWrapper = new QueryWrapper<>();
        likesQueryWrapper.eq("comment_id",likes.getCommentId());
        likesQueryWrapper.eq("liker",likes.getLiker());
        int count=this.count(likesQueryWrapper);
        Map<String, Boolean> stringBooleanMap = new HashMap<>();
        if(count==0){
            stringBooleanMap.put("liked",false);
            return Result.success("未点赞",stringBooleanMap);
        }else{
            stringBooleanMap.put("liked",true);
            return Result.success("已点赞",stringBooleanMap);
        }

    }

    @Override
    public Result personalLikesList(String userId){
        QueryWrapper<User> findUser=new QueryWrapper<>();
        findUser.eq("id",userId);
        User user=userService.getOne(findUser);
        String author=user.getUserName();
        QueryWrapper<Likes> likesQueryWrapper=new QueryWrapper<>();
        likesQueryWrapper.eq("author",author);
        List<Likes> likes=this.list(likesQueryWrapper);
        List<Map<String,Object>> likesArr=new ArrayList<>();
        for(Likes like:likes){
            Map<String,Object> likeData=new HashMap<>();
            likeData.put("liker",like.getLiker());
            likeData.put("content",like.getContent());
            String time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(like.getCreatedAt());
            likeData.put("time",time);
            likeData.put("channel",like.getChannel());
            likeData.put("title",like.getTitle());
            likesArr.add(likeData);
        }
        Map<String,Object> data=new HashMap<>();
        data.put("likes",likesArr);
        return Result.success("获取所有评论成功",data);
    }
}
