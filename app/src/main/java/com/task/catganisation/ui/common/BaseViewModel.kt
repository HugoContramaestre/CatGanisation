package com.task.catganisation.ui.common

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    private val _feedbackUser = MutableLiveData<Event<FeedbackUser>>()
    internal val feedbackUser: LiveData<Event<FeedbackUser>> get() = _feedbackUser
    private val _loading = MutableLiveData<Event<Boolean>>()
    internal val loading: LiveData<Event<Boolean>> get() = _loading

    init {
        _loading.postValueEvent(false)
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }

    fun sendFeedbackUser(feedbackUser: FeedbackUser) = _feedbackUser.postValueEvent(feedbackUser)
    fun toggleLoading(loading: Boolean) = _loading.postValueEvent(loading)
    fun showLoading() =
        _loading.postValueEvent(true)
    fun hideLoading() =
        _loading.postValueEvent(false)

}