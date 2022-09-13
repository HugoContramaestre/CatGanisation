package com.task.catganisation.ui.common

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseFragment(): ScopeFragment() {

    internal val sharedViewModel: SharedViewModel by sharedViewModel()
    private val navController: NavController by lazy { Navigation.findNavController(requireView()) }

    fun navigate(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    fun navigate(resId: Int, args: Bundle? = null) {
        navController.navigate(resId, args)
    }

    fun goBack() {
        if (!navController.popBackStack()) {
            requireActivity().finish()
        }
    }

    fun initViewModelObservers(viewModel: BaseViewModel) {
        viewModel.feedbackUser.observe(viewLifecycleOwner, EventObserver(this::onFeedback))
        viewModel.loading.observe(viewLifecycleOwner, EventObserver(this::onLoading))
    }

    open fun onFeedback(feedbackUser: FeedbackUser) =
        sharedViewModel.handleFeedbackUser(feedbackUser)

    open fun onLoading(loading: Boolean) {
        sharedViewModel.handleLoading(loading)
    }
}