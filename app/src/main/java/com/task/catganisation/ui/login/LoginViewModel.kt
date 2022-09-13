package com.task.catganisation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.catganisation.ui.common.BaseViewModel
import com.task.catganisation.ui.common.FeedbackUser
import com.task.usecases.login.LoginUseCase
import com.task.usecases.login.ValidatePasswordUseCase
import com.task.usecases.login.ValidateUsernameUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseViewModel() {

    private val _loggedIn = MutableLiveData<Unit>()
    val loggedIn: LiveData<Unit> = _loggedIn
    private val _usernameFieldValidation = MutableLiveData<Boolean>()
    val usernameFieldValidation: LiveData<Boolean> = _usernameFieldValidation
    private val _passwordFieldValidation = MutableLiveData<Boolean>()
    val passwordFieldValidation: LiveData<Boolean> = _passwordFieldValidation

    fun login(username: String?, password: String?) {
        viewModelScope.launch {
            showLoading()
            delay(1500)
            val usernameStatus = validateUsernameUseCase.invoke(username)
            val passwordStatus = validatePasswordUseCase.invoke(password)

            _usernameFieldValidation.postValue(usernameStatus)
            _passwordFieldValidation.postValue(passwordStatus)

            if (usernameStatus && passwordStatus) {
                loginUseCase.invoke(username!!, password!!).fold({
                    sendFeedbackUser(FeedbackUser.from(it))
                }, {
                    _loggedIn.postValue(Unit)
                })
            }
            hideLoading()
        }
    }
}