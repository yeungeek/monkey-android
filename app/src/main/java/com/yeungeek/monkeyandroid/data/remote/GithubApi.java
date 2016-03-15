package com.yeungeek.monkeyandroid.data.remote;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.yeungeek.monkeyandroid.BuildConfig;
import com.yeungeek.monkeyandroid.data.model.AccessTokenResp;
import com.yeungeek.monkeyandroid.data.model.Repo;
import com.yeungeek.monkeyandroid.data.model.User;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yeungeek on 2016/1/10.
 */

public interface GithubApi {
    String ENDPOINT = "https://api.github.com/";
    String AUTH_HEADER = "Authorization";

    String CLIENT_ID = "d3cf434537678c39e406";
    String CLIENT_SECRET = "60426efe8d81483492a111fb6fb421e615465b73";

    String initialScope = "user,public_repo,repo";
    String AUTH_URL = "https://www.github.com/login/oauth/authorize?client_id=" +
            CLIENT_ID + "&" + "scope=" + initialScope;

    @GET("users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);

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

    /**********
     * Factory class that sets up a new github services
     *******/
    class Factory {
        public static GithubApi createGithubApi(final Context context) {
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            okHttpClient.interceptors().add()
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            //@see https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor
            final OkHttpClient okHttpClient = builder.addInterceptor(logging)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GithubApi.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(GithubApi.class);
        }
    }
}
