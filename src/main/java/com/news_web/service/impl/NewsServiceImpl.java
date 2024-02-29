package com.news_web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.news_web.models.News;
import com.news_web.mapper.NewsMapper;
import com.news_web.response.Result;
import com.news_web.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GuoZhaoHao
 * @since 2023-07-28
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public Result storeNews(News []news){
        for (News value : news) {
            QueryWrapper<News> findNews = new QueryWrapper<>();
            findNews.eq("title", value.getTitle());
            int count = this.count(findNews);
            if (count == 0) {
                this.save(value);
            }
        }
        return Result.success("新闻存储成功",null);
    }

    @Override
    public Result getNews(String title){
        QueryWrapper<News> findNews=new QueryWrapper<>();
        findNews.eq("title",title);
        int count=this.count(findNews);
        if(count==0){
            return Result.fail("没有查找到新闻");
        }
        News getNews=this.getOne(findNews);
        Map<String,String> data=new HashMap<>();
        data.put("title",getNews.getTitle());
        data.put("content",getNews.getContent());
        data.put("pic",getNews.getPic());
        data.put("time",getNews.getTime());
        data.put("src",getNews.getSrc());
        return Result.success("新闻获取成功!",data);
    }
}
