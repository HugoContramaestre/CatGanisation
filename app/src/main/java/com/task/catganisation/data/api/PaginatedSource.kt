package com.task.catganisation.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.haroldadmin.cnradapter.NetworkResponse
import com.task.data.exception.Failure
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 0

class PaginatedSource<In, Out: Any>(
    private val call: suspend (position: Int, params: LoadParams<Int>) -> NetworkResponse<List<In>, ErrorResult>,
    private val transform: (In) -> Out
): PagingSource<Int, Out>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Out> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return when (val result = call.invoke(position, params)) {
            is NetworkResponse.Success -> {
               try {
                   val items = result.body.map {
                       transform.invoke(it)
                   }
                   LoadResult.Page(
                       data = items,
                       prevKey = null,
                       nextKey = if (items.isEmpty()) null else position +1
                   )
               } catch (e: Exception) {
                   Timber.e(e)
                   LoadResult.Error(e)
               }
            }
            is NetworkResponse.ServerError -> {
                LoadResult.Error(
                    FailureFactory().handleApiCode(
                        result.code,
                        result.body
                    )
                )
            }
            is NetworkResponse.NetworkError -> {
                Timber.e(result.error)
                LoadResult.Error(Failure.NetworkConnection)
            }
            is NetworkResponse.UnknownError -> {
                Timber.e(result.error)
                LoadResult.Error(Failure.UnexpectedFailure)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Out>): Int? {
        return null
    }
}