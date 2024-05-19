package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 用户
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsUser {
    private Integer uid;
    private String username;
    private String userPwd;
    private String nickName; //昵称
}
