package com.zgzx.metaphysics.model.entity;

/**
 * 版本更新
 */
public class VersionUpdateEntity {


    /**
     * version_code : 1
     * version_name : 1.1.1
     * forced : 1
     * download_url : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/uploads/2020/08/03/1596424799597094400.png
     * update_content : 1. 更新了xx<br>2. 更新了xxx
     */

    private int version_code;
    private String version_name;
    private int forced;
    private String download_url;
    private String update_content;
    private String register_page;

    public VersionUpdateEntity() {
    }

    public VersionUpdateEntity(int version_code, String version_name, int forced, String download_url, String update_content,String uregister_page) {
        this.version_code = version_code;
        this.version_name = version_name;
        this.forced = forced;
        this.download_url = download_url;
        this.update_content = update_content;
        this.register_page=uregister_page;
    }

    public boolean isForce(){
        return forced == 1;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getForced() {
        return forced;
    }

    public void setForced(int forced) {
        this.forced = forced;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getUpdate_content() {
        return update_content;
    }

    public void setUpdate_content(String update_content) {
        this.update_content = update_content;
    }

    public String getUregister_page() {
        return register_page;
    }

    public void setUregister_page(String uregister_page) {
        this.register_page = uregister_page;
    }
}
