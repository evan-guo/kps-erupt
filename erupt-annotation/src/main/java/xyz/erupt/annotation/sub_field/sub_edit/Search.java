package xyz.erupt.annotation.sub_field.sub_edit;

import xyz.erupt.annotation.config.Comment;

/**
 * @author YuePeng
 * date 2018-09-28.
 */
public @interface Search {

    @Comment("是否开启搜索")
    boolean value() default true;

    @Comment("高级查询")
    boolean vague() default false;

    @Comment("是否必填")
    boolean notNull() default false;

    @Comment("默认值，JS表达式")
    String defaultValue() default "";

    @Comment("是否显示在页面上")
    boolean show() default true;

}
