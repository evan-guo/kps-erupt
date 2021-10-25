//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xyz.erupt.bi.model;

import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTreeType;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.model.EruptMenu;

@Erupt(
    name = "弹窗"
)
public class BiReleaseModal extends BaseModel {
    @EruptField(
        edit = @Edit(
    title = "菜单位置",
    desc = "发布至根目录可跳过此选项",
    type = EditType.REFERENCE_TREE,
    referenceTreeType = @ReferenceTreeType(
    pid = "parentMenu.id"
)
)
    )
    private EruptMenu eruptMenu;

    public BiReleaseModal() {
    }

    public EruptMenu getEruptMenu() {
        return this.eruptMenu;
    }
}
