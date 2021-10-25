//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.CodeEditorType;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.model.EruptUserVo;

@Entity
@Table(
        name = "e_bi_history"
)
@Erupt(
        name = "报表历史记录",
        orderBy = "operateTime desc",
        power = @Power(
                edit = false,
                add = false,
                viewDetails = false
        )
)
public class BiHistory extends BaseModel {
    @EruptField(
            views = {@View(
                    title = "操作人",
                    column = "name"
            )}
    )
    @ManyToOne
    private EruptUserVo operateUser;
    @EruptField(
            views = {@View(
                    title = "操作时间"
            )}
    )
    private Date operateTime;
    @Lob
    @Type(
            type = "org.hibernate.type.TextType"
    )
    @EruptField(
            views = {@View(
                    title = "SQL语句"
            )},
            edit = @Edit(
                    title = "SQL语句",
                    type = EditType.CODE_EDITOR,
                    codeEditType = @CodeEditorType(
                            language = "sql"
                    )
            )
    )
    private String sqlStatement;
    @ManyToOne
    private Bi bi;

    public BiHistory() {
    }

    public EruptUserVo getOperateUser() {
        return this.operateUser;
    }

    public Date getOperateTime() {
        return this.operateTime;
    }

    public String getSqlStatement() {
        return this.sqlStatement;
    }

    public Bi getBi() {
        return this.bi;
    }

    public void setOperateUser(final EruptUserVo operateUser) {
        this.operateUser = operateUser;
    }

    public void setOperateTime(final Date operateTime) {
        this.operateTime = operateTime;
    }

    public void setSqlStatement(final String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public void setBi(final Bi bi) {
        this.bi = bi;
    }
}
