//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.model.base.HyperModel;

@Entity
@Table(
        name = "e_bi_class_handler"
)
@Erupt(
        name = "报表处理类"
)
@Service
public class BiClassHandler extends HyperModel {
    @EruptField(
            views = {@View(
                    title = "名称"
            )},
            edit = @Edit(
                    title = "名称",
                    notNull = true,
                    search = @Search
            )
    )
    private String name;
    @EruptField(
            views = {@View(
                    title = "处理类路径"
            )},
            edit = @Edit(
                    title = "处理类路径",
                    notNull = true,
                    desc = "需实现xyz.erupt.bi.fun.EruptBiHandler接口",
                    inputType = @InputType(
                            fullSpan = true
                    )
            )
    )
    private String handlerPath;
    @Column(
            length = 2000
    )
    @EruptField(
            views = {@View(
                    title = "参数"
            )},
            edit = @Edit(
                    title = "参数",
                    type = EditType.TEXTAREA
            )
    )
    private String param;
    @EruptField(
            views = {@View(
                    title = "备注"
            )},
            edit = @Edit(
                    title = "备注",
                    type = EditType.TEXTAREA
            )
    )
    private String remark;

    public BiClassHandler() {
    }

    public String getName() {
        return this.name;
    }

    public String getHandlerPath() {
        return this.handlerPath;
    }

    public String getParam() {
        return this.param;
    }

    public String getRemark() {
        return this.remark;
    }
}
