package com.task.catganisation.data.api

import com.task.data.exception.Failure

open class FailureFactory {

    open fun handleCode(code: Int? = -1) =
        /**
        Any specific error based on a error code, such as invalid token,
        would be here inside a when with all the possible code errors defined.
         */
        Failure.api(code)

    open fun handleException(e: Exception? = null) = Failure.UnexpectedFailure

    open fun handleApiCode(code: Int?, errorResult: ErrorResult?) =
        if (errorResult == null) {
            handleCode(code)
        } else {
            // Here the error code would be handled based on the error object.
            handleCode(code)
        }
}