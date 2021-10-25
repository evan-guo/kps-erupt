//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.service;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.transaction.Transactional;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import xyz.erupt.annotation.fun.VLModel;
import xyz.erupt.bi.model.Bi;
import xyz.erupt.bi.model.BiChart;
import xyz.erupt.bi.model.BiClassHandler;
import xyz.erupt.bi.model.BiDataSource;
import xyz.erupt.bi.model.BiDimensionReference;
import xyz.erupt.bi.model.BiFunction;
import xyz.erupt.bi.model.BiHistory;
import xyz.erupt.core.toolkit.TimeRecorder;
import xyz.erupt.core.util.ProjectUtil;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.enums.MenuStatus;
import xyz.erupt.upms.enums.MenuTypeEnum;
import xyz.erupt.upms.model.EruptMenu;

@Service
@Order
public class BiDataInitService implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(BiDataInitService.class);
    static String defineFunctions;
    @Resource
    private EruptDao eruptDao;

    public BiDataInitService() {
    }

    @Transactional
    public void run(String... args) throws Exception {
        MenuTypeEnum.addMenuType(new VLModel("bi", "报表", "报表编码"));
        (new ProjectUtil()).projectStartLoaded("bi", (first) -> {
            if (first) {
                String code = "code";
                String mbi = "$mbi";
                EruptMenu eruptMenu = (EruptMenu)this.eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu(mbi, "报表维护", (String)null, (String)null, MenuStatus.OPEN.getValue(), 20, "fa fa-table", (EruptMenu)null), code, mbi);
                this.eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu(BiDataSource.class.getSimpleName(), "数据源管理", MenuTypeEnum.TABLE.getCode(), BiDataSource.class.getSimpleName(), MenuStatus.OPEN.getValue(), 10, (String)null, eruptMenu), code, BiDataSource.class.getSimpleName());
                this.eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu(BiClassHandler.class.getSimpleName(), "报表处理类", MenuTypeEnum.TABLE.getCode(), BiClassHandler.class.getSimpleName(), MenuStatus.OPEN.getValue(), 20, (String)null, eruptMenu), code, BiClassHandler.class.getSimpleName());
                this.eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu(BiDimensionReference.class.getSimpleName(), "参照维度", MenuTypeEnum.TABLE.getCode(), BiDimensionReference.class.getSimpleName(), MenuStatus.OPEN.getValue(), 30, (String)null, eruptMenu), code, BiDimensionReference.class.getSimpleName());
                this.eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu(BiFunction.class.getSimpleName(), "函数管理", MenuTypeEnum.TABLE.getCode(), BiFunction.class.getSimpleName(), MenuStatus.OPEN.getValue(), 40, (String)null, eruptMenu), code, BiFunction.class.getSimpleName());
                EruptMenu eruptMenuBi = (EruptMenu)this.eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu(Bi.class.getSimpleName(), "报表配置", MenuTypeEnum.TABLE.getCode(), Bi.class.getSimpleName(), MenuStatus.OPEN.getValue(), 100, (String)null, eruptMenu), code, Bi.class.getSimpleName());
                this.eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu(BiChart.class.getSimpleName(), "图表配置", MenuTypeEnum.TABLE.getCode(), BiChart.class.getSimpleName(), MenuStatus.HIDE.getValue(), 10, (String)null, eruptMenuBi), code, BiChart.class.getSimpleName());
                this.eruptDao.persistIfNotExist(EruptMenu.class, new EruptMenu(BiHistory.class.getSimpleName(), "修改记录", MenuTypeEnum.TABLE.getCode(), BiHistory.class.getSimpleName(), MenuStatus.HIDE.getValue(), 20, (String)null, eruptMenuBi), code, BiHistory.class.getSimpleName());
                this.loadDefaultFunction();
            }

        });
        TimeRecorder timeRecorder = new TimeRecorder();
        this.flushFunction();
        log.info("Erupt bi initialization completed in {} ms", timeRecorder.recorder());
    }


    @SneakyThrows
    private void loadDefaultFunction() {
        try {
            String defaultFunctionCode = "default_function";
            this.eruptDao.persistIfNotExist(BiFunction.class, new BiFunction(defaultFunctionCode, defaultFunctionCode, StreamUtils.copyToString(BiDataInitService.class.getResourceAsStream("./BiDefaultFunction.js"), StandardCharsets.UTF_8)), "code", defaultFunctionCode);
        } catch (Throwable var2) {
            throw var2;
        }
    }

    public void flushFunction() {
        List<Object[]> list = this.eruptDao.queryObjectList(BiFunction.class, (String)null, (Map)null, new String[]{"jsFunction"});
        StringBuilder sb = new StringBuilder();
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Object o = var3.next();
            sb.append((String)o).append("\n");
        }

        defineFunctions = sb.toString();
    }
}
