package com.github.aimanzaki.dotaheroes.data.local



import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.aimanzaki.dotaheroes.data.entities.Hero


@Dao
interface HeroDao {

  @Query("SELECT * FROM heroes")
  fun getAllHeroes() : LiveData<List<Hero>>

  @Query("SELECT * FROM heroes LIMIT 1")
  fun getOneHero() :LiveData<List<Hero>>

  @Query("SELECT * FROM heroes WHERE id = :id")
  fun getHero(id:Int): LiveData<Hero>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(heroes:List<Hero>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(hero:Hero)

}
