//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.view;

import java.util.List;
import java.util.Map;

public class BiData {
    private List<BiColumn> columns;
    private List<Map<String, Object>> list;
    private Long total;

    public BiData() {
    }

    public List<BiColumn> getColumns() {
        return this.columns;
    }

    public List<Map<String, Object>> getList() {
        return this.list;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setColumns(final List<BiColumn> columns) {
        this.columns = columns;
    }

    public void setList(final List<Map<String, Object>> list) {
        this.list = list;
    }

    public void setTotal(final Long total) {
        this.total = total;
    }
}
