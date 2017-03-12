package com.example.lzp.ganhuo.fragment.today;

import com.example.lzp.ganhuo.fragment.BaseItem;

import java.util.List;

/**
 * Created by SKJP on 2017/3/9.
 */

public class Today {

    private boolean error;
    private Results results;

    public static class Results {
        private List<Item> androids;
        private List<Item> ioss;
        private List<Item> videos;
        private List<Item> webs;
        private List<Item> resources;
        private List<Item> fulis;
        private List<Item> xias;

        public List<Item> getAndroids() {
            return androids;
        }

        public void setAndroids(List<Item> androids) {
            this.androids = androids;
        }

        public List<Item> getIoss() {
            return ioss;
        }

        public void setIoss(List<Item> ioss) {
            this.ioss = ioss;
        }

        public List<Item> getVideos() {
            return videos;
        }

        public void setVideos(List<Item> videos) {
            this.videos = videos;
        }

        public List<Item> getWebs() {
            return webs;
        }

        public void setWebs(List<Item> webs) {
            this.webs = webs;
        }

        public List<Item> getResources() {
            return resources;
        }

        public void setResources(List<Item> resources) {
            this.resources = resources;
        }

        public List<Item> getFulis() {
            return fulis;
        }

        public void setFulis(List<Item> fulis) {
            this.fulis = fulis;
        }

        public List<Item> getXias() {
            return xias;
        }

        public void setXias(List<Item> xias) {
            this.xias = xias;
        }

        public static class Android extends Item {

        }

        public static class Ios extends Item {

        }

        public static class Video extends Item {

        }

        public static class Web extends Item {

        }

        public static class Resource extends Item {

        }

        public static class Fuli extends Item {

        }

        public static class Xia extends Item {

        }

        public static class Item implements BaseItem {
            protected String _id;
            protected String createdAt;
            protected String desc;
            protected String publishedAt;
            protected String source;
            protected String type;
            protected String url;
            protected String used;
            protected String who;
            protected String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setUsed(String used) {
                this.used = used;
            }

            public void setWho(String who) {
                this.who = who;
            }

            public String get_id() {
                return _id;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public String getDesc() {
                return desc;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public String getSource() {
                return source;
            }

            public String getType() {
                return type;
            }

            public String getUrl() {
                return url;
            }

            public String getUsed() {
                return used;
            }

            public String getWho() {
                return who;
            }
        }
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}
