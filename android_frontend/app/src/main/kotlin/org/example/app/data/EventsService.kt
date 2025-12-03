package org.example.app.data

import retrofit2.Call
import retrofit2.http.*

/**
 * PUBLIC_INTERFACE
 * Retrofit interface for events endpoints.
 */
interface EventsService {

    @GET("api/events")
    fun getEvents(@Query("from") from: String?, @Query("to") to: String?): Call<List<Event>>

    @GET("api/events/{id}")
    fun getEvent(@Path("id") id: String): Call<Event>

    @POST("api/events")
    fun createEvent(@Body event: Event): Call<Event>

    @PUT("api/events/{id}")
    fun updateEvent(@Path("id") id: String, @Body event: Event): Call<Event>

    @DELETE("api/events/{id}")
    fun deleteEvent(@Path("id") id: String): Call<Void>
}
