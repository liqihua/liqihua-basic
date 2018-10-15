package com.liqihua.crud.entity;

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

/**
 * <p>
 * 
 * </p>
 *
 * @author liqihua
 * @since 2018-10-15
 */
@TableName("test_person")
public class TestPersonEntity extends Model<TestPersonEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 人员
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别：0女 1男
     */
    private Boolean gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 省id
     */
    private String provinceId;

    /**
     * 市id
     */
    private String cityId;

    /**
     * 区id
     */
    private String districtId;

    /**
     * 出生时间
     */
    private LocalDate birthday;

    /**
     * 上班时间
     */
    private LocalTime workTime;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public LocalTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(LocalTime workTime) {
        this.workTime = workTime;
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
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TestPersonEntity{" +
        "id=" + id +
        ", name=" + name +
        ", password=" + password +
        ", age=" + age +
        ", gender=" + gender +
        ", avatar=" + avatar +
        ", provinceId=" + provinceId +
        ", cityId=" + cityId +
        ", districtId=" + districtId +
        ", birthday=" + birthday +
        ", workTime=" + workTime +
        ", intro=" + intro +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
