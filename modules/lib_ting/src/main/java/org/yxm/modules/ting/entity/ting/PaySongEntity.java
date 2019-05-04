package org.yxm.modules.ting.entity.ting;

public class PaySongEntity {

  public String error_code;
  public BitrateBean bitrate;
  public SonginfoBean songinfo;

  public static class BitrateBean {

    public String file_bitrate;
    public String free;
    public String file_link;
    public String file_extension;
    public String original;
    public String file_size;
    public String file_duration;
    public String show_link;
    public String song_file_id;
    public String replay_gain;

    @Override
    public String toString() {
      return "BitrateBean{" +
          "file_bitrate='" + file_bitrate + '\'' +
          ", free='" + free + '\'' +
          ", file_link='" + file_link + '\'' +
          ", file_extension='" + file_extension + '\'' +
          ", original='" + original + '\'' +
          ", file_size='" + file_size + '\'' +
          ", file_duration='" + file_duration + '\'' +
          ", show_link='" + show_link + '\'' +
          ", song_file_id='" + song_file_id + '\'' +
          ", replay_gain='" + replay_gain + '\'' +
          '}';
    }
  }

  public static class SonginfoBean {

    public String artist_id;
    public String all_artist_id;
    public String album_no;
    public String pic_big;
    public String pic_small;
    public String relate_status;
    public String resource_type;
    public String copy_type;
    public String lrclink;
    public String pic_radio;
    public String toneid;
    public String all_rate;
    public String play_type;
    public String has_mv_mobile;
    public String pic_premium;
    public String pic_huge;
    public String resource_type_ext;
    public String bitrate_fee;
    public String publishtime;
    public String si_presale_flag;
    public String del_status;
    public String song_id;
    public String title;
    public String ting_uid;
    public String author;
    public String album_id;
    public String album_title;
    public String is_first_publish;
    public String havehigh;
    public String charge;
    public String has_mv;
    public String learn;
    public String song_source;
    public String piao_id;
    public String korean_bb_song;
    public String mv_provider;
    public String special_type;
    public String collect_num;
    public String share_num;
    public String comment_num;

    @Override
    public String toString() {
      return "SonginfoBean{" +
          "artist_id='" + artist_id + '\'' +
          ", all_artist_id='" + all_artist_id + '\'' +
          ", album_no='" + album_no + '\'' +
          ", pic_big='" + pic_big + '\'' +
          ", pic_small='" + pic_small + '\'' +
          ", relate_status='" + relate_status + '\'' +
          ", resource_type='" + resource_type + '\'' +
          ", copy_type='" + copy_type + '\'' +
          ", lrclink='" + lrclink + '\'' +
          ", pic_radio='" + pic_radio + '\'' +
          ", toneid='" + toneid + '\'' +
          ", all_rate='" + all_rate + '\'' +
          ", play_type='" + play_type + '\'' +
          ", has_mv_mobile='" + has_mv_mobile + '\'' +
          ", pic_premium='" + pic_premium + '\'' +
          ", pic_huge='" + pic_huge + '\'' +
          ", resource_type_ext='" + resource_type_ext + '\'' +
          ", bitrate_fee='" + bitrate_fee + '\'' +
          ", publishtime='" + publishtime + '\'' +
          ", si_presale_flag='" + si_presale_flag + '\'' +
          ", del_status='" + del_status + '\'' +
          ", song_id='" + song_id + '\'' +
          ", title='" + title + '\'' +
          ", ting_uid='" + ting_uid + '\'' +
          ", author='" + author + '\'' +
          ", album_id='" + album_id + '\'' +
          ", album_title='" + album_title + '\'' +
          ", is_first_publish='" + is_first_publish + '\'' +
          ", havehigh='" + havehigh + '\'' +
          ", charge='" + charge + '\'' +
          ", has_mv='" + has_mv + '\'' +
          ", learn='" + learn + '\'' +
          ", song_source='" + song_source + '\'' +
          ", piao_id='" + piao_id + '\'' +
          ", korean_bb_song='" + korean_bb_song + '\'' +
          ", mv_provider='" + mv_provider + '\'' +
          ", special_type='" + special_type + '\'' +
          ", collect_num='" + collect_num + '\'' +
          ", share_num='" + share_num + '\'' +
          ", comment_num='" + comment_num + '\'' +
          '}';
    }
  }
}
