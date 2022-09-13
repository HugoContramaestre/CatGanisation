package com.task.catganisation.ui.breeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.task.catganisation.databinding.FragmentBreedsListBinding
import com.task.catganisation.parcel.CatBreed
import com.task.catganisation.ui.breeds.adapter.CatBreedsAdapter
import com.task.catganisation.ui.common.BaseFragment
import com.task.catganisation.ui.common.EventObserver
import com.task.catganisation.ui.common.extensions.visible
import com.task.catganisation.ui.common.states.ListState
import org.koin.androidx.viewmodel.ext.android.viewModel

class BreedsListFragment : BaseFragment() {
    private lateinit var binding: FragmentBreedsListBinding
    private val viewModel: BreedsViewModel by viewModel()
    private val adapter = CatBreedsAdapter(::onItemSelected, ::onAdapterEmpty)
    private var currentQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObservers(viewModel)

        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        initAdapter()
    }

    private fun initListeners() {
        binding.includeRetry.retryButton.setOnClickListener {
            viewModel.getList()
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrBlank()){
                    viewModel.getFilteredList(query)
                    currentQuery = query
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrBlank() && currentQuery != ""){
                    viewModel.getList()
                    currentQuery = ""
                }
                return true
            }
        })
    }

    private fun initObservers() {
        viewModel.listState.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                ListState.Done -> {
                    binding.recyclerView.visible(true)
                    binding.recyclerView.suppressLayout(false)
                    binding.includeEmptyList.mainLayout.visible(false)
                    binding.includeRetry.mainLayout.visible(false)
                }
                ListState.Empty -> {
                    binding.recyclerView.visible(false)
                    binding.recyclerView.suppressLayout(false)
                    binding.includeEmptyList.mainLayout.visible(true)
                    binding.includeRetry.mainLayout.visible(false)
                    adapter.clearItems()
                }
                ListState.Error -> {
                    binding.recyclerView.visible(false)
                    binding.recyclerView.suppressLayout(false)
                    binding.includeEmptyList.mainLayout.visible(false)
                    binding.includeRetry.mainLayout.visible(true)
                    adapter.clearItems()
                }
                ListState.Loading -> {
                    adapter.showPlaceholders()
                    binding.recyclerView.visible(true)
                    binding.recyclerView.suppressLayout(true)
                    binding.includeEmptyList.mainLayout.visible(false)
                    binding.includeRetry.mainLayout.visible(false)
                }
            }
        })

        viewModel.list.observe(viewLifecycleOwner, EventObserver {
            adapter.submitList(it)
        })
    }

    private fun initAdapter() {
        with(binding) {
            recyclerView.adapter = adapter
        }
    }

    private fun onItemSelected(item: CatBreed) {
        navigate(BreedsListFragmentDirections.actionBreedsListFragmentToBreedDetailFragment(item))
    }

    private fun onAdapterEmpty() {
        binding.includeEmptyList.mainLayout.visible(true)
    }
}