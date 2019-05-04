package org.yxm.modules.ting.net;

import java.util.HashMap;
import java.util.Map;
import org.yxm.modules.ting.entity.ting.PaySongEntity;
import org.yxm.modules.ting.entity.ting.RecommandSongListEntity;
import org.yxm.modules.ting.entity.ting.SearchSongEntity;
import org.yxm.modules.ting.entity.ting.SongBillListEntity;
import retrofit2.Callback;


public class TingNetManager {

  /**
   * 播放音乐
   */
  public static void getPaySongData(String songid, Callback<PaySongEntity> callBack) {
    Map<String, String> params = NetHelper.getMusicApiCommonParams("baidu.ting.song.play");
    params.put("songid", songid);
    RetrofitManager.getInstance()
        .getThingApi()
        .getPaySongData(params)
        .enqueue(callBack);
  }


  /**
   * 推荐列表
   */
  public static void getRecommandSongList(String songid, String num,
      Callback<RecommandSongListEntity> callBack) {
    Map<String, String> params = NetHelper
        .getMusicApiCommonParams("baidu.ting.song.getRecommandSongList");
    params.put("songid", songid);
    params.put("num", num);
    RetrofitManager.getInstance()
        .getThingApi()
        .getRecommandSongList(params)
        .enqueue(callBack);
  }

  /**
   * 搜索
   */
  public static void getSearchSongData(String query,
      Callback<SearchSongEntity> callBack) {
    Map<String, String> params = NetHelper.getMusicApiCommonParams("baidu.ting.search.catalogSug");
    params.put("query", query);
    RetrofitManager.getInstance()
        .getThingApi()
        .getSearchSongData(params)
        .enqueue(callBack);
  }

  /**
   * @param type 1-新歌榜,2-热歌榜,11-摇滚榜,12-爵士,16-流行,21-欧美金曲榜,22-经典老歌榜,23-情歌对唱榜,24-影视金曲榜,25-网络歌曲榜
   * @param size 返回条目数量
   * @param offset 获取偏移
   */
  public static void getSongBillListData(int type, int size, int offset,
      Callback<SongBillListEntity> callBack) {
    Map<String, String> params = NetHelper.getMusicApiCommonParams("baidu.ting.billboard.billList");
    params.put("type", String.valueOf(type));
    params.put("size", String.valueOf(size));
    params.put("offset", String.valueOf(offset));
    RetrofitManager.getInstance()
        .getThingApi()
        .getSongBillListData(params)
        .enqueue(callBack);
  }

  public static class NetHelper {

    public static Map<String, String> getMusicApiCommonParams(String method) {
      Map params = new HashMap();
      params.put("format", "json");
      params.put("calback", "");
      params.put("from", "webapp_music");
      params.put("method", method);
      return params;
    }
  }
}