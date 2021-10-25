package xyz.erupt.annotation.sub_field.sub_view;

import xyz.erupt.annotation.config.Comment;
import xyz.erupt.annotation.sub_field.StatisticalType;

/**
 * @author Evan Guo
 * date 2021-10-11.
 */
public @interface Statistical {

    @Comment("是否需要统计")
    boolean value() default false;

    @Comment("统计类型")
    StatisticalType type() default StatisticalType.SUM;

    @Comment("是否需要货币格式化，当 type 为 sum、average、max、min 时有效")
    boolean currency() default false;

}
