package com.yeungeek.monkeyandroid.data.remote;

import com.yeungeek.monkeyandroid.data.model.AccessTokenResp;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.data.model.RepoContent;
import com.yeungeek.monkeyandroid.data.model.User;
import com.yeungeek.monkeyandroid.data.model.WrapList;
import com.yeungeek.monkeyandroid.data.model.WrapUser;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yeungeek on 2016/1/10.
 */

public interface GithubApi {
    String ENDPOINT = "https://api.github.com/";
    String AUTH_HEADER = "Authorization";
    String AUTH_TOKEN = " token ";

    String CLIENT_ID = "d3cf434537678c39e406";
    String CLIENT_SECRET = "60426efe8d81483492a111fb6fb421e615465b73";

    String initialScope = "user,public_repo,repo";
    String AUTH_URL = "https://www.github.com/login/oauth/authorize?client_id=" +
            CLIENT_ID + "&" + "scope=" + initialScope;

    /*******
     * oauth. different endPoint, user override url
     ******/
    @Headers({
            "Accept: application/json"
    })
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Observable<AccessTokenResp> getOAuthToken(@Field("client_id") String client,
                                              @Field("client_secret") String clientSecret, @Field("code") String code);

    /**
     * sent in a header "Authorization: token OAUTH-TOKEN" https://api.github.com
     * sent as a parameter https://api.github.com/?access_token=OAUTH-TOKEN
     */
    @GET("user")
    Observable<User> getUserInfo(@Query(value = "access_token", encoded = true) String accessToken);

    @GET("search/repositories")
    Observable<WrapList<Repo>> getRepos(@Query("q") String query, @Query("page") int pageId);

    @GET("search/users")
    Observable<WrapList<User>> getUsers(@Query("q") String query, @Query("page") int pageId);

    @GET("repos/{owner}/{repo}/readme")
    Observable<RepoContent> getReadme(@Path("owner") String owner, @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/readme")
    Observable<RepoContent> getReadme(@Path("owner") String owner, @Path("repo") String repo,
                                      @Query(value = "access_token", encoded = true) String accessToken);

    @Headers({
            "Content-Length: 0"
    })
    @PUT("user/starred/{owner}/{repo}")
    Observable<Response<Void>> starRepo(@Path("owner") String owner, @Path("repo") String repo,
                                        @Query(value = "access_token", encoded = true) String accessToken);

    @GET("user/starred/{owner}/{repo}")
    Observable<Response<Void>> checkIfStaring(@Path("owner") String owner, @Path("repo") String repo,
                                              @Query(value = "access_token", encoded = true) String accessToken);

    @Headers({
            "Content-Length: 0"
    })
    @DELETE("user/starred/{owner}/{repo}")
    Observable<Response<Void>> unstarRepo(@Path("owner") String owner, @Path("repo") String repo,
                                          @Query(value = "access_token", encoded = true) String accessToken);

    @GET("users/{username}/starred")
    Observable<List<Repo>> listStarredRepo(@Path("username") String username);

    //user
    @GET("users/{username}")
    Observable<WrapUser> getSingleUser(@Path("username") String username);

    @GET("users/{username}")
    Observable<WrapUser> getSingleUser(@Path("username") String username, @Query(value = "access_token", encoded = true) String accessToken);

    @GET("users/{username}/followers")
    Observable<List<User>> getFollowers(@Path("username") String username, @Query("page") int pageId);  //authenticated

    @GET("users/{username}/followers")
    Observable<List<User>> getFollowers(@Path("username") String username, @Query("page") int pageId,
                                        @Query(value = "access_token", encoded = true) String accessToken);  //authenticated

    @GET("users/{username}/following")
    Observable<List<User>> getFollowing(@Path("username") String username, @Query("page") int pageId);

    @GET("users/{username}/following")
    Observable<List<User>> getFollowing(@Path("username") String username, @Query("page") int pageId,
                                        @Query(value = "access_token", encoded = true) String accessToken);

    @GET("user/following/{username}")
    Observable<Response<Void>> checkIfFollowing(@Path("username") String username,
                                                @Query(value = "access_token", encoded = true) String accessToken);

    @Headers({
            "Content-Length: 0"
    })
    @PUT("user/following/{username}")
    Observable<Response<Void>> followUser(@Path("username") String username,
                                          @Query(value = "access_token", encoded = true) String accessToken);

    @Headers({
            "Content-Length: 0"
    })
    @DELETE("user/following/{username}")
    Observable<Response<Void>> unfollowUser(@Path("username") String username,
                                            @Query(value = "access_token", encoded = true) String accessToken);

    //trending
    @GET("http://trending.codehub-app.com/v2/trending")
    Observable<List<Repo>> getTrendingRepo(@Query("language") String language, @Query("since") String since);
}
