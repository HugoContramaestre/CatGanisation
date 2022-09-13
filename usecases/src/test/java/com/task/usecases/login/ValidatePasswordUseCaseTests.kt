package com.task.usecases.login

import com.task.testshared.*
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class ValidatePasswordUseCaseTests {

    companion object {
        private lateinit var validatePasswordUseCase: ValidatePasswordUseCase

        @BeforeClass
        @JvmStatic
        fun setup() {
            validatePasswordUseCase = ValidatePasswordUseCase()
        }
    }

    @Test
    fun validateValidUsername() {
        Assert.assertTrue(validatePasswordUseCase.invoke(mockValidPassword))
    }

    @Test
    fun validateInValidShortUsername() {
        Assert.assertFalse(validatePasswordUseCase.invoke(mockInvalidShortPassword))
    }

    @Test
    fun validateInValidBlankUsername() {
        Assert.assertFalse(validatePasswordUseCase.invoke(mockInvalidBlankPassword))
    }

    @Test
    fun validateInValidEmptyUsername() {
        Assert.assertFalse(validatePasswordUseCase.invoke(mockInvalidEmptyPassword))
    }

    @Test
    fun validateInValidNullUsername() {
        Assert.assertFalse(validatePasswordUseCase.invoke(mockInvalidNullPassword))
    }
}