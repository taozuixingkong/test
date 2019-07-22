package com.liuleilei.macbook.mydemo.net.retrofit

import com.liuleilei.macbook.mydemo.bean.HomeListResponse
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
create by liu
on2019/7/18
 retrofit请求api
 */
interface RetrofitService{
    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     * @param page page
     */
    @GET("/article/list/{page}/json")
    fun getHomeList(@Path("page") page: Int):Deferred<HomeListResponse>
}