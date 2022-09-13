package com.task.catganisation.ui.breeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.task.catganisation.databinding.FragmentBreedDetailBinding
import com.task.catganisation.ui.common.BaseFragment
import com.task.catganisation.ui.common.bindContentImage
import com.task.catganisation.ui.common.extensions.EXTRA_CAT_BREED
import com.task.catganisation.ui.common.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BreedDetailFragment: BaseFragment() {
    private lateinit var binding: FragmentBreedDetailBinding

    private val viewModel: BreedDetailViewModel by viewModel {
        parametersOf(arguments?.getParcelable(EXTRA_CAT_BREED))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObservers(viewModel)

        initObservers()
    }

    private fun initObservers(){
        viewModel.detail.observe(viewLifecycleOwner) {
            binding.includeRetry.mainLayout.visible(false)
            binding.includeEmptyList.mainLayout.visible(false)
            binding.image.bindContentImage(it.image.url)
            binding.name.text = it.name
            binding.description.text = it.description
            binding.countryCode.text = it.countryCode
            binding.temperament.text = it.temperament
            binding.wikipediaUrl.text = it.wikipediaUrl
        }
    }
}