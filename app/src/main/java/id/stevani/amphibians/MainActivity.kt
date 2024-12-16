package id.stevani.amphibians // Menentukan nama paket untuk file Kotlin ini.

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import id.stevani.amphibians.ui.AmphibiansApp
import id.stevani.amphibians.ui.theme.AmphibiansTheme

// Kelas utama aplikasi, yaitu MainActivity, yang merupakan turunan dari ComponentActivity.
class MainActivity : ComponentActivity() {
    // Metode onCreate yang akan dipanggil ketika aktivitas dibuat.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Fungsi setContent digunakan untuk mengatur antarmuka pengguna aplikasi menggunakan Jetpack Compose.
        setContent {
            // Menggunakan tema aplikasi khusus, yaitu AmphibiansTheme.
            AmphibiansTheme {
                // Surface adalah container dengan warna latar belakang sesuai tema.
                Surface(
                    modifier = Modifier.fillMaxSize(), // Mengatur ukuran Surface agar memenuhi layar.
                    color = MaterialTheme.colorScheme.background // Menggunakan warna latar belakang dari skema tema material.
                ) {
                    AmphibiansApp() // Memanggil fungsi AmphibiansApp untuk menampilkan UI aplikasi.
                }
            }
        }
    }
}
