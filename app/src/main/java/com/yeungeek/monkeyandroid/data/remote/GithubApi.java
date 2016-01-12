package com.yeungeek.monkeyandroid.data.remote;

import android.content.Context;

import com.yeungeek.monkeyandroid.BuildConfig;
import com.yeungeek.monkeyandroid.data.model.Repo;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yeungeek on 2016/1/10.
 */
public interface GithubApi {
    String ENDPOINT = "https://api.github.com/";
    String AUTH_HEADER = "Authorization";

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    /********** Factory class that sets up a new github services *******/
    class Factory {
        public static GithubApi createGithubApi(final Context context) {
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            okHttpClient.interceptors().add()
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            //@see https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor
            final OkHttpClient okHttpClient = builder.addInterceptor(logging).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GithubApi.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(GithubApi.class);
        }
    }
}
