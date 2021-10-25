//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.CodeEditorType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.bi.service.BiDataInitService;
import xyz.erupt.core.exception.EruptApiErrorTip;
import xyz.erupt.upms.model.base.HyperModel;

@Entity
@Table(
        name = "e_bi_function"
)
@Erupt(
        name = "报表函数",
        dataProxy = {BiFunction.class}
)
@Service
public class BiFunction extends HyperModel implements DataProxy<BiFunction> {
    @EruptField(
            views = {@View(
                    title = "编码"
            )},
            edit = @Edit(
                    title = "编码",
                    notNull = true,
                    search = @Search(
                            vague = true
                    )
            )
    )
    private String code;
    @EruptField(
            views = {@View(
                    title = "名称"
            )},
            edit = @Edit(
                    title = "名称",
                    notNull = true,
                    search = @Search(
                            vague = true
                    )
            )
    )
    private String name;
    @Lob
    @Type(
            type = "org.hibernate.type.TextType"
    )
    @EruptField(
            views = {@View(
                    title = "函数表达式"
            )},
            edit = @Edit(
                    title = "函数表达式",
                    desc = "参照JavaScript function写法",
                    codeEditType = @CodeEditorType(
                            language = "javascript"
                    ),
                    notNull = true,
                    type = EditType.CODE_EDITOR
            )
    )
    private String jsFunction;
    @Resource
    @Transient
    private BiDataInitService biDataInitService;
    private static final ScriptEngine scriptEngine = (new ScriptEngineManager()).getEngineByName("nashorn");

    public BiFunction(String code, String name, String jsFunction) {
        this.code = code;
        this.name = name;
        this.jsFunction = jsFunction;
    }

    public BiFunction() {
    }

    private void testFunction(BiFunction biFunction) {
        try {
            scriptEngine.eval(biFunction.getJsFunction());
        } catch (ScriptException var3) {
            throw new EruptApiErrorTip(var3.getMessage());
        }
    }

    public void afterAdd(BiFunction biFunction) {
        this.testFunction(biFunction);
        this.biDataInitService.flushFunction();
    }

    public void afterUpdate(BiFunction biFunction) {
        this.testFunction(biFunction);
        this.biDataInitService.flushFunction();
    }

    public void afterDelete(BiFunction biFunction) {
        this.testFunction(biFunction);
        this.biDataInitService.flushFunction();
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getJsFunction() {
        return this.jsFunction;
    }

    public BiDataInitService getBiDataInitService() {
        return this.biDataInitService;
    }
}
