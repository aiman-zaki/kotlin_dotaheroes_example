package com.github.aimanzaki.dotaheroes.ui.hero_matchups

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.github.aimanzaki.dotaheroes.data.entities.Hero
import com.github.aimanzaki.dotaheroes.data.entities.HeroMatchup
import com.github.aimanzaki.dotaheroes.data.repository.HeroRepository
import com.github.aimanzaki.dotaheroes.utils.Resource


class HeroMatchupsViewModel @ViewModelInject constructor(
  private val repository: HeroRepository
) : ViewModel(){
  private  val _id = MutableLiveData<Int>()
  private  val _rivalID = MutableLiveData<Int>()

  private val _heroMatchups = _id.switchMap { id -> repository.getHeroMatchups(id) }


  val heroMatchups: LiveData<Resource<List<HeroMatchup>>> = _heroMatchups

  fun rivalDetail(id:Int):LiveData<Resource<Hero>> = repository.getHero(id)

  fun start(id:Int){
    _id.value = id
  }
}
