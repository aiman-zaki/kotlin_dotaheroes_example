package com.github.aimanzaki.dotaheroes.data.remote

import com.github.aimanzaki.dotaheroes.data.entities.Hero
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HeroService {
  @GET("heroes")
  suspend fun getHeroes(): Response<List<Hero>>

  @GET("heroes/{id}")
  suspend fun getHero(@Path("id") id:Int): Response<Hero>

  //@GET("heroes/{id}/matchups")
  //suspend fun getHeroMatchups(@Path("id") id:Int):Response

}
