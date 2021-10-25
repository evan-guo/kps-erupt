//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.annotation.sub_erupt.Tpl.Engine;
import xyz.erupt.bi.fun.EruptBiHandler;
import xyz.erupt.bi.model.Bi;
import xyz.erupt.bi.model.BiChart;
import xyz.erupt.bi.model.BiClassHandler;
import xyz.erupt.bi.model.BiDimension;
import xyz.erupt.bi.model.BiDimensionReference;
import xyz.erupt.bi.service.BiService;
import xyz.erupt.bi.view.BiChartVo;
import xyz.erupt.bi.view.BiColumn;
import xyz.erupt.bi.view.BiData;
import xyz.erupt.bi.view.BiDimensionVo;
import xyz.erupt.bi.view.BiVo;
import xyz.erupt.bi.view.Reference;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.annotation.EruptRouter.VerifyMethod;
import xyz.erupt.core.annotation.EruptRouter.VerifyType;
import xyz.erupt.core.config.EruptProp;
import xyz.erupt.core.config.GsonFactory;
import xyz.erupt.core.exception.EruptNoLegalPowerException;
import xyz.erupt.core.util.EruptSpringUtil;
import xyz.erupt.core.util.Erupts;
import xyz.erupt.core.util.ExcelUtil;
import xyz.erupt.core.util.HttpUtil;
import xyz.erupt.core.util.SecurityUtil;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.tpl.service.EruptTplService;

@RestController
@RequestMapping({"/erupt-api/bi"})
public class EruptBiController {
    private static final Logger log = LoggerFactory.getLogger(EruptBiController.class);
    @Resource
    private EruptDao eruptDao;
    @Resource
    private BiService biService;
    private final Gson gson = GsonFactory.getGson();
    @Resource
    private EruptProp eruptProp;
    @Resource
    private HttpServletRequest request;
    @PersistenceContext
    private EntityManager entityManager;

    public EruptBiController() {
    }

