//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.transaction.Transactional;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.OperationHandler;
import xyz.erupt.annotation.sub_erupt.Drill;
import xyz.erupt.annotation.sub_erupt.Link;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_erupt.RowOperation.Mode;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.CodeEditorType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.util.Erupts;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.enums.MenuStatus;
import xyz.erupt.upms.model.EruptMenu;
import xyz.erupt.upms.model.base.HyperModel;
import xyz.erupt.upms.service.EruptContextService;
import xyz.erupt.upms.service.EruptUserService;

@Entity
@Table(
        name = "e_bi",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"code"}
        )}
)
@Erupt(
        name = "报表配置",
        rowOperation = {@RowOperation(
                title = "发布",
                mode = Mode.SINGLE,
                icon = "fa fa-send",
                eruptClass = BiReleaseModal.class,
                operationHandler = Bi.class
        )},
        dataProxy = {BiDataProxy.class},
        drills = {@Drill(
                title = "图表配置",
                icon = "fa fa-pie-chart",
                link = @Link(
                        linkErupt = BiChart.class,
                        joinColumn = "bi.id"
                )
        ), @Drill(
                title = "修改记录",
                icon = "fa fa-history",
                link = @Link(
                        linkErupt = BiHistory.class,
                        joinColumn = "bi.id"
                )
        )}
)
@Component
public class Bi extends HyperModel implements OperationHandler<Bi, BiReleaseModal> {
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
    @ManyToOne
    @JoinColumn(
            name = "datasource_id"
    )
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
            edit = @Edit(
                    title = "处理类",
                    type = EditType.REFERENCE_TABLE
            )
    )
    private BiClassHandler classHandler;
    @EruptField(
            views = {@View(
                    title = "自动刷新周期（秒）"
            )},
            edit = @Edit(
                    title = "自动刷新周期（秒）"
            )
    )
    private Integer refreshTime;
    @EruptField(
            views = {@View(
                    title = "导出"
            )},
            edit = @Edit(
                    title = "导出",
                    search = @Search,
                    notNull = true
            )
    )
    private Boolean export = true;
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
    @Transient
    @EruptField(
            views = {@View(
                    title = "效果预览",
                    type = ViewType.LINK_DIALOG,
                    desc = "需提前设置菜单权限"
            )}
    )
    private String view;
    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @JoinColumn(
            name = "bi_id"
    )
    private Set<BiChart> biCharts;
    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "bi_id"
    )
    @OrderBy("sort")
    @EruptField(
            edit = @Edit(
                    title = "查询维度",
                    type = EditType.TAB_TABLE_ADD
            )
    )
    private Set<BiDimension> biDimension;
    @Resource
    @Transient
    private EruptDao eruptDao;
    @Resource
    @Transient
    private EruptUserService eruptUserService;
    @Resource
    @Transient
    private EruptContextService eruptContextService;

    public Bi() {
    }

    @Transactional
    public String exec(List<Bi> data, BiReleaseModal biReleaseModal, String[] param) {
        Bi bi = (Bi)data.get(0);
        Erupts.requireNull(this.eruptDao.queryEntity(EruptMenu.class, String.format("code = '%s'", bi.getCode())), "菜单已存在请勿重复发布");
        Integer max = (Integer)this.eruptDao.getEntityManager().createQuery("select max(sort) from " + EruptMenu.class.getSimpleName()).getSingleResult();
        EruptMenu eruptMenu = new EruptMenu(bi.getCode(), bi.getName(), "bi", bi.getCode(), MenuStatus.OPEN.getValue(), max + 10, (String)null, biReleaseModal.getEruptMenu());
        this.eruptDao.persist(eruptMenu);
        this.eruptUserService.cacheUserInfo(this.eruptUserService.getCurrentEruptUser(), this.eruptContextService.getCurrentToken());
        return null;
    }

    public String getCode() {
        return this.code;
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

    public Integer getRefreshTime() {
        return this.refreshTime;
    }

    public Boolean getExport() {
        return this.export;
    }

    public String getSqlStatement() {
        return this.sqlStatement;
    }

    public String getView() {
        return this.view;
    }

    public Set<BiChart> getBiCharts() {
        return this.biCharts;
    }

    public Set<BiDimension> getBiDimension() {
        return this.biDimension;
    }

    public EruptDao getEruptDao() {
        return this.eruptDao;
    }

    public EruptUserService getEruptUserService() {
        return this.eruptUserService;
    }

    public EruptContextService getEruptContextService() {
        return this.eruptContextService;
    }

    public void setCode(final String code) {
        this.code = code;
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

    public void setRefreshTime(final Integer refreshTime) {
        this.refreshTime = refreshTime;
    }

    public void setExport(final Boolean export) {
        this.export = export;
    }

    public void setSqlStatement(final String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public void setView(final String view) {
        this.view = view;
    }

    public void setBiCharts(final Set<BiChart> biCharts) {
        this.biCharts = biCharts;
    }

    public void setBiDimension(final Set<BiDimension> biDimension) {
        this.biDimension = biDimension;
    }

    public void setEruptDao(final EruptDao eruptDao) {
        this.eruptDao = eruptDao;
    }

    public void setEruptUserService(final EruptUserService eruptUserService) {
        this.eruptUserService = eruptUserService;
    }

    public void setEruptContextService(final EruptContextService eruptContextService) {
        this.eruptContextService = eruptContextService;
    }
}
