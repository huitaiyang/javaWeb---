package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 新闻
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsHeadline {
    private Integer hid;
    private String title;
    private String article;
    private Integer type;
    public Integer publisher;
    public Integer pageViews; //浏览量
    private Date createTime;
    private Date updateTime;
    private Integer isDelete;
}
