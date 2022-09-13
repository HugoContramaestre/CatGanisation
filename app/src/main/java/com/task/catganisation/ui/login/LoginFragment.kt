package com.task.catganisation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputLayout
import com.task.catganisation.R
import com.task.catganisation.databinding.FragmentLoginBinding
import com.task.catganisation.ui.common.BaseFragment
import com.task.catganisation.ui.common.EventObserver
import com.task.catganisation.ui.common.FeedbackUser
import com.task.catganisation.ui.common.extensions.hideError
import com.task.catganisation.ui.common.extensions.showError
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
    }

    private fun initListeners() {
        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.usernameInputText.text.toString(),
                binding.passwordInputText.text.toString()
            )
        }
    }

    private fun initObservers() {
        viewModel.loggedIn.observe(viewLifecycleOwner) {
            navigate(LoginFragmentDirections.actionLoginFragmentToBreedsListFragment())
        }

        viewModel.usernameFieldValidation.observe(viewLifecycleOwner) {
            showFieldError(
                binding.usernameInputLayout,
                R.string.error_invalid_username,
                it
            )
        }

        viewModel.passwordFieldValidation.observe(viewLifecycleOwner) {
            showFieldError(
                binding.passwordInputLayout,
                R.string.error_invalid_password,
                it
            )
        }

        viewModel.loading.observe(viewLifecycleOwner, EventObserver{
            onLoading(it)
        })
    }

    private fun showFieldError(view: TextInputLayout, msg: Int, isValid: Boolean) {
        if(isValid){
            view.hideError()
        } else {
            view.showError(getString(msg))
        }
    }
}