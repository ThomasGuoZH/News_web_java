package com.news_web.service;

import com.news_web.models.UserFav;
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
public interface UserFavService extends IService<UserFav> {
    Result isFav(UserFav.NewsUserFav newsUserFav);
    Result createFav(UserFav.NewsUserFav newsUserFav);
    Result disFav(UserFav.NewsUserFav newsUserFav);
    Result getFavesList(String userId);
}
