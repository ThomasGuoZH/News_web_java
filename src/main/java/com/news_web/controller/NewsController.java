package com.news_web.controller;


import com.news_web.models.News;
import com.news_web.response.Result;
import com.news_web.service.NewsService;
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
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/store_news")
    @ResponseBody
    public Result storeNews(News[] news){
        return newsService.storeNews(news);
    }

    @GetMapping("/get_news")
    @ResponseBody
    public Result getNews(String title){
        return newsService.getNews(title);
    }
}

