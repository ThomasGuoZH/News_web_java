package com.news_web.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author GuoZhaoHao
 * @since 2023-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String userName;

    private String password;

    private String sex;

    private String email;

    private String phone;

    @Data
    public static class RegisterForm{
        private String username;
        private String password;
        private String confirmedPassword;
        private String sex;
        private String email;
        private String phone;
    }

    @Data
    public static class LoginForm{
        private String username;
        private String password;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class ChangeInfoForm{
        private String username;
        private String id;
        private  String sex;
        private String email;
        private String phone;
    }

    @Data
    public static class ChangePasswordForm{
        private String id;
        private String oldPassword;
        private String newPassword;
        private String confirmedPassword;
    }
}
