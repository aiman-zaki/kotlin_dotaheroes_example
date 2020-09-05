package com.github.aimanzaki.dotaheroes.data.remote

import javax.inject.Inject


class HeroRemoteDataSource @Inject constructor(
  private val heroService:HeroService
):BaseDataSource(){
  suspend fun getHeroes() = getResult { heroService.getHeroes() }
  suspend fun getHero(id:Int) = getResult { heroService.getHero(id) }
  suspend fun getHeroMatchups(id:Int) = getResult {  heroService.getHeroMatchups(id)}
}
