package com.example.BookApp.ui.screens.BookDetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.BookApp.R
import com.example.BookApp.models.Book
import com.example.BookApp.models.Author
import com.example.BookApp.models.Genre

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    bookId: Int?,
    viewModel: BookDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val bookDetailState by viewModel.bookDetailState.collectAsState()

    LaunchedEffect(bookId) {
        bookId?.let { viewModel.fetchBookDetails(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        when (bookDetailState) {
            is BookDetailState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is BookDetailState.Error -> {
                val errorMessage = (bookDetailState as BookDetailState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            bookId?.let { viewModel.fetchBookDetails(it) }
                        }) {
                            Text("Retry")
                        }
                    }
                }
            }
            is BookDetailState.Success -> {
                val book = (bookDetailState as BookDetailState.Success).book
                Log.d("BookDetailScreen", "Judul: ${book.judul}, Genre: ${book.genres?.size}")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color(0xFFF5F9FF)),
                    horizontalAlignment = Alignment.CenterHorizontally, // Pusatkan konten horizontal
                    contentPadding = PaddingValues(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    item {
                        // Cover Buku
                        AsyncImage(
                            model = book.coverImageUrl,
                            contentDescription = "Cover buku ${book.judul ?: "Tanpa Judul"}",
                            placeholder = painterResource(id = R.drawable.ic_placeholder),
                            error = painterResource(id = R.drawable.ic_placeholder),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(200.dp) // Sesuaikan ukuran cover
                                .height(300.dp) // Sesuaikan ukuran cover
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Judul Buku
                        Text(
                            text = book.judul ?: "Untitled",
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Tombol Baca dan Bookmark
                        Button(
                            onClick = { /* TODO: Implement Baca action */ },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF))
                        ) {
                            Text(text = "Baca", color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton( // Gunakan OutlinedButton untuk Bookmark
                            onClick = { /* TODO: Implement Bookmark action */ },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF007BFF)),
                            border = ButtonDefaults.outlinedButtonBorder
                        ) {
                            Text(text = "Bookmark")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Ikon Bintang dan Jam
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Rating Icon and Text
                            book.rating?.let { rating -> // Hanya tampilkan jika rating tidak null
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "$rating / 5.0", // <-- Tampilkan nilai rating
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Black
                                    )
                                }
                            } ?: run {
                                // Tampilkan teks default jika rating null
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "N/A", // <-- Tampilkan jika rating null
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(24.dp))

                            // Publication Date Icon and Text
                            book.tanggalPublikasi?.let { pubDate -> 
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = "Publication Date",
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = pubDate.substringBefore("T"),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Black
                                    )
                                }
                            } ?: run {
                                // Tampilkan teks default jika tanggal publikasi null
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = "Publication Date",
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "N/A",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally // Pusatkan konten ini
                        ) {
                            // Penulis
                            book.author?.let { author -> // Pastikan author tidak null
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Author",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = author.name ?: "Unknown Author",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Gray
                                    )
                                }
                            } ?: run {
                                Text(
                                    text = "Unknown Author",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Genre Tags
                            book.genres?.let { genresList ->
                                if (genresList.isNotEmpty()) {
                                    LazyRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        contentPadding = PaddingValues(horizontal = 0.dp)
                                    ) {
                                        items(genresList) { genre ->
                                            AssistChip(
                                                onClick = { /* Handle genre click */ },
                                                label = { Text(genre.name ?: "Unknown Genre") },
                                                colors = AssistChipDefaults.assistChipColors(
                                                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                                    labelColor = MaterialTheme.colorScheme.primary
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                        // Deskripsi Buku
                        Text(
                            text = "Deskripsi:",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = Color.Black
                        )
                        Text(
                            text = book.deskripsi ?: "No description available.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
