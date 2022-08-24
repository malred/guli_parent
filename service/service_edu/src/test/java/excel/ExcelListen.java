package excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author malguy-wang sir
 * @create ---
 */
public class ExcelListen extends AnalysisEventListener<DemoData> {
    //一行一行读取excel
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("****"+demoData);
    }
    //读取完成后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) { }
    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        System.out.println("表头: "+headMap);
    }
}
