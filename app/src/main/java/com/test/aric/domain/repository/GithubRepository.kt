package com.test.aric.domain.repository

import com.test.aric.data.remote.dto.RepoInfo
import com.test.aric.data.remote.dto.UserInfo
import com.test.aric.data.remote.dto.UserSearchResult
import retrofit2.Response

interface GithubRepository {
    suspend fun searchUserByName( q:String, page:String): UserSearchResult

    suspend fun getAllFollowers(user:String): List<UserInfo>

    suspend fun getAllRepos(user:String):  Response< List<RepoInfo>>
}