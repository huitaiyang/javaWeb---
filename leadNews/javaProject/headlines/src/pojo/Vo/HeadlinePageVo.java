package pojo.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页面内容排序?
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeadlinePageVo {
    private Integer hid;
    private String title;
    private Integer type;
    private Integer pageViews;
    private Long pastHours;
    private Integer publisher;

}
