package com.github.aimanzaki.dotaheroes.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "heroes")
data class Hero (
  @PrimaryKey
  val id: Int,
  val name: String,
  val localized_name:String,
  val primary_attr:String,
  val attack_type:String,
  val roles:List<String>
)


data class HeroMatchup (
  @PrimaryKey
  val heroId: Int,
  val rivalId: Int,
  val games_played:Int,
  val wins:Int
)
