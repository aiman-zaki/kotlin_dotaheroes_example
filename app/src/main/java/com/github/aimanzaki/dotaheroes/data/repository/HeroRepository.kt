package com.github.aimanzaki.dotaheroes.data.repository

import com.github.aimanzaki.dotaheroes.data.local.HeroDao
import com.github.aimanzaki.dotaheroes.data.remote.HeroRemoteDataSource
import com.github.aimanzaki.dotaheroes.utils.performGetFromLocal
import com.github.aimanzaki.dotaheroes.utils.performGetOperation
import com.github.aimanzaki.dotaheroes.utils.performGetOperationWithoutLocal
import javax.inject.Inject

class HeroRepository @Inject constructor(
  private val remoteDataSource: HeroRemoteDataSource,
  private val localDataSource: HeroDao
){


  fun getHeroes() = performGetOperation(
    databaseQuery = { localDataSource.getAllHeroes()},
    networkCall = { remoteDataSource.getHeroes()},
    saveCallResult = { localDataSource.insertAll(it)}
  )

  fun getHero(id:Int) = performGetFromLocal { localDataSource.getHero(id) }


  fun getHeroMatchups(id:Int) = performGetOperationWithoutLocal(
    networkCall = { remoteDataSource.getHeroMatchups(id)}
  )
}
