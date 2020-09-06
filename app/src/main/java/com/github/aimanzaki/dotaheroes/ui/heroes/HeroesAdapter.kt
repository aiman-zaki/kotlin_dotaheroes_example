package com.github.aimanzaki.dotaheroes.ui.heroes

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.github.aimanzaki.dotaheroes.R
import com.github.aimanzaki.dotaheroes.data.entities.Hero
import com.github.aimanzaki.dotaheroes.databinding.ItemHeroBinding
import java.util.*
import kotlin.collections.ArrayList


class HeroesAdapter(private val listener: HeroItemListener) : RecyclerView.Adapter<HeroViewHolder>(){
  interface HeroItemListener {
    fun onClickedHero(heroId:Int)
  }

  private val items = ArrayList<Hero>()
  fun setItems(items:ArrayList<Hero>){
    this.items.clear()
    this.items.addAll(items)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
    val binding: ItemHeroBinding = ItemHeroBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return HeroViewHolder(binding,listener)
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: HeroViewHolder, position: Int) = holder.bind(items[position])


}

class HeroViewHolder(private val itemBinding: ItemHeroBinding, private val listener: HeroesAdapter.HeroItemListener):RecyclerView.ViewHolder(itemBinding.root),
  View.OnClickListener{
  private lateinit var hero:Hero

  init {
      itemBinding.root.setOnClickListener(this)
  }

  @SuppressLint("SetTextI18n")
  fun bind(item:Hero){
    this.hero = item
    itemBinding.heroName.text = item.localized_name
    itemBinding.heroPrimaryAttribute.text = item.primary_attr
    when (item.primary_attr) {
        "agi" -> {
          itemBinding.heroPrimaryAttribute.setTextColor(Color.GREEN)
        }
        "str" -> {
          itemBinding.heroPrimaryAttribute.setTextColor(Color.RED)
        }
        else -> {
          itemBinding.heroPrimaryAttribute.setTextColor(Color.BLUE)

        }
    }

    val imageName = item.localized_name.replace(" ","_").replace("-","").toLowerCase(Locale.ENGLISH).replace("\\s".toRegex(), "")

    Glide.with(itemBinding.root).
    load("https://cdn.cloudflare.steamstatic.com/apps/dota2/images/heroes/"+imageName+"_full.png").
      transform(CircleCrop())
      .placeholder(R.drawable.ic_launcher_background)
      .error(R.drawable.ic_launcher_foreground)
      .into(itemBinding.image)

      itemBinding.matchupsButton.setOnClickListener(View.OnClickListener {
        listener.onClickedHero(heroId = hero.id)
      })
      if(hero.expanded){
        setVisibility(View.VISIBLE)
      }

  }

  private fun setVisibility(v:Int){
    itemBinding.expandable.visibility = v
  }

  override fun onClick(v:View?){
    if (hero.expanded) setVisibility(View.GONE) else setVisibility(View.VISIBLE)
    hero.expanded = !hero.expanded

  }
}
