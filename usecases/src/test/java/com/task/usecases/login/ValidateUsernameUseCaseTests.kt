package com.task.usecases.login

import com.task.testshared.*
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class ValidateUsernameUseCaseTests {

    companion object {
        private lateinit var validateUsernameUseCase: ValidateUsernameUseCase

        @BeforeClass
        @JvmStatic
        fun setup() {
            validateUsernameUseCase = ValidateUsernameUseCase()
        }
    }

    @Test
    fun validateValidUsername() {
        Assert.assertTrue(validateUsernameUseCase.invoke(mockValidUsername))
    }

    @Test
    fun validateValidShortUsername() {
        Assert.assertTrue(validateUsernameUseCase.invoke(mockValidShortUsername))
    }

    @Test
    fun validateInValidUsername() {
        Assert.assertFalse(validateUsernameUseCase.invoke(mockInvalidUsername))
    }

    @Test
    fun validateInValidShortUsername() {
        Assert.assertFalse(validateUsernameUseCase.invoke(mockInvalidShortUsername))
    }

    @Test
    fun validateInValidBlankUsername() {
        Assert.assertFalse(validateUsernameUseCase.invoke(mockInvalidBlankUsername))
    }

    @Test
    fun validateInValidEmptyUsername() {
        Assert.assertFalse(validateUsernameUseCase.invoke(mockInvalidEmptyUsername))
    }

    @Test
    fun validateInValidNullUsername() {
        Assert.assertFalse(validateUsernameUseCase.invoke(mockInvalidNullUsername))
    }
}