package com.task.usecases.login

private const val MIN_NAME_STRING_LENGTH = 3
private const val MAX_STRING_LENGTH = 16
private const val USERNAME_REGEX = "[A-Z0-9a-z._]{$MIN_NAME_STRING_LENGTH,$MAX_STRING_LENGTH}"
private const val MIN_CHAR_PASSWORD = 5

class ValidateUsernameUseCase {
    fun invoke(username: String?): Boolean {
        return username != null &&
                username.matches(USERNAME_REGEX.toRegex()) &&
                username.length >= MIN_NAME_STRING_LENGTH &&
                username.length <= MAX_STRING_LENGTH
    }
}

class ValidatePasswordUseCase {
    fun invoke(password: String?): Boolean {
        return password != null && password.length >= MIN_CHAR_PASSWORD && password.length <= MAX_STRING_LENGTH
    }
}