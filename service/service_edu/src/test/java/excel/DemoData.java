package excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Data
public class DemoData {
    //设置表头名称
    @ExcelProperty("学生编号")
    //学生编号
    private Integer sno;
    //学生名称
    @ExcelProperty("学生姓名")
    private String sname;
}
