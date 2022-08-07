package com.yyld.conair.ds.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ls")
@ApiModel(value="Ls对象", description="")
public class Ls extends Model<Ls> {

    private static final long serialVersionUID = 1L;


    @TableField("id")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("username")
    private String username;

    @TableField("name")
    private String name;

    @TableField("money")
    private Integer money;

    @TableField("time")
    private String time;

    @ApiModelProperty(value = "0下注 1充值 2中奖 默认0")
    @TableField("type")
    private String type;

    @TableField("summoney")
    private Integer summoney;

    @ApiModelProperty(value = "流水记录")
    @TableField("lsmoney")
    private Integer lsmoney;

    @ApiModelProperty(value = "费用描述")
    @TableField("remark")
    private String remark;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
