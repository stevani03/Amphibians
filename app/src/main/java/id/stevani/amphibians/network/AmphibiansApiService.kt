package id.stevani.amphibians.network

import id.stevani.amphibians.model.Amphibian
import retrofit2.http.GET


// Interface untuk layanan API Retrofit.
// Mendefinisikan endpoint yang digunakan untuk mengambil data dari server.
interface AmphibiansApiService {

// Mendefinisikan endpoint untuk mengambil daftar Amphibian dari server.
// Menggunakan anotasi @GET untuk menentukan metode HTTP yang digunakan (GET).
@GET("amphibians")
suspend fun getAmphibians(): List<Amphibian>
}
