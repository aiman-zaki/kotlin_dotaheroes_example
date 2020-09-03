package com.github.aimanzaki.dotaheroes.ui.heroes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.github.aimanzaki.dotaheroes.data.repository.HeroRepository

class HeroesViewModel @ViewModelInject constructor(
  private val repository: HeroRepository
): ViewModel() {
  val heroes = repository.getHeroes()
}
