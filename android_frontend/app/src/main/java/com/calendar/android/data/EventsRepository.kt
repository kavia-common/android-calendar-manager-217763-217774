package com.calendar.android.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventsRepository(private val api: EventsApi = ApiClient.api) {

    // PUBLIC_INTERFACE
    suspend fun listInRange(startIso: String, endIso: String): Result<List<EventResponse>> = withContext(Dispatchers.IO) {
        val resp = api.listInRange(startIso, endIso)
        if (resp.isSuccessful) Result.success(resp.body() ?: emptyList())
        else Result.failure(RuntimeException("Error ${resp.code()}"))
    }

    // PUBLIC_INTERFACE
    suspend fun create(req: EventRequest): Result<EventResponse> = withContext(Dispatchers.IO) {
        val resp = api.create(req)
        if (resp.isSuccessful && resp.body() != null) Result.success(resp.body()!!)
        else Result.failure(RuntimeException("Error ${resp.code()}"))
    }

    // PUBLIC_INTERFACE
    suspend fun update(id: String, req: EventRequest): Result<EventResponse> = withContext(Dispatchers.IO) {
        val resp = api.update(id, req)
        if (resp.isSuccessful && resp.body() != null) Result.success(resp.body()!!)
        else Result.failure(RuntimeException("Error ${resp.code()}"))
    }

    // PUBLIC_INTERFACE
    suspend fun delete(id: String): Result<Unit> = withContext(Dispatchers.IO) {
        val resp = api.delete(id)
        if (resp.isSuccessful) Result.success(Unit)
        else Result.failure(RuntimeException("Error ${resp.code()}"))
    }
}
