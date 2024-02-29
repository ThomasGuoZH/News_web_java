package com.news_web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.news_web.models.Comment;
import com.news_web.mapper.CommentMapper;
import com.news_web.models.Likes;
import com.news_web.models.User;
import com.news_web.response.Result;
import com.news_web.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news_web.service.LikesService;
import com.news_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private LikesService likesService;

    @Override
    public Result commentList(String title){
        QueryWrapper<Comment> parentCommentQueryWrapper=new QueryWrapper<>();
        parentCommentQueryWrapper.eq("title",title);
        parentCommentQueryWrapper.eq("type",0);
        List<Comment> parentComments=this.list(parentCommentQueryWrapper);
        List<Map<String,Object>> parentCommentsArr=new ArrayList<>();
        for(Comment parentComment:parentComments){
            QueryWrapper<Comment> childCommentQueryWrapper=new QueryWrapper<>();
            childCommentQueryWrapper.eq("parent_id", parentComment.getId());
            childCommentQueryWrapper.eq("type",1);
            List<Comment> childComments=this.list(childCommentQueryWrapper);
            List<Map<String,Object>> childCommentsArr=new ArrayList<>();
            for(Comment childComment:childComments){
                Map<String,Object> childCommentsArrData=new HashMap<>();
                childCommentsArrData.put("author",childComment.getAuthor());
                childCommentsArrData.put("content",childComment.getContent());
                childCommentsArrData.put("id",childComment.getId().toString());
                childCommentsArrData.put("likes",childComment.getLikes());
                String time1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(childComment.getCreatedAt());
                childCommentsArrData.put("time",time1);
                childCommentsArr.add(childCommentsArrData);
            }
            Map<String,Object> parentCommentsArrData=new HashMap<>();
            parentCommentsArrData.put("author",parentComment.getAuthor());
            parentCommentsArrData.put("content",parentComment.getContent());
            parentCommentsArrData.put("id",parentComment.getId().toString());
            parentCommentsArrData.put("likes",parentComment.getLikes());
            String time2=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(parentComment.getCreatedAt());
            parentCommentsArrData.put("time",time2);
            parentCommentsArrData.put("replies",childCommentsArr);
            parentCommentsArr.add(parentCommentsArrData);
        }
        Map<String,Object> data=new HashMap<>();
        data.put("comments",parentCommentsArr);
        return Result.success("获取所有评论成功",data);
    }

    @Override
    public Result parentComment(Comment.ParentComment parentComment){
        Comment comment=new Comment();
        Date date=new Date();
        comment.setContent(parentComment.getContent())
                .setAuthor(parentComment.getAuthor())
                .setParentAuthor(comment.getAuthor())
                .setTitle(parentComment.getTitle())
                .setChannel(parentComment.getChannel())
                .setCreatedAt(date)
                .setType(0)
                .setLikes(0);
        boolean flag=this.save(comment);
        comment.setParentId(comment.getId());
        boolean flag2=this.updateById(comment);
        Map<String,Object> data=new HashMap<>();
        data.put("author",comment.getAuthor());
        data.put("content",comment.getContent());
        data.put("id",comment.getId().toString());
        data.put("likes",comment.getLikes());
        String time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(comment.getCreatedAt());
        data.put("time",time);
        return Result.success("评论成功",data);
    }

    @Override
    public Result childComment(Comment.ChildComment childComment){
        QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
        System.out.println(childComment);
        commentQueryWrapper.eq("id",Integer.parseInt(childComment.getParent_id()));
        int count=this.count(commentQueryWrapper);
        if(count==0){
            return Result.fail("1级评论不存在");
        }
        Date date=new Date();
        Comment comment=new Comment();
        comment.setTitle(childComment.getTitle())
                .setAuthor(comment.getAuthor())
                .setContent(comment.getContent())
                .setChannel(comment.getChannel())
                .setParentId(comment.getParentId())
                .setParentAuthor(comment.getParentAuthor())
                .setCreatedAt(date)
                .setLikes(0)
                .setType(1);
        boolean flag=this.save(comment);
        Map<String,Object> data=new HashMap<>();
        data.put("author",comment.getAuthor());
        data.put("content",comment.getContent());
        data.put("id",comment.getId().toString());
        data.put("likes",comment.getLikes());
        String time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(comment.getCreatedAt());
        data.put("time",time);
        return Result.success("评论成功",data);
    }

    @Override
    public Result personalCommentsList(String userId){
        QueryWrapper<User> findUser=new QueryWrapper<>();
        findUser.eq("id",userId);
        User user=userService.getOne(findUser);
        System.out.println(user);
        String author=user.getUserName();
        QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
        commentQueryWrapper.eq("author",author);
        commentQueryWrapper.eq("type",0);
        List<Comment> Comments=this.list(commentQueryWrapper);
        List<Map<String,Object>> commentsArr=new ArrayList<>();
        for(Comment comment:Comments){
            Map<String,Object> commentData=new HashMap<>();
            commentData.put("id",comment.getId().toString());
            commentData.put("content",comment.getContent());
            String time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(comment.getCreatedAt());
            commentData.put("time",time);
            commentData.put("channel",comment.getChannel());
            commentData.put("title",comment.getTitle());
            commentsArr.add(commentData);
        }
        Map<String,Object> data=new HashMap<>();
        data.put("comments",commentsArr);
        return Result.success("获取所有评论成功",data);
    }

    @Override
    public Result personalRepliesList(String userId){
        QueryWrapper<User> findUser=new QueryWrapper<>();
        findUser.eq("id",Long.parseLong(userId));
        User user=userService.getOne(findUser);
        String author=user.getUserName();
        QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
        commentQueryWrapper.eq("parent_author",author);
        commentQueryWrapper.eq("type",1);
        List<Comment> subComments=this.list(commentQueryWrapper);
        List<Map<String,Object>> commentsArr=new ArrayList<>();
        for(Comment subComment:subComments){
            QueryWrapper<Comment> commentQueryWrapper1 = new QueryWrapper<>();
            commentQueryWrapper1.eq("type",0);
            commentQueryWrapper1.eq("parent_id",subComment.getParentId());
            Comment parentComment=this.getOne(commentQueryWrapper1);
            Map<String,Object> commentData=new HashMap<>();
            commentData.put("author",subComment.getAuthor());
            commentData.put("content",subComment.getContent());
            String time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(subComment.getCreatedAt());
            commentData.put("time",time);
            commentData.put("channel",subComment.getChannel());
            commentData.put("title",subComment.getTitle());
            commentData.put("parentContent",parentComment.getContent());
            commentsArr.add(commentData);
        }
        Map<String,Object> data=new HashMap<>();
        data.put("replies",commentsArr);
        return Result.success("获取回复成功",data);
    }

    @Override
    public Result deleteComments(String commentId){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("id",Integer.parseInt(commentId));
        this.remove(commentQueryWrapper);

        QueryWrapper<Comment> commentQueryWrapper1 = new QueryWrapper<>();
        commentQueryWrapper1.eq("parent_id",commentId);
        List<Comment> childComments=this.list(commentQueryWrapper1);
        this.remove(commentQueryWrapper1);

        QueryWrapper<Likes> likesQueryWrapper = new QueryWrapper<>();
        likesQueryWrapper.eq("comment_id",commentId);
        for(Comment comment:childComments){
            QueryWrapper<Likes> likesQueryWrapper1 = new QueryWrapper<>();
            likesQueryWrapper1.eq("comment_id",comment.getId());
            likesService.remove(likesQueryWrapper1);
        }
        likesService.remove(likesQueryWrapper);
        return Result.success("删除评论成功",null);
    }
}
