package com.task.catganisation.ui.common


class SharedViewModel() : BaseViewModel() {

    fun handleFeedbackUser(feedbackUser: FeedbackUser) = sendFeedbackUser(feedbackUser)

    fun handleLoading(loading: Boolean) = toggleLoading(loading)
}