package com.zgzx.metaphysics.model.entity;

import java.util.List;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/22
 * @Description: 首页数据
 */
public class HomeDataEntity {

    private List<BannerBean> banner;
    private List<NoticeBean> notice;
    private List<EntryBean> entry;
    private List<MasterBean> master;
    private List<ToolBean> tool;
    private WeatherBean weather;

    public HomeDataEntity() {
    }

    public HomeDataEntity(List<BannerBean> banner, List<NoticeBean> notice, List<EntryBean> entry, List<MasterBean> master, List<ToolBean> tool, WeatherBean weatherBean) {
        this.banner = banner;
        this.notice = notice;
        this.entry = entry;
        this.master = master;
        this.tool = tool;
        this.weather = weatherBean;
    }

    public WeatherBean getWeather() {
        return weather;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<NoticeBean> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeBean> notice) {
        this.notice = notice;
    }

    public List<EntryBean> getEntry() {
        return entry;
    }

    public void setEntry(List<EntryBean> entry) {
        this.entry = entry;
    }

    public List<MasterBean> getMaster() {
        return master;
    }

    public void setMaster(List<MasterBean> master) {
        this.master = master;
    }

    public List<ToolBean> getTool() {
        return tool;
    }

    public void setTool(List<ToolBean> tool) {
        this.tool = tool;
    }

    public static class BannerBean {
        /**
         * id : 1
         * title : banner1
         * image : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png
         * link_url :
         * create_time : 1597732119
         * update_time : 1597732119
         * status : 1
         * sort : 1
         */

        private int id;
        private String title;
        private String image;
        private String link_url;
        private int create_time;
        private int update_time;
        private int status;
        private int sort;

        public BannerBean() {
        }

        public BannerBean(String image, String link_url) {
            this.image = image;
            this.link_url = link_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public static class NoticeBean {
        /**
         * id : 1
         * title : 系统公告
         * content : 来源：经济日报
         * create_time : 1597732119
         * update_time : 1597732119
         * sort : 1
         */

        private int id;
        private String title;
        private String url;
        private String content;
        private int status;
        private int create_time;
        private int update_time;
        private int sort;

        public NoticeBean() {
        }

        public NoticeBean(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public static class EntryBean {
        /**
         * id : 1
         * name : 运程
         * icon : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png
         */

        private int id;
        private String name;
        private String icon;

        public EntryBean() {
        }

        public EntryBean(int id, String name, String icon) {
            this.id = id;
            this.name = name;
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class MasterBean {
        /**
         * id : 1
         * name : 陈定邦
         * master_id : 1
         * banner : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png
         */

        private int id;
        private String name;
        private String master_id;
        private String banner;

        public MasterBean() {
        }

        public MasterBean(String master_id, String banner) {
            this.master_id = master_id;
            this.banner = banner;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMaster_id() {
            return master_id;
        }

        public void setMaster_id(String master_id) {
            this.master_id = master_id;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }
    }

    public static class ToolBean {
        /**
         * id : 1
         * name : 易经占卜
         * link_url : https://www.baidu.com/
         * banner : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/default_avatar.png
         */

        private int id;
        private String name;
        private String link_url;
        private String banner;

        public ToolBean() {
        }

        public ToolBean(String link_url, String banner) {
            this.link_url = link_url;
            this.banner = banner;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }
    }

    public static class WeatherBean {
        private String city;
        private float temp;
        private float max_temp;
        private float min_temp;
        private String description;

        public WeatherBean() {
        }

        public WeatherBean(String city, float temp, float max_temp, float min_temp, String description) {
            this.city = city;
            this.temp = temp;
            this.max_temp = max_temp;
            this.min_temp = min_temp;
            this.description = description;
        }

        public String getCity() {
            return city;
        }

        public float getTemp() {
            return temp;
        }

        public float getMax_temp() {
            return max_temp;
        }

        public float getMin_temp() {
            return min_temp;
        }

        public String getDescription() {
            return description;
        }
    }
}
