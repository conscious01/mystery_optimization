package com.zgzx.metaphysics.model.entity;

public class MasterPhotoEntity {

    /**
     * id : 3
     * photo : http://127.0.0.1:8781/api/v1/upload/1595320887-Banner_英文.png
     */

    private int id;
    private String photo;

    public MasterPhotoEntity() {
    }

    public MasterPhotoEntity(int id, String photo) {
        this.id = id;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
