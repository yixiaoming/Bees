package org.yxm.bees.entity.ting;


import android.os.Parcel;

import java.util.List;

public class SongBillListEntity {


    public BillboardBean billboard;
    public String error_code;
    public List<SongBillListEntity> song_list;

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


    }

}