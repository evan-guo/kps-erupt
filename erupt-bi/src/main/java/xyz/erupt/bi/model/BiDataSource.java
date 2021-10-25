//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.VLModel;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.CodeEditorType;
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.annotation.sub_field.sub_edit.ShowBy;
import xyz.erupt.bi.constant.DBTypeEnum;
import xyz.erupt.bi.service.BiDataSourceService;
import xyz.erupt.upms.model.base.HyperModel;

@Entity
@Table(
        name = "e_bi_datasource",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"code"}
        )}
)
@Erupt(
        name = "数据源",
        dataProxy = {BiDataSourceService.class}
)
public class BiDataSource extends HyperModel implements ChoiceFetchHandler {
    @EruptField(
            views = {@View(
                    title = "编码"
            )},
            edit = @Edit(
                    title = "编码",
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
    private String name;
    @EruptField(
            edit = @Edit(
                    title = "驱动",
                    notNull = true
            )
    )
    private String driver;
    @EruptField(
            views = {@View(
                    title = "连接字符串"
            )},
            edit = @Edit(
                    title = "连接字符串",
                    type = EditType.TEXTAREA,
                    notNull = true
            )
    )
    private String url;
    @EruptField(
            views = {@View(
                    title = "用户名"
            )},
            edit = @Edit(
                    title = "用户名",
                    notNull = true
            )
    )
    private String userName;
    @EruptField(
            edit = @Edit(
                    title = "密码",
                    inputType = @InputType(
                            type = "password"
                    )
            )
    )
    private String password;
    @EruptField(
            views = {@View(
                    title = "数据库类型"
            )},
            edit = @Edit(
                    title = "数据库类型",
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            fetchHandler = {BiDataSource.class}
                    )
            )
    )
    private String type;
    @Lob
    @Type(
            type = "org.hibernate.type.TextType"
    )
    @EruptField(
            edit = @Edit(
                    title = "分页语句",
                    type = EditType.CODE_EDITOR,
                    codeEditType = @CodeEditorType(
                            language = "sql"
                    ),
                    placeHolder = "select * from (@sql) t limit @size offset @skip",
                    desc = "分页变量：\n@sql：sql语句\n@size：展示条数\n@skip：跳过行数",
                    showBy = @ShowBy(
                            dependField = "type",
                            expr = "value === 'Other'"
                    )
            )
    )
    private String limitSql;
    @Column(
            length = 2000
    )
    @EruptField(
            views = {@View(
                    title = "备注",
                    type = ViewType.HTML
            )},
            edit = @Edit(
                    title = "备注",
                    type = EditType.TEXTAREA
            )
    )
    private String remark;

    public BiDataSource() {
    }

    public List<VLModel> fetch(String[] params) {
        return (List)Stream.of(DBTypeEnum.values()).map((it) -> {
            return new VLModel(it.name(), it.name());
        }).collect(Collectors.toList());
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDriver() {
        return this.driver;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getType() {
        return this.type;
    }

    public String getLimitSql() {
        return this.limitSql;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDriver(final String driver) {
        this.driver = driver;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setLimitSql(final String limitSql) {
        this.limitSql = limitSql;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }
}
