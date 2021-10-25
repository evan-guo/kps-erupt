//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.CodeEditorType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.ShowBy;
import xyz.erupt.annotation.sub_field.sub_edit.SliderType;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType.Type;
import xyz.erupt.upms.model.base.HyperModel;

@Entity
@Table(
        name = "e_bi_chart",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"code", "bi_id"}
        )}
)
@Erupt(
        name = "图表配置",
        orderBy = "sort"
)
public class BiChart extends HyperModel {
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
    @EruptField(
            views = {@View(
                    title = "栅格数"
            )},
            edit = @Edit(
                    title = "栅格数",
                    type = EditType.SLIDER,
                    desc = "单行可以显示的图表数量",
                    notNull = true,
                    sliderType = @SliderType(
                            max = 12,
                            markPoints = {1, 2, 3, 4, 6, 8, 12},
                            dots = true
                    )
            )
    )
    private Integer grid = 1;
    @EruptField(
            views = {@View(
                    title = "高度(px)"
            )},
            edit = @Edit(
                    title = "高度(px)",
                    notNull = true
            )
    )
    private Integer height = 340;
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
    @ManyToOne
    @EruptField(
            edit = @Edit(
                    title = "处理类",
                    type = EditType.REFERENCE_TABLE
            )
    )
    private BiClassHandler classHandler;
    @ManyToOne
    @EruptField(
            views = {@View(
                    title = "数据源",
                    column = "name"
            )},
            edit = @Edit(
                    title = "数据源",
                    type = EditType.REFERENCE_TREE,
                    search = @Search
            )
    )
    private BiDataSource dataSource;
    @EruptField(
            views = {@View(
                    title = "图表类型"
            )},
            edit = @Edit(
                    title = "图表类型",
                    notNull = true,
                    desc = "图表参考：G2Plot",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            type = Type.RADIO,
                            vl = {@VL(
                                    label = "折线图",
                                    value = "Line"
                            ), @VL(
                                    label = "阶梯折线图",
                                    value = "StepLine"
                            ), @VL(
                                    label = "柱状图",
                                    value = "Column"
                            ), @VL(
                                    label = "堆叠柱状图",
                                    value = "StackedColumn"
                            ), @VL(
                                    label = "面积图",
                                    value = "Area"
                            ), @VL(
                                    label = "百分比面积图",
                                    value = "PercentageArea"
                            ), @VL(
                                    label = "条形图",
                                    value = "Bar"
                            ), @VL(
                                    label = "百分比条形图",
                                    value = "PercentStackedBar"
                            ), @VL(
                                    label = "散点图",
                                    value = "Scatter"
                            ), @VL(
                                    label = "饼图",
                                    value = "Pie"
                            ), @VL(
                                    label = "环形图",
                                    value = "Ring"
                            ), @VL(
                                    label = "玫瑰图",
                                    value = "Rose"
                            ), @VL(
                                    label = "雷达图",
                                    value = "Radar"
                            ), @VL(
                                    label = "气泡图",
                                    value = "Bubble",
                                    desc = "需要4个数据列：x / y / series / size"
                            ), @VL(
                                    label = "瀑布图",
                                    value = "Waterfall",
                                    desc = "需要2个数据列：x:名称 y:增加或减少的值"
                            ), @VL(
                                    label = "漏斗图",
                                    value = "Funnel"
                            ), @VL(
                                    label = "自定义模板",
                                    value = "tpl",
                                    desc = "使用前请确认是否导入erupt-tpl模块"
                            )}
                    )
            )
    )
    private String type;
    @EruptField(
            edit = @Edit(
                    title = "模板地址",
                    desc = "项目内路径，解析方式为Thymeleaf，可通过data获取图表数据，option获取自定义图表参数",
                    showBy = @ShowBy(
                            dependField = "type",
                            expr = "value=='tpl'"
                    )
            )
    )
    private String path;
    @Lob
    @org.hibernate.annotations.Type(
            type = "org.hibernate.type.TextType"
    )
    @EruptField(
            views = {@View(
                    title = "图表SQL"
            )},
            edit = @Edit(
                    title = "图表SQL",
                    desc = "规则：二维切片，三维切片，维度顺序：X -> Y -> Series",
                    type = EditType.CODE_EDITOR,
                    notNull = true,
                    codeEditType = @CodeEditorType(
                            language = "sql"
                    )
            )
    )
    private String sqlStatement;
    @Column(
            length = 5000
    )
    @EruptField(
            edit = @Edit(
                    title = "自定义图表配置",
                    desc = "JSON格式，参照G2Plot",
                    type = EditType.CODE_EDITOR,
                    codeEditType = @CodeEditorType(
                            language = "json"
                    )
            )
    )
    private String chartOption;
    @ManyToOne
    private Bi bi;

    public BiChart() {
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public Integer getGrid() {
        return this.grid;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getSort() {
        return this.sort;
    }

    public BiClassHandler getClassHandler() {
        return this.classHandler;
    }

    public BiDataSource getDataSource() {
        return this.dataSource;
    }

    public String getType() {
        return this.type;
    }

    public String getPath() {
        return this.path;
    }

    public String getSqlStatement() {
        return this.sqlStatement;
    }

    public String getChartOption() {
        return this.chartOption;
    }

    public Bi getBi() {
        return this.bi;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setGrid(final Integer grid) {
        this.grid = grid;
    }

    public void setHeight(final Integer height) {
        this.height = height;
    }

    public void setSort(final Integer sort) {
        this.sort = sort;
    }

    public void setClassHandler(final BiClassHandler classHandler) {
        this.classHandler = classHandler;
    }

    public void setDataSource(final BiDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public void setSqlStatement(final String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public void setChartOption(final String chartOption) {
        this.chartOption = chartOption;
    }

    public void setBi(final Bi bi) {
        this.bi = bi;
    }
}
