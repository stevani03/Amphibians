package id.stevani.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.stevani.amphibians.R
import id.stevani.amphibians.model.Amphibian
import id.stevani.amphibians.ui.theme.AmphibiansTheme

@Composable
fun HomeScreen(
    amphibiansUiState: AmphibiansUiState, // Status UI yang diterima, bisa Loading, Success, atau Error.
    retryAction: () -> Unit, // Fungsi yang dipanggil saat pengguna ingin mencoba ulang (retry) untuk memuat data.
    modifier: Modifier = Modifier, // Modifier untuk menyesuaikan tampilan (default: Modifier).
    contentPadding: PaddingValues = PaddingValues(0.dp) // Padding untuk konten (default: tanpa padding).
) {
    // Menentukan tampilan berdasarkan status UI.
    when (amphibiansUiState) {
        is AmphibiansUiState.Loading -> 
            LoadingScreen(modifier.size(200.dp)) // Menampilkan tampilan loading dengan ukuran 200dp.
        
        is AmphibiansUiState.Success ->
            AmphibiansListScreen(
                amphibians = amphibiansUiState.amphibians, // Menampilkan daftar amfibi yang berhasil dimuat.
                modifier = modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                contentPadding = contentPadding // Padding tambahan untuk konten.
            )
        
        else -> 
            ErrorScreen(retryAction, modifier) // Menampilkan layar error jika gagal memuat data.
    }
}

// Tampilan loading yang menunjukkan gambar animasi atau gambar loading.
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img), // Menampilkan gambar loading dari resource.
        contentDescription = stringResource(R.string.loading), // Deskripsi gambar untuk aksesibilitas.
        modifier = modifier // Menyesuaikan modifier yang diterima.
    )
}

// Tampilan error yang meminta pengguna untuk mencoba ulang.
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier, // Modifier yang diterima untuk menyesuaikan tampilan.
        verticalArrangement = Arrangement.Center, // Menyusun elemen secara vertikal di tengah.
        horizontalAlignment = Alignment.CenterHorizontally // Menyusun elemen secara horizontal di tengah.
    ) {
        // Menampilkan pesan kesalahan.
        Text(stringResource(R.string.loading_failed))
        
        // Tombol untuk mencoba ulang jika terjadi kesalahan.
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry)) // Label tombol retry.
        }
    }
}

@Composable
fun AmphibianCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.amphibian_title, amphibian.name, amphibian.type),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            AsyncImage(
            modifier = Modifier.fillMaxWidth(), // Mengatur gambar untuk mengisi lebar layar
            model = ImageRequest.Builder(context = LocalContext.current) // Membuat permintaan gambar menggunakan ImageRequest
            .data(amphibian.imgSrc) // URL atau sumber gambar yang akan dimuat
            .crossfade(true) // Mengaktifkan efek transisi saat gambar dimuat
            .build(), // Membangun permintaan gambar
            contentDescription = null, // Deskripsi konten untuk aksesibilitas (null karena gambar ini tidak memerlukan deskripsi)
            contentScale = ContentScale.FillWidth, // Mengatur agar gambar mengisi lebar layar dan proporsional secara horizontal
            error = painterResource(id = R.drawable.ic_broken_image), // Gambar yang ditampilkan jika terjadi kesalahan (gambar rusak)
            placeholder = painterResource(id = R.drawable.loading_img) // Gambar yang ditampilkan saat gambar sedang dimuat (gambar loading)
            )
            
            Text(
                text = amphibian.description, // Teks deskripsi amfibi yang ditampilkan
                style = MaterialTheme.typography.titleMedium, // Gaya teks yang digunakan untuk menampilkan deskripsi
                textAlign = TextAlign.Justify, // Menyusun teks agar rata kiri-kanan
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)) // Padding untuk memberi jarak antara teks dan batas layar
            )

        }
    }
}

@Composable
private fun AmphibiansListScreen(
    amphibians: List<Amphibian>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = amphibians,
            key = { amphibian ->
                amphibian.name
            }
        ) { amphibian ->
            AmphibianCard(amphibian = amphibian, modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AmphibiansTheme {
        LoadingScreen(
            Modifier
                .fillMaxSize()
                .size(200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AmphibiansTheme {
        ErrorScreen({}, Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibiansListScreenPreview() {
    AmphibiansTheme {
        val mockData = List(10) {
            Amphibian(
                "Lorem Ipsum - $it",
                "$it",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                        " eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad" +
                        " minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip" +
                        " ex ea commodo consequat.",
                imgSrc = ""
            )
        }
        AmphibiansListScreen(mockData, Modifier.fillMaxSize())
    }
}
