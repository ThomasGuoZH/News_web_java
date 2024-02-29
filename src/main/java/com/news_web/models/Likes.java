package com.news_web.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Likes implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String title;

    private String liker;

    private String author;

    private String content;

    private String commentId;

    private String channel;

    @Data
    public static class CommentLike{
        private String title;
        private String liker;
        private String author;
        private String content;
        private String commentId;
        private String channel;
    }
}
