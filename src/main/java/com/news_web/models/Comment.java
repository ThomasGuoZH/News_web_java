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
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    private Integer parentId;

    private Date createdAt;

    private String title;

    private String author;

    private Integer type;

    private String parentAuthor;

    private Integer likes;

    private String channel;

    @Data
    public static class ParentComment{
        private String title;
        private String author;
        private String content;
        private String channel;
    }

    @Data
    public static class ChildComment{
        private String title;
        private String author;
        private String content;
        private String channel;
        private String parent_id;
        private String parent_author;
    }
}
