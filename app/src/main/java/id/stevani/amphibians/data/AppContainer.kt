package id.stevani.amphibians.data

import id.stevani.amphibians.network.AmphibiansApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Interface untuk Dependency Injection (DI) container.
// Interface ini menyediakan properti untuk mendeklarasikan dependensi yang diperlukan di aplikasi.
interface AppContainer {
    val amphibiansRepository: AmphibiansRepository // Repository untuk data Amphibians.
}

// Implementasi default dari AppContainer.
// Class ini bertugas mengatur bagaimana dependensi diinisialisasi dan dikelola.
class DefaultAppContainer : AppContainer {
    // URL dasar untuk API server.
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())) // Konverter untuk JSON.
        .baseUrl(BASE_URL) // URL API.
        .build()

    //Layanan ini dibuat secara lazy, sehingga hanya diinisialisasi saat pertama kali digunakan
    private val retrofitService: AmphibiansApiService by lazy {
        retrofit.create(AmphibiansApiService::class.java)
    }

    override val amphibiansRepository: AmphibiansRepository by lazy {
        DefaultAmphibiansRepository(retrofitService)
    }
}
