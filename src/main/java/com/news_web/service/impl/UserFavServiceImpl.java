package com.news_web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news_web.models.UserFav;
import com.news_web.mapper.UserFavMapper;
import com.news_web.response.Result;
import com.news_web.service.UserFavService;
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
public class UserFavServiceImpl extends ServiceImpl<UserFavMapper, UserFav> implements UserFavService {
    @Override
    public Result isFav(UserFav.NewsUserFav newsUserFav){
        QueryWrapper<UserFav> userFavQueryWrapper=new QueryWrapper<>();
        userFavQueryWrapper.eq("title",newsUserFav.getTitle());
        userFavQueryWrapper.eq("user_id",newsUserFav.getUser_id());
        int count=this.count(userFavQueryWrapper);
        Map<String,Object> data=new HashMap<>();
        if(count==1){
            data.put("isFavorite",true);
            return Result.success("已收藏",data);
        }else {
            data.put("isFavorite",false);
            return Result.success("未收藏",data);
        }
    }

    @Override
    public Result createFav(UserFav.NewsUserFav newsUserFav){
        QueryWrapper<UserFav> userFavQueryWrapper=new QueryWrapper<>();
        userFavQueryWrapper.eq("title",newsUserFav.getTitle());
        userFavQueryWrapper.eq("user_id",newsUserFav.getUser_id());
        int count=this.count(userFavQueryWrapper);
        if(count==1){
            return Result.fail("已收藏过");
        }
        UserFav userFav=new UserFav();
        userFav.setChannel(newsUserFav.getChannel());
        userFav.setTitle(newsUserFav.getTitle());
        userFav.setUserId(newsUserFav.getUser_id());
        // 获得系统时间
        Date date = new Date();
        userFav.setCreatedAt(date);
        boolean flag=this.save(userFav);
        Map<String,Object> data=new HashMap<>();
        data.put("isFavorite",true);
        return Result.success("收藏成功",data);
    }

    @Override
    public Result disFav(UserFav.NewsUserFav newsUserFav){
        QueryWrapper<UserFav> userFavQueryWrapper=new QueryWrapper<>();
        userFavQueryWrapper.eq("title",newsUserFav.getTitle());
        userFavQueryWrapper.eq("user_id",newsUserFav.getUser_id());
        int count=this.count(userFavQueryWrapper);
        if(count==0){
            return Result.fail("未收藏过");
        }
        UserFav userFav=this.getOne(userFavQueryWrapper);
        boolean flag=this.remove(userFavQueryWrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("isFavorite",false);
        return Result.success("取消收藏成功",data);
    }

    @Override
    public Result getFavesList(String userId){
        QueryWrapper<UserFav> userFavQueryWrapper=new QueryWrapper<>();
        userFavQueryWrapper.eq("user_id",userId);
        List<UserFav> findFavs=this.list(userFavQueryWrapper);
        List<Map<String,String>> favArr=new ArrayList<>();
        for(UserFav userFav:findFavs){
            Map<String,String> data=new HashMap<>();
            data.put("title",userFav.getTitle());
            data.put("channel",userFav.getChannel());
            String nowTime=new SimpleDateFormat("yyyy-MM--dd hh:mm:ss").format(userFav.getCreatedAt());
            data.put("time",nowTime);
            favArr.add(data);
        }
        Map<String,Object> favourites=new HashMap<>();
        favourites.put("favourites",favArr);
        return Result.success("获取收藏成功",favourites);
    }
}
