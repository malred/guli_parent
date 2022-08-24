package excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author malguy-wang sir
 * @create ---
 */
public class excel2 {
    public static void main(String[] args) {
        //读操作
        //设置读取的地址
        String fileName = "D:\\java_excel\\write.xlsx";
        //调用方法,读取
        //参数一: 文件的路径, 参数二: 实体类.class
        EasyExcel.read(fileName, DemoData.class, new ExcelListen())
                .sheet().doRead();
    }
}
