package com.example.BookApp.ui.screens.Explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.FilterList // <-- Untuk ikon filter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.BookApp.R
import com.example.BookApp.models.Book
import com.example.BookApp.ui.components.BookItem
import com.example.BookApp.ui.navigation.Destinations
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    navController: NavHostController,
    onBookClick: (Int) -> Unit,
    onNavigateToHome: () -> Unit = {},
    onNavigateToLibrary: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    onNavigateToExplore: () -> Unit = {}
) {
    val exploreState by viewModel.exploreState.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            HomeTopAppBar(
                userName = userName,
                onSearchClick = { navController.navigate(Destinations.SEARCH) },
                onProfileClick = { navController.navigate(Destinations.PROFILE) }
            )
        },
        bottomBar = {
            HomeBottomNavigationBar(
                currentRoute = currentRoute ?: Destinations.EXPLORE, // Gunakan currentRoute
                onHomeClick = {
                    if (currentRoute != Destinations.HOME) {
                        navController.navigate(Destinations.HOME) {
                            popUpTo(Destinations.HOME) { inclusive = true }
                        }
                    }
                },
                onExploreClick = { /* Already on Explore */ },
                onLibraryClick = {
                    if (currentRoute != Destinations.LIBRARY) {
                        navController.navigate(Destinations.LIBRARY) {
                            popUpTo(Destinations.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F9FF))
        ) {
            // Search Bar dan Filter Icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Cari...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search input") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF007BFF),
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                IconButton(onClick = { /* TODO: Implement filter action */ }) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter", tint = Color.Black)
                }
            }

            // Konten Utama (Grid Buku)
            when (exploreState) {
                is ExploreState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is ExploreState.Error -> {
                    val msg = (exploreState as ExploreState.Error).message
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Error: $msg", color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.fetchBooks() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
                is ExploreState.Success -> {
                    val books = (exploreState as ExploreState.Success).books
                    if (books.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No books found.", style = MaterialTheme.typography.bodyLarge)
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(books) { book ->
                                BookItem(book = book, onClick = { onBookClick(book.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

// Reused from HomeScreen.kt (copy-paste if not in common module)
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
            selected = currentRoute == Destinations.EXPLORE,
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
            selected = currentRoute == Destinations.LIBRARY,
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