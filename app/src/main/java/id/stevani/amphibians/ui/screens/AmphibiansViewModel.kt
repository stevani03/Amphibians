package id.stevani.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import id.stevani.amphibians.AmphibiansApplication
import id.stevani.amphibians.data.AmphibiansRepository
import id.stevani.amphibians.model.Amphibian
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Interface yang merepresentasikan status UI dari layar Amphibians.
sealed interface AmphibiansUiState {
    // Status jika data berhasil dimuat, menyimpan daftar amfibi.
    data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState

    // Status jika terjadi kesalahan saat memuat data.
    object Error : AmphibiansUiState

    // Status saat data sedang dalam proses pemuatan.
    object Loading : AmphibiansUiState
}

// ViewModel untuk layar Amphibians, bertanggung jawab untuk memuat data dan mengelola status UI.
class AmphibiansViewModel(private val amphibiansRepository: AmphibiansRepository) : ViewModel() {

    // Variabel yang merepresentasikan status UI. Nilainya dapat berubah dan diobservasi oleh Compose.
    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set // Setter-nya bersifat privat agar hanya ViewModel yang bisa mengubahnya.

    // Blok inisialisasi yang secara otomatis memuat data saat ViewModel dibuat.
    init {
        getAmphibians() // Memanggil fungsi untuk memuat data amfibi.
    }

    // Fungsi untuk memuat data amfibi dari repository.
    fun getAmphibians() {
        // Meluncurkan coroutine di viewModelScope agar operasi berjalan di latar belakang.
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading // Mengatur status menjadi Loading.
            amphibiansUiState = try {
                // Jika berhasil, mengatur status menjadi Success dengan data yang dimuat.
                AmphibiansUiState.Success(amphibiansRepository.getAmphibians())
            } catch (e: IOException) {
                // Jika ada kesalahan jaringan atau IO, mengatur status menjadi Error.
                AmphibiansUiState.Error
            } catch (e: HttpException) {
                // Jika ada kesalahan dari server (misalnya 404, 500), mengatur status menjadi Error.
                AmphibiansUiState.Error
            }
        }
    }

    // Objek pendamping (companion object) untuk menyediakan pabrik (factory) ViewModel.
    companion object {
        // Pabrik untuk membuat instance ViewModel dengan dependensi yang disediakan.
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Mengambil aplikasi dari konteks ViewModel.
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AmphibiansApplication)
                // Mendapatkan repository dari container aplikasi.
                val amphibiansRepository = application.container.amphibiansRepository
                // Mengembalikan instance ViewModel dengan repository sebagai dependensi.
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}
