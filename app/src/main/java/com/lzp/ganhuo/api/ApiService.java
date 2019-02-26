package com.lzp.ganhuo.api;

import com.lzp.ganhuo.model.bean.CategoryItem;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("http://gank.io/api/data/{category}/{count}/{page}")
    Observable<CategoryItem> categoryItem(@Path("category") String category, @Path("count") int count, @Path("page") int page);
}
