package id.stevani.amphibians.data

import id.stevani.amphibians.model.Amphibian
import id.stevani.amphibians.network.AmphibiansApiService

// Interface yang mendefinisikan kontrak untuk repository Amphibians.
// Repository bertugas menyediakan data, baik dari jaringan, database, atau sumber lainnya.
interface AmphibiansRepository {
    
    // Suspend berarti fungsi ini dirancang untuk dipanggil dalam coroutine.
    suspend fun getAmphibians(): List<Amphibian>
}

// Menggunakan AmphibiansApiService untuk mendapatkan data dari jaringan.
class DefaultAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService // Dependency injection untuk layanan API.
) : AmphibiansRepository {
    
    // Fungsi ini memanggil API secara langsung melalui AmphibiansApiService.
    override suspend fun getAmphibians(): List<Amphibian> = amphibiansApiService.getAmphibians()
}

