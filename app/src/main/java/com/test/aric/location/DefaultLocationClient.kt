package com.test.aric.location

import android.content.Context
import android.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultLocationClient(private val context:Context):LocationClient {
    override fun getLocationUpdates(interval: Long): Flow<Location> = flow {
        emit(Location("12"))
    }
}