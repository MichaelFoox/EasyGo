package ua.nure.easygo.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ua.nure.easygo.model.Map;
import ua.nure.easygo.model.Point;
import ua.nure.easygo.model.User;

/**
 * Created by Oleg on 27.10.2016.
 */

public interface EasyGoService {
    @GET("maps")
    Call<List<Map>> getMaps();

    @GET("maps/{id}")
    Call<Map> getMap(@Path("id") long id);

    @GET("maps/{id}/points")
    Call<List<Point>> getPoints(@Path("id") long mapId);

    @GET("points/{id}")
    Call<Point> getPoint(@Path("id") long id);

    @GET("users/{login}")
    Call<User> getUser(@Path("login") String login);


    @POST("maps")
    Call<Void> postMap(@Body Map map);

    @POST("points")
    Call<Void> postPoint(@Body Point point);

    @POST("users")
    Call<Void> postUser(@Body User user);


    @DELETE("points/{id}")
    Call<Void> deletePoint(@Path("id") long id);

    @DELETE("maps/{id}")
    Call<Void> deleteMap(@Path("id") long id);

}
