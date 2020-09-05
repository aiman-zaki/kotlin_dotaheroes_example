package com.github.aimanzaki.dotaheroes.ui.hero_matchups

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aimanzaki.dotaheroes.data.entities.Hero
import com.github.aimanzaki.dotaheroes.databinding.HeroesMatchupsFragmentBinding
import com.github.aimanzaki.dotaheroes.ui.heroes.HeroesAdapter
import com.github.aimanzaki.dotaheroes.utils.Resource
import com.github.aimanzaki.dotaheroes.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroMatchupsFragment : Fragment(),HeroMatchupsAdapter.HeroMatchupsListener {
  private var binding: HeroesMatchupsFragmentBinding by autoCleared()
  private val viewModel: HeroMatchupsViewModel by viewModels()
  private lateinit var adapter: HeroMatchupsAdapter

  override fun onCreateView(
    inflater: LayoutInflater,container:ViewGroup?,savedInstanceState:Bundle?
  ): View? {
    binding = HeroesMatchupsFragmentBinding.inflate(inflater,container,false)
    return  binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    arguments?.getInt("id")?.let { viewModel.start(it) }
    setupRecyclerView()
    setupObservers()
  }


  private fun setupRecyclerView(){
    adapter = HeroMatchupsAdapter(this)
    binding.matchupsRv.layoutManager = LinearLayoutManager(requireContext())
    binding.matchupsRv.adapter = adapter
  }

  private fun bindRivalDetail(id:Int,index:Int){
    val data = viewModel.rivalDetail(id)
    var hero = Hero(1,"","","","", listOf<String>())
    data.observe(viewLifecycleOwner,{
      when(it.status){
        Resource.Status.SUCCESS -> {
          binding.progressBar.visibility = View.GONE
          hero = it.data!!
        }
        Resource.Status.LOADING -> {
          binding.progressBar.visibility = View.VISIBLE
        }
        Resource.Status.ERROR -> {
          hero = Hero(1,"","","","", listOf())
        }
      }
      adapter.updateItem(index,hero)
    })
  }

  private fun setupObservers(){
    viewModel.heroMatchups.observe(viewLifecycleOwner, Observer {
      when(it.status){
        Resource.Status.SUCCESS -> {
          binding.progressBar.visibility = View.GONE
          if(!it.data.isNullOrEmpty()) {
            adapter.setItems(ArrayList(it.data))
            it.data.withIndex().forEach { x -> bindRivalDetail(x.value.hero_id,x.index) }
          }
        }
        Resource.Status.ERROR ->{
          Log.d("DATA",it.message)
          Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }
        Resource.Status.LOADING -> {
          binding.progressBar.visibility = View.VISIBLE
        }
      }
    })
  }

  override fun onClickedHero(heroId: Int) {
    TODO("Not yet implemented")
  }
}
