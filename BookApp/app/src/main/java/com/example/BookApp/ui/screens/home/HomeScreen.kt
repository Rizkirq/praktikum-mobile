package com.example.BookApp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.BookApp.R
import com.example.BookApp.ui.components.BookItem
import com.example.BookApp.models.Book
import com.example.BookApp.models.Author
import com.example.BookApp.models.Genre
import com.example.BookApp.ui.navigation.Destinations
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToExplore: () -> Unit = {},
    onNavigateToLibrary: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    onBookClick: (Int) -> Unit
) {
    val homeState by viewModel.homeState.collectAsState()
    val userName by viewModel.userName.collectAsState()

    Scaffold(
        topBar = {
            HomeTopAppBar(
                userName = userName,
                onSearchClick = onNavigateToSearch,
                onProfileClick = onNavigateToProfile
            )
        },
        bottomBar = {
            HomeBottomNavigationBar(
                currentRoute = Destinations.HOME,
                onHomeClick = { /* Already on Home */ },
                onExploreClick = onNavigateToExplore,
                onLibraryClick = onNavigateToLibrary
            )
        }
    ) { paddingValues ->
        when (homeState) {
            is HomeState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is HomeState.Error -> {
                val errorMessage = (homeState as HomeState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.fetchBooks() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            is HomeState.Success -> {
                val recommendedBooks = (homeState as HomeState.Success).recommendedBooks
                val updatedBooks = (homeState as HomeState.Success).updatedBooks

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color(0xFFF5F9FF)),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        BookSection(title = "Rekomendasi", books = recommendedBooks, onBookClick = onBookClick)
                    }
                    item {
                        BookSection(title = "Update", books = updatedBooks, onBookClick = onBookClick)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    userName: String,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Welcome $userName",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Black
                )
            }
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun BookSection(title: String, books: List<Book>, onBookClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.Black
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(books) { book ->
                BookItem(book = book, onClick = { onBookClick(book.id) })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HomeBottomNavigationBar(
    currentRoute: String,
    onHomeClick: () -> Unit,
    onExploreClick: () -> Unit,
    onLibraryClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFFE0E7F5),
        tonalElevation = 5.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == Destinations.HOME,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF007BFF),
                selectedTextColor = Color(0xFF007BFF),
                indicatorColor = Color(0xFFC7D8F5),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = currentRoute == "explore",
            onClick = onExploreClick,
            icon = { Icon(painterResource(id = R.drawable.ic_explore), contentDescription = "Explore") },
            label = { Text("Explore") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF007BFF),
                selectedTextColor = Color(0xFF007BFF),
                indicatorColor = Color(0xFFC7D8F5),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = currentRoute == "library",
            onClick = onLibraryClick,
            icon = { Icon(painterResource(id = R.drawable.ic_library), contentDescription = "Library") },
            label = { Text("Library") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF007BFF),
                selectedTextColor = Color(0xFF007BFF),
                indicatorColor = Color(0xFFC7D8F5),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}