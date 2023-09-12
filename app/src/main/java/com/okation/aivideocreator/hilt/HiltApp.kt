package com.okation.aivideocreator.hilt

import android.content.Context
import androidx.room.Room
import com.okation.aivideocreator.network.OpenAiApi
import com.okation.aivideocreator.network.UberDuckApi
import com.okation.aivideocreator.ui.home.local.Database
import com.okation.aivideocreator.ui.home.local.HomeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltApp {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS) // Bağlantı kurma süresi
            .readTimeout(60, TimeUnit.SECONDS) // Veri okuma süresi
            .writeTimeout(60, TimeUnit.SECONDS) // Veri gönderme süresi
            .build()
    }

    @Provides
    @Singleton
    @Named("openAiApi")
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("uberDuck")
    fun provideBeats(okHttpClient1: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.uberduck.ai/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient1)
            .build()
    }


    @Provides
    @Singleton
    fun provideOpenAiApi(@Named("openAiApi") retrofit: Retrofit) : OpenAiApi{
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideUberDuckApi(@Named("uberDuck")retrofit1: Retrofit) : UberDuckApi{
        return retrofit1.create()
    }


    @Singleton
    @Provides
    fun provideArticleDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(appContext, Database::class.java, "song_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Singleton
    @Provides
    fun provideArticleDao(articleDatabase: Database) : HomeDao {
        return articleDatabase.songDao()
    }
}