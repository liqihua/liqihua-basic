package com.liqihua.crud.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author liqihua
 * @since 2018-10-11
 */
@TableName("test_person")
@ApiModel(value="TestPersonEntity对象", description="")
public class TestPersonEntity extends Model<TestPersonEntity> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "人员")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别：0女 1男")
    private Boolean gender;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "省id")
    private String provinceId;

    @ApiModelProperty(value = "市id")
    private String cityId;

    @ApiModelProperty(value = "区id")
    private String districtId;

    @ApiModelProperty(value = "出生时间")
    private LocalDate birthday;

    @ApiModelProperty(value = "上班时间")
    private LocalTime workTime;

    @ApiModelProperty(value = "个人简介")
    private String intro;

    private LocalDateTime createDate;

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