    @RequestMapping({"/{code}"})
    @EruptRouter(
            verifyType = VerifyType.MENU,
            authIndex = 1
    )
    public BiVo getBuilder(@PathVariable("code") String code, HttpServletResponse response) {
        Bi bi = (Bi)this.eruptDao.queryEntity(Bi.class, "code = :code", new HashMap<String, Object>(1) {
            {
                this.put("code", code);
            }
        });
        if (null == bi) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        } else {
            BiVo biVo = new BiVo();
            if (StringUtils.isBlank(bi.getSqlStatement())) {
                biVo.setTable(false);
                biVo.setExport(false);
            } else {
                biVo.setTable(true);
                biVo.setExport(bi.getExport());
            }

            int maxSort = 9999;
            List<BiChartVo> biChartVos = new ArrayList();
            Iterator var7 = bi.getBiCharts().iterator();

            int var10001;
            while(var7.hasNext()) {
                BiChart chart = (BiChart)var7.next();
                BiChartVo biChartVo = new BiChartVo();
                biChartVo.setChartOption(chart.getChartOption());
                biChartVo.setId(chart.getId());
                biChartVo.setCode(chart.getCode());
                biChartVo.setGrid(chart.getGrid());
                biChartVo.setHeight(chart.getHeight());
                biChartVo.setName(chart.getName());
                biChartVo.setPath(chart.getPath());
                biChartVo.setType(chart.getType());
                if (chart.getSort() == null) {
                    ++maxSort;
                    var10001 = maxSort;
                } else {
                    var10001 = chart.getSort();
                }

                biChartVo.setSort(var10001);
                biChartVos.add(biChartVo);
                biVo.setCharts(biChartVos);
            }

            List<BiDimensionVo> biDimensionVos = new ArrayList();

            BiDimensionVo biDimensionVo;
            for(Iterator var14 = bi.getBiDimension().iterator(); var14.hasNext(); biDimensionVos.add(biDimensionVo)) {
                BiDimension dimension = (BiDimension)var14.next();
                biDimensionVo = new BiDimensionVo();
                biDimensionVo.setId(dimension.getId());
                biDimensionVo.setCode(dimension.getCode());
                biDimensionVo.setNotNull(dimension.getNotNull());
                biDimensionVo.setTitle(dimension.getTitle());
                biDimensionVo.setType(dimension.getType());
                if (dimension.getSort() == null) {
                    ++maxSort;
                    var10001 = maxSort;
                } else {
                    var10001 = dimension.getSort();
                }

                dimension.setSort(var10001);
                if (StringUtils.isNotBlank(dimension.getDefaultValue())) {
                    try {
                        biDimensionVo.setDefaultValue(BiService.evalScript(dimension.getDefaultValue()));
                    } catch (ScriptException var12) {
                        log.error("{}.{} -> {}", new Object[]{bi.getName(), dimension.getCode(), var12.getMessage()});
                    }
                }
            }

            if (null != bi.getRefreshTime() && bi.getRefreshTime() > 0) {
                biVo.setRefreshTime(bi.getRefreshTime());
            }

            biVo.setId(bi.getId());
            biVo.setCode(bi.getCode());
            biVo.setCharts((List)biChartVos.stream().sorted(Comparator.comparing(BiChartVo::getSort, Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList()));
            biVo.setDimensions((List)biDimensionVos.stream().sorted(Comparator.comparing(BiDimensionVo::getSort, Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList()));
            return biVo;
        }
    }

    @PostMapping({"/data/{code}"})
    @EruptRouter(
            verifyType = VerifyType.MENU,
            authIndex = 2
    )
    public BiData getData(@RequestParam("index") int pageIndex, @RequestParam("size") int pageSize, @RequestParam(value = "sort",required = false) String sort, @RequestBody Map<String, Object> query, @PathVariable String code) {
        pageSize = pageSize > 100 ? 100 : pageSize;
        Bi bi = (Bi)this.eruptDao.queryEntity(Bi.class, "code = :code", new HashMap<String, Object>(1) {
            {
                this.put("code", code);
            }
        });
        this.verifyBiMenuPermissions(bi, code);
        return this.biService.queryBiData(bi, pageIndex, pageSize, query, false);
    }

    @EruptRouter(
            verifyType = VerifyType.MENU,
            authIndex = 1
    )
    @RequestMapping({"/{code}/reference/{id}"})
    public List<Reference> refQuery(@PathVariable("id") Long dimId, @PathVariable String code, @RequestBody Map<String, Object> query) {
        BiDimension dimension = (BiDimension)this.entityManager.find(BiDimension.class, dimId);
        this.verifyBiMenuPermissions(dimension.getBi(), code);
        BiDimensionReference reference = dimension.getBiDimensionReference();
        List<Map<String, Object>> list = this.biService.startQuery(reference.getRefSql(), reference.getClassHandler(), reference.getDataSource(), query);
        List<Reference> references = new ArrayList();
        Iterator var8 = list.iterator();

        while(var8.hasNext()) {
            Map<String, Object> map = (Map)var8.next();
            if (map.keySet().size() == 1) {
                Object obj = map.values().iterator().next();
                references.add(new Reference(obj, obj));
            } else {
                Iterator iterator;
                if (map.keySet().size() >= 3) {
                    iterator = map.values().iterator();
                    references.add(new Reference(iterator.next(), iterator.next(), iterator.next()));
                } else {
                    iterator = map.values().iterator();
                    references.add(new Reference(iterator.next(), iterator.next()));
                }
            }
        }

        return references;
    }

    @EruptRouter(
            verifyType = VerifyType.MENU,
            authIndex = 1
    )
    @RequestMapping({"/{code}/chart/{id}"})
    public List<Map<String, Object>> biChart(@PathVariable("id") Long chartId, @RequestBody Map<String, Object> query, @PathVariable String code) {
        BiChart chart = (BiChart)this.entityManager.find(BiChart.class, chartId);
        this.verifyBiMenuPermissions(chart.getBi(), code);
        return this.biService.startQuery(chart.getSqlStatement(), chart.getClassHandler(), chart.getDataSource(), query);
    }

    @EruptRouter(
            verifyType = VerifyType.MENU,
            verifyMethod = VerifyMethod.PARAM,
            authIndex = 1
    )
    @RequestMapping({"/{code}/excel/{id}"})
    public void exportExcel(@PathVariable("id") Long id, @PathVariable("code") String code, @RequestParam("condition") String conditionStr, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IOException {
        if (!this.eruptProp.isCsrfInspect() || !SecurityUtil.csrfInspect(request, response)) {
            Bi bi = this.biService.findBi(id);
            this.verifyBiMenuPermissions(bi, code);
            Erupts.requireTrue(bi.getExport(), bi.getName() + "禁止导出！");
            Map<String, Object> condition = (Map)this.gson.fromJson(URLDecoder.decode(conditionStr, StandardCharsets.UTF_8.name()), (new TypeToken<Map<String, Object>>() {
            }).getType());
            BiData biData = this.biService.queryBiData(bi, 1, 100000, condition, true);
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet(bi.getName());
            sheet.createFreezePane(0, 1, 1, 1);
            Row headRow = sheet.createRow(0);
            CellStyle headStyle = ExcelUtil.beautifyExcelStyle(wb);
            Font headFont = wb.createFont();
            headFont.setColor(IndexedColors.WHITE.index);
            headStyle.setFont(headFont);
            headStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for(int i = 0; i < biData.getColumns().size(); ++i) {
                BiColumn biColumn = (BiColumn)biData.getColumns().get(i);
                Cell cell = headRow.createCell(i);
                cell.setCellStyle(headStyle);
                sheet.setColumnWidth(i, (biColumn.getName().length() + 10) * 256);
                cell.setCellValue(biColumn.getName());
            }

            CellStyle style = ExcelUtil.beautifyExcelStyle(wb);
            Font font = wb.createFont();
            font.setColor(IndexedColors.BLACK1.index);
            style.setFont(font);

            for(int i = 0; i < biData.getList().size(); ++i) {
                Row row = sheet.createRow(i + 1);
                Map<String, Object> map = (Map)biData.getList().get(i);

                for(int j = 0; j < biData.getColumns().size(); ++j) {
                    Object value = map.get(((BiColumn)biData.getColumns().get(j)).getName());
                    if (null != value) {
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(style);
                        cell.setCellValue(value.toString());
                    }
                }
            }

            if (null != bi.getClassHandler()) {
                BiClassHandler biClassHandler = bi.getClassHandler();
                EruptBiHandler biHandler = (EruptBiHandler)EruptSpringUtil.getBeanByPath(biClassHandler.getHandlerPath(), EruptBiHandler.class);
                biHandler.exportHandler(biClassHandler.getParam(), condition, wb);
            }

            wb.write(HttpUtil.downLoadFile(request, response, bi.getName() + ".xls"));
        }
    }

    @GetMapping(
            value = {"/{code}/custom-chart/{id}"},
            produces = {"text/html;charset=UTF-8"}
    )
    @EruptRouter(
            authIndex = 1,
            verifyType = VerifyType.MENU,
            verifyMethod = VerifyMethod.PARAM
    )
    public void customerChart(@PathVariable("id") Long chartId, @RequestParam("condition") String conditionStr, HttpServletResponse response, @PathVariable String code) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Map<String, Object> condition = (Map)this.gson.fromJson(URLDecoder.decode(conditionStr, StandardCharsets.UTF_8.name()), (new TypeToken<Map<String, Object>>() {
        }).getType());
        BiChart biChart = (BiChart)this.entityManager.find(BiChart.class, chartId);
        this.verifyBiMenuPermissions(biChart.getBi(), code);
        Map<String, Object> map = new HashMap();
        map.put("data", this.biService.startQuery(biChart.getSqlStatement(), biChart.getClassHandler(), biChart.getDataSource(), condition));
        EruptTplService eruptTplService = (EruptTplService)EruptSpringUtil.getBean(EruptTplService.class);
        eruptTplService.tplRender(Engine.Thymeleaf, biChart.getPath(), map, response.getWriter());
    }

    private void verifyBiMenuPermissions(Bi bi, String code) {
        String biCode = (String)Optional.ofNullable(this.request.getHeader("erupt")).orElse(this.request.getParameter("_erupt"));
        if (!biCode.equals(bi.getCode()) || !code.equals(bi.getCode())) {
            throw new EruptNoLegalPowerException();
        }
    }
}
