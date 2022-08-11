package com.yyld.conair.ds.users.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("users")
//@ApiModel(value="Users对象", description="")
public class Users extends Model<Users> {

    private static final long serialVersionUID = 1L;

//    @TableField("id")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

//    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @TableField("name")
    private String name;

//    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

//    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

//    @ApiModelProperty(value = "手机号")
    @TableField("mobile")
    private String mobile;

//    @ApiModelProperty(value = "状态 0:禁用，1:正常")
    @TableField("status")
    private Integer status;

//    @ApiModelProperty(value = "豆数")
    @TableField("numberbeans")
    private Integer numberbeans;

//    @ApiModelProperty(value = "用户类型0普通用户，1管理员，2超级管理员")
    @TableField("usertype")
    private String usertype;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
