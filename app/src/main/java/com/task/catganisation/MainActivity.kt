package com.task.catganisation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.task.catganisation.databinding.ActivityMainBinding
import com.task.catganisation.ui.common.BaseActivity
import com.task.catganisation.ui.common.EventObserver
import com.task.catganisation.ui.common.SharedViewModel
import com.task.catganisation.ui.common.extensions.showSnackbarSimple
import com.task.catganisation.ui.common.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModel()
    private lateinit var currentNavController: NavController

    companion object {
        private val TOOLBAR_BACK_BUTTON_EXCLUDED_FRAGMENTS = listOf(
            R.id.breedsListFragment
        )

        private val TOOLBAR_EXCLUDED_FRAGMENTS = listOf(
            R.id.loginFragment
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObservers()
    }

    private fun initViews() {
        currentNavController =
            (supportFragmentManager
                .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment)
                .navController
        currentNavController.addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController.navigateUp()
    }

    private fun initObservers() {
        sharedViewModel.loading.observe(this, EventObserver {
            binding.includeLoader.mainLayout.visible(it)
        })
        sharedViewModel.feedbackUser.observe(this, EventObserver {
            binding.root.showSnackbarSimple(it.messageResId, it.error)
        })
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        title = destination.label
        supportActionBar?.let {
            if (TOOLBAR_EXCLUDED_FRAGMENTS.contains(destination.id)) {
                it.hide()
            } else {
                it.show()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(
            !TOOLBAR_BACK_BUTTON_EXCLUDED_FRAGMENTS.contains(destination.id)
        )
    }
}