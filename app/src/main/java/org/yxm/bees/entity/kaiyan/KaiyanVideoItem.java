package org.yxm.bees.entity.kaiyan;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanVideoItem {

    public String type;
    public Data data;

    @Override
    public String toString() {
        return "KaiyanVideoItem{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
        public String title;
        public String description;
        public Author author;
        public String playUrl;
        public Integer duration;
        public String date;

        @Override
        public String toString() {
            return "Data{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", author=" + author +
                    ", playUrl='" + playUrl + '\'' +
                    ", duration=" + duration +
                    ", date='" + date + '\'' +
                    '}';
        }

        public class Author {
            public String icon;
            public String name;
            public String description;

            @Override
            public String toString() {
                return "Author{" +
                        "icon='" + icon + '\'' +
                        ", name='" + name + '\'' +
                        ", description='" + description + '\'' +
                        '}';
            }
        }
    }
}
