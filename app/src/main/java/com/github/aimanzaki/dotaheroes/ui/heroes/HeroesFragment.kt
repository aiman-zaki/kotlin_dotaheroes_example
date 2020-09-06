package com.github.aimanzaki.dotaheroes.ui.heroes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.github.aimanzaki.dotaheroes.R
import com.github.aimanzaki.dotaheroes.databinding.HeroesFragmentBinding
import com.github.aimanzaki.dotaheroes.utils.Resource
import com.github.aimanzaki.dotaheroes.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.zip.Inflater


@AndroidEntryPoint
class HeroesFragment : Fragment(),HeroesAdapter.HeroItemListener{
  private var binding: HeroesFragmentBinding by autoCleared()
  private val viewModel: HeroesViewModel by viewModels()
  private lateinit var adapter: HeroesAdapter

  override fun onCreateView(
    inflater: LayoutInflater,container:ViewGroup?, savedInstanceState:Bundle?
  ): View?{

    binding = HeroesFragmentBinding.inflate(inflater,container,false)
    return  binding.root

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecyclerView()
    setupObservers()
  }

  private fun setupRecyclerView(){
    adapter = HeroesAdapter(this)
    binding.heroesRv.layoutManager = LinearLayoutManager(requireContext())
    binding.heroesRv.adapter = adapter
    (binding.heroesRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
  }

  private  fun setupObservers(){
    viewModel.heroes.observe(viewLifecycleOwner, Observer {
      when(it.status){
        Resource.Status.SUCCESS -> {
          binding.progressBar.visibility = View.GONE
          if(!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
        }
        Resource.Status.ERROR -> {
          Timber.e(it.message)
          Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
        }
        Resource.Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
      }
    })
  }

  override fun onClickedHero(heroId: Int) {
    findNavController().navigate(
      R.id.action_heroesFragment_to_heroMatchupFragment,
      bundleOf("id" to heroId)
    )

  }
}
