//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.service;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import xyz.erupt.bi.constant.DBTypeEnum;
import xyz.erupt.bi.constant.ScriptPlaceholderConst;
import xyz.erupt.bi.fun.EruptBiHandler;
import xyz.erupt.bi.model.Bi;
import xyz.erupt.bi.model.BiClassHandler;
import xyz.erupt.bi.model.BiDataSource;
import xyz.erupt.bi.view.BiColumn;
import xyz.erupt.bi.view.BiData;
import xyz.erupt.core.util.EruptSpringUtil;
import xyz.erupt.core.util.Erupts;
import xyz.erupt.upms.service.EruptUserService;

@Service
public class BiService {
    private static final Logger log = LoggerFactory.getLogger(BiService.class);
    private static final String TOTAL_KEY = "count";
    @Resource
    private EruptUserService eruptUserService;
    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Resource
    private BiDataSourceService dataSourceService;
    private static final ScriptEngine scriptEngine = (new ScriptEngineManager()).getEngineByName("nashorn");
    private static final Pattern EXPRESS_PATTERN = Pattern.compile("(?<=\\$\\{)(.+?)(?=\\})");

    public BiService() {
    }

    private String getLimitSql(BiDataSource biDataSource) {
        if (null == biDataSource) {
            return "select * from (@sql) t limit @size offset @skip";
        } else {
            return StringUtils.isNotBlank(biDataSource.getLimitSql()) ? biDataSource.getLimitSql() : ((DBTypeEnum)Stream.of(DBTypeEnum.values()).filter((it) -> {
                return it.name().equals(biDataSource.getType());
            }).findFirst().orElse(DBTypeEnum.MySQL)).getLimitSql();
        }
    }

    public Bi findBi(Long id) {
        return (Bi)this.entityManager.find(Bi.class, id);
    }

    public BiData queryBiData(Bi bi, int pageIndex, int pageSize, Map<String, Object> query, boolean export) {
        Erupts.requireTrue(StringUtils.isNotBlank(bi.getSqlStatement()), "express not found");
        query.put(ScriptPlaceholderConst.EXPORT_PLACEHOLDER, export);
        query.put(ScriptPlaceholderConst.USER_ID_PLACEHOLDER, this.eruptUserService.getCurrentUid());
        query.put(ScriptPlaceholderConst.REQUEST_PLACEHOLDER, this.request);
        query.put(ScriptPlaceholderConst.RESPONSE_PLACEHOLDER, this.response);
        BiData biData = new BiData();
        if (!export) {
            biData.setTotal(this.getTotal(bi, query));
        }

        if (null != biData.getTotal() && biData.getTotal() <= 0L) {
            biData.setList(new ArrayList(0));
        } else {
            String sql = this.getLimitSql(bi.getDataSource()).replace("@sql", bi.getSqlStatement()).replace("@size", String.valueOf(pageSize)).replace("@skip", String.valueOf((pageIndex - 1) * pageSize));
            List<Map<String, Object>> list = this.startQuery(sql, bi.getClassHandler(), bi.getDataSource(), query);
            if (null != list && list.size() > 0) {
                List<BiColumn> biColumns = new LinkedList();
                Map<String, Object> map = (Map)list.get(0);
                map.keySet().forEach((key) -> {
                    biColumns.add(new BiColumn(key));
                });
                biData.setColumns(biColumns);
            }

            biData.setList(list);
        }

        return biData;
    }

    @SneakyThrows
    public List<Map<String, Object>> startQuery(String express, BiClassHandler classHandler, BiDataSource biDataSource, Map<String, Object> query) {
        try {
            EruptBiHandler biHandler = null;
            express = this.processPlaceHolder(express, query);
            if (null != classHandler) {
                biHandler = (EruptBiHandler)EruptSpringUtil.getBeanByPath(classHandler.getHandlerPath(), EruptBiHandler.class);
                express = biHandler.exprHandler(classHandler.getParam(), query, express);
            }

            NamedParameterJdbcTemplate jdbcTemplate = this.dataSourceService.getJdbcTemplate(biDataSource);
            List<Map<String, Object>> list = this.jdbcQuery(jdbcTemplate, express, query);
            Optional.ofNullable(biHandler).ifPresent((it) -> {
                it.resultHandler(classHandler.getParam(), query, list);
            });
            return list;
        } catch (Throwable var8) {
            throw var8;
        }
    }

    public static Object evalScript(String script, Bindings bindings) throws ScriptException {
        return null == bindings ? scriptEngine.eval(script) : scriptEngine.eval(script, bindings);
    }

    public static Object evalScript(String script) throws ScriptException {
        return evalScript(script, (Bindings)null);
    }


    @SneakyThrows
    private Long getTotal(Bi bi, Map<String, Object> query) {
        try {
            String express = this.processPlaceHolder(bi.getSqlStatement(), query);
            BiClassHandler biClassHandler = bi.getClassHandler();
            if (null != biClassHandler) {
                express = ((EruptBiHandler)EruptSpringUtil.getBeanByPath(biClassHandler.getHandlerPath(), EruptBiHandler.class)).exprHandler(biClassHandler.getParam(), query, express);
            }

            return Long.valueOf(this.dataSourceService.getJdbcTemplate(bi.getDataSource()).queryForMap(String.format("select count(*) %s from (%s) count_", "count", express), query).get("count").toString());
        } catch (Throwable var5) {
            throw var5;
        }
    }

    private List<Map<String, Object>> jdbcQuery(NamedParameterJdbcTemplate jdbcTemplate, String express, Map<String, Object> query) {
        log.info(express);
        return jdbcTemplate.query(express, query, (rs, i) -> {
            Map<String, Object> map = new LinkedHashMap();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for(int index = 1; index <= columnCount; ++index) {
                map.put(metaData.getColumnLabel(index), rs.getObject(index));
            }

            return map;
        });
    }

    @SneakyThrows
    private String processPlaceHolder(String express, Map<String, Object> param) {
        try {
            Bindings bindings = new SimpleBindings();
            Optional.ofNullable(param).ifPresent((it) -> {
                it.forEach((key, value) -> {
                    bindings.put(key, it.get(key));
                });
            });

            String exp;
            Object result;
            for(Matcher m = EXPRESS_PATTERN.matcher(express); m.find(); express = express.replace("${" + exp + "}", result.toString())) {
                exp = m.group();
                result = scriptEngine.eval(BiDataInitService.defineFunctions + "\n" + exp, bindings);
                result = Optional.ofNullable(result).orElse("");
            }

            return express;
        } catch (Throwable var7) {
            throw var7;
        }
    }
}
