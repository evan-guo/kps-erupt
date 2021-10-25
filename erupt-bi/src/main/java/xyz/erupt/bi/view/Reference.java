//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.view;

public class Reference {
    Object id;
    Object title;
    Object pid;

    public Reference(Object id, Object title) {
        this.id = id;
        this.title = title;
    }

    public Reference(Object id, Object title, Object pid) {
        this.id = id;
        this.title = title;
        this.pid = pid;
    }

    public Reference() {
    }

    public Object getId() {
        return this.id;
    }

    public Object getTitle() {
        return this.title;
    }

    public Object getPid() {
        return this.pid;
    }

    public void setId(final Object id) {
        this.id = id;
    }

    public void setTitle(final Object title) {
        this.title = title;
    }

    public void setPid(final Object pid) {
        this.pid = pid;
    }
}
