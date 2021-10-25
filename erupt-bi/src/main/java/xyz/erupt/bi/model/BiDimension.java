//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.CodeEditorType;
import xyz.erupt.annotation.sub_field.sub_edit.ShowBy;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType.Type;
import xyz.erupt.jpa.model.BaseModel;

@Entity
@Table(
        name = "e_bi_dimension"
)
@Erupt(
        name = "查询维度"
)
public class BiDimension extends BaseModel {
    @EruptField(
            views = {@View(
                    title = "维度编码"
            )},
            edit = @Edit(
                    title = "维度编码",
                    notNull = true
            )
    )
    private String code;
    @EruptField(
            views = {@View(
                    title = "名称"
            )},
            edit = @Edit(
                    title = "名称",
                    notNull = true
            )
    )
    private String title;
    @EruptField(
            views = {@View(
                    title = "显示顺序",
                    sortable = true
            )},
            edit = @Edit(
                    title = "显示顺序"
            )
    )
    private Integer sort;
    @EruptField(
            views = {@View(
                    title = "是否必填"
            )},
            edit = @Edit(
                    title = "是否必填",
                    notNull = true
            )
    )
    private Boolean notNull = true;
    @ManyToOne
    @EruptField(
            views = {@View(
                    title = "参照维度",
                    column = "name"
            )},
            edit = @Edit(
                    title = "参照维度",
                    type = EditType.REFERENCE_TABLE,
                    showBy = @ShowBy(
                            dependField = "type",
                            expr = "value && value.indexOf('REFERENCE') != -1"
                    )
            )
    )
    private BiDimensionReference biDimensionReference;
    @EruptField(
            views = {@View(
                    title = "维度类型"
            )},
            edit = @Edit(
                    title = "维度类型",
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            type = Type.RADIO,
                            vl = {@VL(
                                    value = "INPUT",
                                    label = "文本"
                            ), @VL(
                                    value = "NUMBER",
                                    label = "数值"
                            ), @VL(
                                    value = "NUMBER_RANGE",
                                    label = "数值区间"
                            ), @VL(
                                    value = "TAG",
                                    label = "标签"
                            ), @VL(
                                    value = "DATE",
                                    label = "日期"
                            ), @VL(
                                    value = "DATE_RANGE",
                                    label = "日期区间"
                            ), @VL(
                                    value = "DATETIME",
                                    label = "日期时间"
                            ), @VL(
                                    value = "DATETIME_RANGE",
                                    label = "日期时间区间"
                            ), @VL(
                                    value = "TIME",
                                    label = "时间"
                            ), @VL(
                                    value = "WEEK",
                                    label = "周"
                            ), @VL(
                                    value = "MONTH",
                                    label = "月"
                            ), @VL(
                                    value = "YEAR",
                                    label = "年"
                            ), @VL(
                                    value = "REFERENCE_TREE_RADIO",
                                    label = "单选树参照",
                                    desc = "返回三列：id/label/pid，pid为空代表树根节点"
                            ), @VL(
                                    value = "REFERENCE_TREE_MULTI",
                                    label = "多选树参照",
                                    desc = "返回三列：id/label/pid，pid为空代表树根节点"
                            ), @VL(
                                    value = "REFERENCE_CASCADE",
                                    label = "级联选择参照",
                                    desc = "返回三列：id/label/pid，pid为空代表树根节点"
                            ), @VL(
                                    value = "REFERENCE",
                                    label = "单选参照"
                            ), @VL(
                                    value = "REFERENCE_MULTI",
                                    label = "多选参照"
                            ), @VL(
                                    value = "REFERENCE_RADIO",
                                    label = "Radio参照"
                            ), @VL(
                                    value = "REFERENCE_CHECKBOX",
                                    label = "Checkbox参照"
                            )}
                    )
            )
    )
    private String type;
    @Column(
            length = 2000
    )
    @EruptField(
            views = {@View(
                    title = "默认值"
            )},
            edit = @Edit(
                    title = "默认值",
                    desc = "通过js脚本动态生成默认值，字符串请加引号",
                    type = EditType.CODE_EDITOR,
                    codeEditType = @CodeEditorType(
                            language = "javascript"
                    )
            )
    )
    private String defaultValue;
    @ManyToOne(
            cascade = {CascadeType.DETACH}
    )
    private Bi bi;

    public BiDimension() {
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getSort() {
        return this.sort;
    }

    public Boolean getNotNull() {
        return this.notNull;
    }

    public BiDimensionReference getBiDimensionReference() {
        return this.biDimensionReference;
    }

    public String getType() {
        return this.type;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public Bi getBi() {
        return this.bi;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setSort(final Integer sort) {
        this.sort = sort;
    }

    public void setNotNull(final Boolean notNull) {
        this.notNull = notNull;
    }

    public void setBiDimensionReference(final BiDimensionReference biDimensionReference) {
        this.biDimensionReference = biDimensionReference;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setBi(final Bi bi) {
        this.bi = bi;
    }
}
