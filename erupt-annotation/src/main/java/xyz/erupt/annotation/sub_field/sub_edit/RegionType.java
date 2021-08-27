package xyz.erupt.annotation.sub_field.sub_edit;

import xyz.erupt.annotation.config.Comment;

import java.beans.Transient;

public @interface RegionType {

    @Comment("ui style")
    RegionType.Type type() default RegionType.Type.CASCADE;

    @Comment("省市区依赖的字段名，不需要依赖的用null代替")
    String[] referenceColumns() default {"province", "city", "county"};

    @Comment("展示列名")
    String showColumn() default "name";

    @Comment("查询时传的列名")
    String searchColumn() default "id";

    enum Type {
        @xyz.erupt.annotation.config.Comment("级联选择框")
        CASCADE
    }


}
