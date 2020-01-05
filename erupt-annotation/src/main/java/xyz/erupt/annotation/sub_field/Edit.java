package xyz.erupt.annotation.sub_field;

import xyz.erupt.annotation.config.SerializeBy;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Html;
import xyz.erupt.annotation.sub_field.sub_edit.*;

import java.beans.Transient;

/**
 * @author liyuepeng
 * @date 2018-09-28.
 */
public @interface Edit {

    String title();

    String desc() default "";

    boolean notNull() default false;

    boolean show() default true;

    boolean readOnly() default false;

    String placeHolder() default "";

    Search search() default @Search(false);

    @Transient
    String orderBy() default "";

    @Transient
    Filter filter() default @Filter(condition = "");

    EditType type() default EditType.AUTO;

    @SerializeBy(method = "type", value = "INPUT")
    InputType inputType() default @InputType;

    @SerializeBy(method = "type", value = "NUMBER")
    NumberType numberType() default @NumberType();

    @SerializeBy(method = "type", value = "BOOLEAN")
    BoolType boolType() default @BoolType(trueText = "是", falseText = "否");

    @Transient
    Html htmlType() default @Html;

    @SerializeBy(method = "type", value = "CHOICE")
    ChoiceType choiceType() default @ChoiceType;

    @SerializeBy(method = "type", value = "DATE")
    DateType dateType() default @DateType;

    @SerializeBy(method = "type", value = "SLIDER")
    SliderType sliderType() default @SliderType(max = 999);

    @SerializeBy(method = "type", value = "ATTACHMENT")
    AttachmentType attachmentType() default @AttachmentType;

    @SerializeBy(method = "type", value = "DEPEND_SWITCH")
    DependSwitchType dependSwitchType() default @DependSwitchType(attr = {});

    @SerializeBy(method = "type", value = "REFERENCE_TREE")
    ReferenceTreeType referenceTreeType() default @ReferenceTreeType;

    @SerializeBy(method = "type", value = "REFERENCE_TABLE")
    ReferenceTableType referenceTableType() default @ReferenceTableType;

}