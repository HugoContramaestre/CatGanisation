package com.task.catganisation.ui.common

import com.task.data.exception.Failure
import com.task.catganisation.R

data class FeedbackUser(
    val messageResId: Int,
    val error: Boolean = false,
) {
    companion object {
        fun from(failure: Failure?): FeedbackUser {
            val msg = when(failure) {
                is Failure.NetworkConnection -> R.string.error_connection
                else -> R.string.error_unknown
            }
            return FeedbackUser(msg, true)
        }
    }
}