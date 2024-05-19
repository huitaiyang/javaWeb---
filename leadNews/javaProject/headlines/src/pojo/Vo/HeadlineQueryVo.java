package pojo.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 方便条件查询
 * 存放其他信息
 * 实现 搜索功能
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeadlineQueryVo {
    private String keyWords;
    private Integer type;
    private Integer pageNum;
    private Integer pageSize;
}
