package org.example.app.data

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * PUBLIC_INTERFACE
 * Retrofit API client providing EventsService instance.
 */
class ApiClient private constructor(context: Context) {

    val eventsService: EventsService

    init {
        val baseUrl = context.getString(org.example.app.R.string.backend_base_url)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        eventsService = retrofit.create(EventsService::class.java)
    }

    companion object {
        @Volatile private var _instance: ApiClient? = null

        // PUBLIC_INTERFACE
        fun init(context: Context) {
            /** Initialize the API client singleton. No-op if already initialized. */
            if (_instance == null) {
                synchronized(this) {
                    if (_instance == null) {
                        _instance = ApiClient(context.applicationContext)
                    }
                }
            }
        }

        // PUBLIC_INTERFACE
        val instance: ApiClient
            get() = _instance ?: throw IllegalStateException("ApiClient not initialized. Call ApiClient.init(context) in Application or first Activity.")
    }
}
