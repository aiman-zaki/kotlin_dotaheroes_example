package com.github.aimanzaki.dotaheroes.di

import android.content.Context
import com.github.aimanzaki.dotaheroes.data.local.AppDatabase
import com.github.aimanzaki.dotaheroes.data.local.HeroDao
import com.github.aimanzaki.dotaheroes.data.remote.HeroRemoteDataSource
import com.github.aimanzaki.dotaheroes.data.remote.HeroService
import com.github.aimanzaki.dotaheroes.data.repository.HeroRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule{

  @Singleton
  @Provides
  fun provideRetrofit(gson:Gson):Retrofit = Retrofit.Builder()
    .baseUrl("https://api.opendota.com/api/")
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

  @Provides
  fun provideGson():Gson = GsonBuilder().create()

  @Provides
  fun provideHeroService(retrofit: Retrofit): HeroService = retrofit.create(HeroService::class.java)

  @Singleton
  @Provides
  fun provideHeroesRemoteDataSource(heroService: HeroService) = HeroRemoteDataSource(heroService)

  @Singleton
  @Provides
  fun provideDatabase(@ApplicationContext appContext:Context) = AppDatabase.getDatabase(appContext)

  @Singleton
  @Provides
  fun provideHeroDao(db:AppDatabase) = db.heroDao()

  @Singleton
  @Provides
  fun provideRepository(remoteDataSource: HeroRemoteDataSource, localDataSource: HeroDao) = HeroRepository(remoteDataSource,localDataSource)
}
