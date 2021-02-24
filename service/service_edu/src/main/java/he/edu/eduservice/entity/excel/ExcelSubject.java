package he.edu.eduservice.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 21:47 2021/2/23
 */
@Data
public class ExcelSubject {

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("父级")
    private String parentName;

    @ExcelProperty("排序")
    private Integer sort;
}
