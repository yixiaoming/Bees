package org.yxm.bees.entity.ting;


import android.os.Parcel;

import java.util.List;

public class SongBillListEntity {


    public BillboardBean billboard;
    public String error_code;
    public List<SongEntity> song_list;

    protected SongBillListEntity(Parcel in) {
        error_code = in.readString();
    }

    public static class BillboardBean {
        public String billboard_type;
        public String billboard_no;
        public String update_date;
        public String billboard_songnum;
        public String havemore;
        public String name;
        public String comment;
        public String pic_s640;
        public String pic_s444;
        public String pic_s260;
        public String pic_s210;
        public String web_url;

        @Override
        public String toString() {
            return "BillboardBean{" +
                    "billboard_type='" + billboard_type + '\'' +
                    ", billboard_no='" + billboard_no + '\'' +
                    ", update_date='" + update_date + '\'' +
                    ", billboard_songnum='" + billboard_songnum + '\'' +
                    ", havemore='" + havemore + '\'' +
                    ", name='" + name + '\'' +
                    ", comment='" + comment + '\'' +
                    ", pic_s640='" + pic_s640 + '\'' +
                    ", pic_s444='" + pic_s444 + '\'' +
                    ", pic_s260='" + pic_s260 + '\'' +
                    ", pic_s210='" + pic_s210 + '\'' +
                    ", web_url='" + web_url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SongBillListEntity{" +
                "billboard=" + billboard +
                ", error_code='" + error_code + '\'' +
                ", song_list=" + song_list +
                '}';
    }
}