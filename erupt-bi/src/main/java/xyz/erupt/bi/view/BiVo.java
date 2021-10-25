//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.view;

import java.util.List;

public class BiVo {
    private Long id;
    private String code;
    private boolean export;
    private boolean table;
    private int refreshTime;
    private List<BiDimensionVo> dimensions;
    private List<BiChartVo> charts;

    public BiVo() {
    }

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public boolean isExport() {
        return this.export;
    }

    public boolean isTable() {
        return this.table;
    }

    public int getRefreshTime() {
        return this.refreshTime;
    }

    public List<BiDimensionVo> getDimensions() {
        return this.dimensions;
    }

    public List<BiChartVo> getCharts() {
        return this.charts;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setExport(final boolean export) {
        this.export = export;
    }

    public void setTable(final boolean table) {
        this.table = table;
    }

    public void setRefreshTime(final int refreshTime) {
        this.refreshTime = refreshTime;
    }

    public void setDimensions(final List<BiDimensionVo> dimensions) {
        this.dimensions = dimensions;
    }

    public void setCharts(final List<BiChartVo> charts) {
        this.charts = charts;
    }
}
