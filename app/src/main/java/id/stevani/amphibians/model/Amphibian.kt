package id.stevani.amphibians.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


 //Data class untuk merepresentasikan objek Amphibian.
 //Kelas ini digunakan untuk memetakan data JSON yang diterima dari API.
 //Menggunakan anotasi @Serializable untuk integrasi dengan kotlinx.serialization.
@Serializable
data class Amphibian(
    val name: String,               // Nama dari Amphibian.
    val type: String,               // Jenis Amphibian, misalnya "frog", "salamander", dll.
    val description: String,        // Deskripsi tentang Amphibian.
    @SerialName("img_src")          // Menyelaraskan nama properti dengan field JSON "img_src".
    val imgSrc: String              // URL gambar untuk Amphibian.
)
