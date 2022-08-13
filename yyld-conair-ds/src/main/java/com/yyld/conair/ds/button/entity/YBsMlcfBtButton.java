package com.yyld.conair.ds.button.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 业务类型分页按钮表
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Y_BS_MLCF_BT_BUTTON")
@ApiModel(value="YBsMlcfBtButton对象", description="业务类型分页按钮表")
public class YBsMlcfBtButton extends Model<YBsMlcfBtButton> {

    private static final long serialVersionUID = 1L;

    @TableId("ID_")
    private String id;

    @TableField("BT_KEY_")
    private String btKey;

    @TableField("BT_ID_")
    private String btId;

    @TableField("BUTTON_NAME_")
    private String buttonName;

    @TableField("BUTTON_POSITION_")
    private String buttonPosition;

    @TableField("BUTTON_SWITCH_")
    private String buttonSwitch;

    @TableField("BUTTON_ICON_")
    private String buttonIcon;

    @TableField("BUTTON_TYPE_")
    private String buttonType;

    @TableField("BUTTON_PLAIN_")
    private Integer buttonPlain;

    @TableField("BUTTON_ROUND_")
    private Integer buttonRound;

    @TableField("BUTTON_CIRCLE_")
    private Integer buttonCircle;

    @TableField("SUBTAB_")
    private String subtab;

    @TableField("BIND_FUNCTION_")
    private String bindFunction;

    @ApiModelProperty(value = "按钮编码取值于按钮全局配置表")
    @TableField("BUTTON_CODE_")
    private String buttonCode;

    @ApiModelProperty(value = "排序")
    @TableField("SN_")
    private Long sn;

    @ApiModelProperty(value = "按钮属性")
    @TableField("BUTTON_ATTRIBUTE_")
    private String buttonAttribute;

    @ApiModelProperty(value = "按钮风格")
    @TableField("BUTTON_STYLE_")
    private String buttonStyle;

    @ApiModelProperty(value = "风格类型")
    @TableField("STYLE_TYPE_")
    private String styleType;

    @ApiModelProperty(value = "按钮别名")
    @TableField("BUTTON_ALIAS_")
    private String buttonAlias;

    @ApiModelProperty(value = "按钮规则属性")
    @TableField("BUTTON_RULE_")
    private String buttonRule;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
