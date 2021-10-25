package xyz.erupt.annotation.sub_field;

import xyz.erupt.annotation.config.Comment;

/**
 * @author EvanGuo
 * date 2021-10-11.
 */
public enum StatisticalType {
    @Comment("次数")
    COUNT,
    @Comment("总计")
    SUM,
    @Comment("平均值")
    AVERAGE,
    @Comment("最大值")
    MAX,
    @Comment("最小值")
    MIN,
}