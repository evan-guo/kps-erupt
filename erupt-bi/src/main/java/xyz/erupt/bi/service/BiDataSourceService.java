//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.service;

import com.zaxxer.hikari.HikariDataSource;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.bi.model.BiDataSource;
import xyz.erupt.core.exception.EruptApiErrorTip;

@Service
public class BiDataSourceService implements DataProxy<BiDataSource> {
    private final Map<String, NamedParameterJdbcTemplate> templateMap = new HashMap();
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BiDataSourceService() {
    }

    public NamedParameterJdbcTemplate getJdbcTemplate(BiDataSource biDataSource) {
        if (null == biDataSource) {
            return this.namedParameterJdbcTemplate;
        } else {
            NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) this.templateMap.get(biDataSource.getCode());
            if (null == jdbcTemplate) {
                synchronized (this) {
                    jdbcTemplate = (NamedParameterJdbcTemplate) this.templateMap.get(biDataSource.getCode());
                    if (null != jdbcTemplate) {
                        return jdbcTemplate;
                    } else {
                        HikariDataSource hikariDataSource = new HikariDataSource();
                        hikariDataSource.setReadOnly(true);
                        hikariDataSource.setDriverClassName(biDataSource.getDriver());
                        hikariDataSource.setJdbcUrl(biDataSource.getUrl());
                        hikariDataSource.setPassword(biDataSource.getPassword());
                        hikariDataSource.setUsername(biDataSource.getUserName());
                        return (NamedParameterJdbcTemplate) this.templateMap.put(biDataSource.getCode(), new NamedParameterJdbcTemplate(hikariDataSource));
                    }
                }
            } else {
                return jdbcTemplate;
            }
        }
    }

    public void beforeAdd(BiDataSource biDataSource) {
        try {
            Class.forName(biDataSource.getDriver());
        } catch (ClassNotFoundException var3) {
            throw new EruptApiErrorTip("找不到驱动类，请检查JDBC驱动包是否在项目内");
        }
    }

    public void beforeUpdate(BiDataSource biDataSource) {
        this.beforeAdd(biDataSource);
    }

    public void afterUpdate(BiDataSource biDataSource) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = (NamedParameterJdbcTemplate) this.templateMap.remove(biDataSource.getCode());
        if (null != namedParameterJdbcTemplate) {
            HikariDataSource hikariDataSource = (HikariDataSource) namedParameterJdbcTemplate.getJdbcTemplate().getDataSource();
            if (hikariDataSource != null && !hikariDataSource.isClosed()) {
                hikariDataSource.close();
            }
        }

    }

    public void afterDelete(BiDataSource biDataSource) {
        this.afterUpdate(biDataSource);
    }
}
