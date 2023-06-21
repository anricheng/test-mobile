package com.test.aric.domain.use_case.get_repos

import com.test.aric.common.Resource
import com.test.aric.data.remote.dto.RepoInfo
import com.test.aric.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetReposUseCase @Inject constructor(
    private val repository: GithubRepository
) : BaseUseCase() {
    operator fun invoke(user: String): Flow<Resource<List<RepoInfo>>> = perform {
        repository.getAllRepos(user)
    }
}
open class BaseUseCase {
    fun <T> perform(isShowLoading: Boolean = true, block: suspend () -> T): Flow<Resource<T>> =
        flow {
            try {
                if (isShowLoading) {
                    emit(Resource.Loading<T>())
                }
                emit(Resource.Success<T>(block()))
            } catch (e: HttpException) {
                emit(Resource.Error<T>(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: IOException) {
                emit(Resource.Error<T>("Couldn't reach server. Check your internet connection."))
            }
        }
}