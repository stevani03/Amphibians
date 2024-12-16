package id.stevani.amphibians.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import id.stevani.amphibians.R
import id.stevani.amphibians.ui.screens.AmphibiansViewModel
import id.stevani.amphibians.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibiansApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Mengatur Scaffold agar mengambil seluruh ukuran layar.
        topBar = {
            // TopAppBar menampilkan judul aplikasi di bagian atas layar.
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name), // Menampilkan nama aplikasi dari string resource.
                        style = MaterialTheme.typography.headlineMedium // Menentukan gaya teks untuk judul.
                    )
                }
            )
        }
    ) {
        // Surface adalah pembungkus untuk area konten yang memberikan latar belakang sesuai dengan tema.
        Surface(
            modifier = Modifier.fillMaxSize(), // Mengatur Surface agar mengisi seluruh layar.
            color = MaterialTheme.colorScheme.background // Menggunakan warna latar belakang yang sesuai dengan tema aplikasi.
        ) {
            // Membuat instance ViewModel untuk mengelola state UI dari data amphibians.
            val amphibiansViewModel: AmphibiansViewModel =
                viewModel(factory = AmphibiansViewModel.Factory) // Menggunakan ViewModel dengan Factory.

            // Menyediakan HomeScreen yang menerima state UI dari ViewModel dan fungsi untuk mencoba lagi.
            HomeScreen(
                amphibiansUiState = amphibiansViewModel.amphibiansUiState, // State UI yang diobservasi oleh HomeScreen.
                retryAction = amphibiansViewModel::getAmphibians, // Fungsi untuk mendapatkan data amphibians jika terjadi kesalahan.
                modifier = Modifier.fillMaxSize(), // Mengatur agar HomeScreen mengisi seluruh layar.
                contentPadding = it // Padding yang berasal dari Scaffold untuk konten.
            )
        }
    }
}
