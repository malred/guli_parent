package excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author malguy-wang sir
 * @create ---
 */
public class excel1 {
    public static void main(String[] args) {
        //写操作
        //设置写入的地址
        String fileName="D:\\java_excel\\write.xlsx";
        //调用方法,写入
        //参数一: 文件的路径, 参数二: 实体类.class
        EasyExcel.write(fileName,DemoData.class)
                .sheet("学生列表")//表名
                .doWrite(getData()); //写入的数据
    }
    //创建方法,返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list=new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data=new DemoData();
            data.setSno(i);
            data.setSname("luu"+i);
            list.add(data);
        }
        return list;
    }
}
