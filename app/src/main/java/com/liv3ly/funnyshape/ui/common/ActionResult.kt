package com.liv3ly.funnyshape.ui.common

import com.nimble.survey.module.common.ActionStatus

data class ActionResult<out T>(val status: ActionStatus, val data: T?, val error: Int?) {
    companion object {
        fun <T> success(data: T?): ActionResult<T> {
            return ActionResult(ActionStatus.SUCCESS, data, null)
        }

        fun <T> error(error: Int): ActionResult<T> {
            return ActionResult(ActionStatus.ERROR, null, error)
        }

        fun <T> loading(): ActionResult<T> {
            return ActionResult(ActionStatus.LOADING, null, null)
        }
    }
}