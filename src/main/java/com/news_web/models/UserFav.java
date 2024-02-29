package com.news_web.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author GuoZhaoHao
 * @since 2023-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserFav implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String userId;

    private Date createdAt;

    private String channel;

    @Data
    public static class NewsUserFav{
        private String channel;
        private String title;
        private String user_id;
    }
}
