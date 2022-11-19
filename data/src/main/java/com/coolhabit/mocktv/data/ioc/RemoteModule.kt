package com.coolhabit.mocktv.data.ioc

import com.coolhabit.mocktv.data.network.ChannelsApi
import com.coolhabit.mocktv.data.utils.ApiConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val url = request.url.newBuilder().build()
            val resultRequest = request.newBuilder().url(url).build()
            chain.proceed(resultRequest)
        }
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideChannelsApi(retrofit: Retrofit): ChannelsApi = retrofit.create(ChannelsApi::class.java)
}
