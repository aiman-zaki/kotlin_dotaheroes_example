package com.github.aimanzaki.dotaheroes.data.local

import android.content.Context
import androidx.room.*
import com.github.aimanzaki.dotaheroes.data.entities.Hero

@Database(entities = [Hero::class], version = 2 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract  fun heroDao(): HeroDao

  companion object {
    @Volatile private  var instance: AppDatabase? = null

    fun getDatabase(context:Context): AppDatabase = instance ?: synchronized(this){
      instance ?: buildDatabase(context).also { instance = it } }

    private fun buildDatabase(appContext: Context) =
      Room.databaseBuilder(appContext,AppDatabase::class.java, "heroes")
        .fallbackToDestructiveMigration()
        .build()
  }
}

