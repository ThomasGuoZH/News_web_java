package com.news_web.service;

import com.news_web.models.News;
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
public interface NewsService extends IService<News> {
    Result storeNews(News []news);
    Result getNews(String title);
}
