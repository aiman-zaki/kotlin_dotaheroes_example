package com.github.aimanzaki.dotaheroes.ui.hero_matchups

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Index
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.github.aimanzaki.dotaheroes.R
import com.github.aimanzaki.dotaheroes.data.entities.Hero
import com.github.aimanzaki.dotaheroes.data.entities.HeroMatchup
import com.github.aimanzaki.dotaheroes.databinding.ItemHeroBinding
import com.github.aimanzaki.dotaheroes.databinding.ItemHeroMatchupBinding
import com.github.aimanzaki.dotaheroes.ui.heroes.HeroViewHolder
import com.github.aimanzaki.dotaheroes.ui.heroes.HeroesAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

class HeroMatchupsAdapter(private val listener: HeroMatchupsListener) : RecyclerView.Adapter<HeroMatchupsViewHolder>() {

  interface HeroMatchupsListener {
    fun onClickedHero(heroId: Int)
  }

  private val items = ArrayList<HeroMatchup>()
  fun setItems(items:ArrayList<HeroMatchup>){
    this.items.clear()
    this.items.addAll(items)
    notifyDataSetChanged()
  }

  fun updateItem(index:Int,rival:Hero){
    this.items[index].hero = rival
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroMatchupsViewHolder {
    val binding: ItemHeroMatchupBinding = ItemHeroMatchupBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return HeroMatchupsViewHolder(binding,listener)
  }
  override fun getItemCount(): Int = items.size
  override fun onBindViewHolder(holder: HeroMatchupsViewHolder, position: Int) = holder.bind(items[position])
}


class HeroMatchupsViewHolder(private val itemBinding: ItemHeroMatchupBinding, private val listener: HeroMatchupsAdapter.HeroMatchupsListener):RecyclerView.ViewHolder(itemBinding.root),
  View.OnClickListener{

  private lateinit var heroMatchups:HeroMatchup

  init {
    itemBinding.root.setOnClickListener(this)
  }

  @SuppressLint("SetTextI18n")
  fun bind(item:HeroMatchup){
    itemBinding.gamesPlayed.text = item.games_played.toString()
    itemBinding.matchesWin.text = item.wins.toString()
    itemBinding.matchLose.text = (item.games_played - item.wins).toString()

    if(item.hero != null){
      val imageName = item.hero.localized_name.replace(" ","_").replace("-","").toLowerCase(Locale.ENGLISH).replace("\\s".toRegex(), "")

      Glide.with(itemBinding.root).
      load("https://cdn.cloudflare.steamstatic.com/apps/dota2/images/heroes/"+imageName+"_full.png").
      transform(CircleCrop())
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_foreground)
        .into(itemBinding.image)
    }
  }

  override fun onClick(v:View?){
  }

}
