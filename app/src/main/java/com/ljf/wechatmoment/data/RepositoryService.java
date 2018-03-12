package com.ljf.wechatmoment.data;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RepositoryService {
    @GET("user/jsmith")
    Call<UserInfo> userInfo();

    @GET("user/jsmith/tweets")
    Call<List<Tweet>> tweets();
}
