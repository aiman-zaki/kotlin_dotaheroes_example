package com.github.aimanzaki.dotaheroes.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
  companion object {
    private inline fun <reified T> fromJson(json: String): T {
      return Gson().fromJson(json, object: TypeToken<T>(){}.type)
    }

  }

  @TypeConverter
  fun fromList(value: List<String>?) :String?{
    val gson = Gson()
    return gson.toJson(value)
  }

  @TypeConverter
  fun fromString(value:String):List<String>?{
    return fromJson(value)
  }
}

