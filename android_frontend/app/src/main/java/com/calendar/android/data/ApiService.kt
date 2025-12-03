package com.calendar.android.data

import com.calendar.android.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface EventsApi {
    // PUBLIC_INTERFACE
    @GET("/api/events")
    suspend fun listInRange(
        @Query("start") startIso: String,
        @Query("end") endIso: String
    ): Response<List<EventResponse>>

    // PUBLIC_INTERFACE
    @POST("/api/events")
    suspend fun create(@Body req: EventRequest): Response<EventResponse>

    // PUBLIC_INTERFACE
    @GET("/api/events/{id}")
    suspend fun get(@Path("id") id: String): Response<EventResponse>

    // PUBLIC_INTERFACE
    @PUT("/api/events/{id}")
    suspend fun update(@Path("id") id: String, @Body req: EventRequest): Response<EventResponse>

    // PUBLIC_INTERFACE
    @DELETE("/api/events/{id}")
    suspend fun delete(@Path("id") id: String): Response<Void>
}

object ApiClient {
    val api: EventsApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EventsApi::class.java)
    }
}
