package common;

import lombok.Getter;

/**
 * 枚举类
 * 统一返回结果状态信息类
 */
public enum ResultCodeEnum {
    SUCCESS(200,"success"), //登录成功
    USERNAME_ERROR(501,"usernameError"), //用户名错误
    PASSWORD_ERROR(503,"passwordError"), //密码错误
    NOTLOGIN(504,"notLogin"), //未登录状态
    USERNAME_USED(505,"usernameUsed") //用户名被占用
    ;

    @Getter //注解创建ResultCodeEnum的get方法
    private Integer code;
    @Getter
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
