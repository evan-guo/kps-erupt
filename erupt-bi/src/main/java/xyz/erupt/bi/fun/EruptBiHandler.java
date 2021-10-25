//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.fun;

import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import xyz.erupt.annotation.config.Comment;

public interface EruptBiHandler {
    @Comment("查询表达式动态处理")
    default String exprHandler(@Comment("处理类参数") String param, @Comment("查询条件") Map<String, Object> condition, @Comment("查询表达式") String expr) {
        return expr;
    }

    @Comment("返回结果处理")
    default void resultHandler(@Comment("处理类参数") String param, @Comment("查询条件") Map<String, Object> condition, @Comment("查询结果") List<Map<String, Object>> result) {
    }

    @Comment("导出excel处理")
    default void exportHandler(@Comment("处理类参数") String param, @Comment("查询条件") Map<String, Object> condition, @Comment("创建好的poi对象") Workbook workbook) {
    }
}
