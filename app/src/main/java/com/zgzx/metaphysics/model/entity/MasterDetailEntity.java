package com.zgzx.metaphysics.model.entity;

import com.zgzx.metaphysics.ui.activities.EditMasterPhotoActivity;

import java.util.List;

/**
 * 师傅详情
 *
 * @see EditMasterPhotoActivity
 */
public class MasterDetailEntity {

    /**
     * intro : hello 大家好 练习时长两年半的老司机
     * fields : [{"id":4,"name":"起名"},{"id":5,"name":"改运"},{"id":6,"name":"八字"}]
     * photos : [{"id":3,"photo":"http://127.0.0.1:8781/api/v1/upload/1595320887-Banner_英文.png"},{"id":4,"photo":"http://127.0.0.1:8781/api/v1/upload/1595320887-logo.png"},{"id":7,"photo":"http://127.0.0.1:8781/api/v1/upload/1595320887-Banner_英文.png"}]
     */

    private String intro;
    private List<MasterServiceTypeEntity> fields;
    private List<MasterPhotoEntity> photos;

    public MasterDetailEntity() {
    }

    public MasterDetailEntity(String intro, List<MasterServiceTypeEntity> fields, List<MasterPhotoEntity> photos) {
        this.intro = intro;
        this.fields = fields;
        this.photos = photos;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<MasterServiceTypeEntity> getFields() {
        return fields;
    }

    public List<MasterPhotoEntity> getPhotos() {
        return photos;
    }

}
