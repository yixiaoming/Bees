package org.yxm.modules.ting.net.api;

import java.util.Map;
import org.yxm.modules.ting.entity.ting.PaySongEntity;
import org.yxm.modules.ting.entity.ting.RecommandSongListEntity;
import org.yxm.modules.ting.entity.ting.SearchSongEntity;
import org.yxm.modules.ting.entity.ting.SongBillListEntity;
import org.yxm.modules.ting.entity.ting.SongLrcEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ITingApi {

  String BASE_URL = "http://tingapi.ting.baidu.com/";
  String SUB_URL = "v1/restserver/ting";

  /**
   * 播放音乐
   */
  @GET(SUB_URL)
  Call<PaySongEntity> getPaySongData(@QueryMap Map<String, String> params);

  /**
   * 歌词
   */
  @GET(SUB_URL)
  Call<SongLrcEntity> getSongLrcData(@QueryMap Map<String, String> params);

  /**
   * 获取列表
   */
  @GET(SUB_URL)
  Call<SongBillListEntity> getSongBillListData(@QueryMap Map<String, String> params);

  /**
   * 搜索
   */
  @GET(SUB_URL)
  Call<SearchSongEntity> getSearchSongData(@QueryMap Map<String, String> params);

  /**
   * 获取歌手歌曲列表
   */
  @GET(SUB_URL)
  Call<SongLrcEntity> getSingerSongList(@QueryMap Map<String, String> params);

  /**
   * 推荐列表
   */
  @GET(SUB_URL)
  Call<RecommandSongListEntity> getRecommandSongList(@QueryMap Map<String, String> params);

  /**
   * 下载
   */
  @GET(SUB_URL)
  Call<SongLrcEntity> downSong(@QueryMap Map<String, String> params);

  /**
   * 获取歌手信息
   */
  @GET(SUB_URL)
  Call<SongLrcEntity> getSingerInfo(@QueryMap Map<String, String> params);


}