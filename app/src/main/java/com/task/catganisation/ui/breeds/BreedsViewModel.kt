package com.task.catganisation.ui.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.catganisation.ui.common.BaseViewModel
import com.task.catganisation.ui.common.Event
import com.task.catganisation.ui.common.FeedbackUser
import com.task.catganisation.ui.common.postValueEvent
import com.task.catganisation.ui.common.states.ListState
import com.task.domain.CatBreed
import com.task.usecases.breeds.GetCatBreedsByCountryUseCase
import com.task.usecases.breeds.GetCatBreedsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BreedsViewModel(
    private val getCatBreedsUseCase: GetCatBreedsUseCase,
    private val getCatBreedsByCountryUseCase: GetCatBreedsByCountryUseCase
) : BaseViewModel() {

    private val _listState = MutableLiveData<Event<ListState>>()
    val listState: LiveData<Event<ListState>> = _listState
    private val _list = MutableLiveData<Event<List<CatBreed>>>()
    val list: LiveData<Event<List<CatBreed>>>
        get() {
            if(_list.value == null) getList()
            return _list
        }

    fun getList() {
        viewModelScope.launch {
            _listState.postValueEvent(ListState.Loading)
            delay(500)
            getCatBreedsUseCase.invoke()
                .fold({
                    _listState.postValueEvent(ListState.Error)
                    sendFeedbackUser(FeedbackUser.from(it))
                }, { catBreedsList ->
                    if (catBreedsList.isNotEmpty()) {
                        _listState.postValueEvent(ListState.Done)
                        _list.postValueEvent(catBreedsList)
                    } else {
                        _listState.postValueEvent(ListState.Empty)
                    }
                })
        }
    }

    fun getFilteredList(country: String) {
        viewModelScope.launch {
            _listState.postValueEvent(ListState.Loading)
            delay(500)
            getCatBreedsByCountryUseCase.invoke(country)
                .fold({
                    _listState.postValueEvent(ListState.Error)
                    sendFeedbackUser(FeedbackUser.from(it))
                }, { catBreedsList ->
                    if (catBreedsList.isNotEmpty()) {
                        _listState.postValueEvent(ListState.Done)
                        _list.postValueEvent(catBreedsList)
                    } else {
                        _listState.postValueEvent(ListState.Empty)
                    }
                })
        }
    }
}