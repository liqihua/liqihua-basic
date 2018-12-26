package com.liqihua.modules.person.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * <p>
 * 测试人员
 * </p>
 *
 * @author liqihua
 * @since 2018-12-26
 */


@ApiModel(value="TestPersonVO")
public class TestPersonVO {


    private Long id;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("性别：0女 1男")
    private Boolean gender;

    @ApiModelProperty("睡觉时间")
    private LocalTime sleepTime;

    @ApiModelProperty("上班时间")
    private LocalDateTime workTime;

    @ApiModelProperty("出生时间")
    private LocalDate birthday;

    @ApiModelProperty("个人简介")
    private String intro;

    @ApiModelProperty("创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
    public LocalTime getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(LocalTime sleepTime) {
        this.sleepTime = sleepTime;
    }
    public LocalDateTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(LocalDateTime workTime) {
        this.workTime = workTime;
    }
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }



    @Override
    public String toString() {
        return "TestPersonEntity{" +
        "id=" + id +
        ", avatar=" + avatar +
        ", name=" + name +
        ", password=" + password +
        ", age=" + age +
        ", gender=" + gender +
        ", sleepTime=" + sleepTime +
        ", workTime=" + workTime +
        ", birthday=" + birthday +
        ", intro=" + intro +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
