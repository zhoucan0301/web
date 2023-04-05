package com.example.estatemanagement.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name="tb_building")
public class Building implements Serializable {

    @Id
    private Integer id;//仪器序号
    private Double communityName;//温度9110
    private Double  communityId;//压强9130
    private Double name;//降雨量9140
    //private Integer totalHouseholds;
    //private String description;
    //private Date createTime;
    private Date updateTime;//更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCommunityName() {
        return communityName;
    }

    public void setCommunityName(Double communityName) {
        this.communityName = communityName;
    }

    public Double getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Double communityId) {
        this.communityId = communityId;
    }

    public Double getName() {
        return name;
    }

    public void setName(Double name) {
        this.name = name;
    }

   /** public Integer getTotalHouseholds() {
        return totalHouseholds;
    }

    public void setTotalHouseholds(Integer totalHouseholds) {
        this.totalHouseholds = totalHouseholds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }**/

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

