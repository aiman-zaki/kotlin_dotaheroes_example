package com.github.aimanzaki.dotaheroes.data.repository

import com.github.aimanzaki.dotaheroes.data.local.HeroDao
import com.github.aimanzaki.dotaheroes.data.remote.HeroRemoteDataSource
import com.github.aimanzaki.dotaheroes.utils.performGetOperation
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

  fun getHero(id:Int) = performGetOperation(
    databaseQuery = { localDataSource.getHero(id)},
    networkCall = { remoteDataSource.getHero(id)},
    saveCallResult = { localDataSource.insert(it)}
  )
}
