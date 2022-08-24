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
    @ExcelProperty(value="学生编号",index=0)
    //学生编号
    private Integer sno;
    //学生名称
    @ExcelProperty(value="学生姓名",index=1)
    private String sname;
}
