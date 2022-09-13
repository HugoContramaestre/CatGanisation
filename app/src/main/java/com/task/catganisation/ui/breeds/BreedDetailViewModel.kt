package com.task.catganisation.ui.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.catganisation.ui.common.BaseViewModel
import com.task.domain.CatBreed as CatBreedDomain
import com.task.catganisation.parcel.CatBreed
import com.task.usecases.breeds.GetCatBreedsByCountryUseCase
import kotlinx.coroutines.launch

class BreedDetailViewModel(
    catBreed: CatBreed
): BaseViewModel() {
    private val _detail = MutableLiveData<CatBreedDomain>()
    val detail: LiveData<CatBreedDomain> = _detail

    init {
        _detail.postValue(catBreed.toDomain())
    }
}