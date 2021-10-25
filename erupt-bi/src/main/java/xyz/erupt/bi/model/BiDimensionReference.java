//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.CodeEditorType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.model.base.HyperModel;

@Entity
@Table(
        name = "e_bi_dimension_reference"
)
@Erupt(
        name = "参照维度"
)
public class BiDimensionReference extends HyperModel {
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
    @ManyToOne
    @EruptField(
            views = {@View(
                    title = "处理类",
                    column = "name"
            )},
            edit = @Edit(
                    title = "处理类",
                    type = EditType.REFERENCE_TABLE
            )
    )
    private BiClassHandler classHandler;
    @Lob
    @Type(
            type = "org.hibernate.type.TextType"
    )
    @EruptField(
            views = {@View(
                    title = "参照SQL"
            )},
            edit = @Edit(
                    title = "参照SQL",
                    type = EditType.CODE_EDITOR,
                    codeEditType = @CodeEditorType(
                            language = "sql"
                    ),
                    notNull = true
            )
    )
    private String refSql;

    public BiDimensionReference() {
    }

    public String getName() {
        return this.name;
    }

    public BiDataSource getDataSource() {
        return this.dataSource;
    }

    public BiClassHandler getClassHandler() {
        return this.classHandler;
    }

    public String getRefSql() {
        return this.refSql;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDataSource(final BiDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setClassHandler(final BiClassHandler classHandler) {
        this.classHandler = classHandler;
    }

    public void setRefSql(final String refSql) {
        this.refSql = refSql;
    }
}
