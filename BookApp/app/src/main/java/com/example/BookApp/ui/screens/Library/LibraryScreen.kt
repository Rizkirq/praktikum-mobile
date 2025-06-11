package com.example.BookApp.ui.screens.Library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FilterList
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.BookApp.R
import com.example.BookApp.ui.components.BookItem
import com.example.BookApp.ui.navigation.Destinations
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController // <-- Import ini

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = hiltViewModel(),
    navController: NavHostController, // <-- Tambahkan parameter ini
    onBookClick: (Int) -> Unit = {} // Callback ketika item buku diklik
) {
    val userName by viewModel.userName.collectAsState()
    val bookmarkedBooks by viewModel.bookmarkedBooks.collectAsState()
    val historyBooks by viewModel.historyBooks.collectAsState()

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
                currentRoute = currentRoute ?: Destinations.LIBRARY,
                onHomeClick = {
                    if (currentRoute != Destinations.HOME) {
                        navController.navigate(Destinations.HOME) {
                            popUpTo(Destinations.HOME) { inclusive = true }
                        }
                    }
                },
                onExploreClick = {
                    if (currentRoute != Destinations.EXPLORE) {
                        navController.navigate(Destinations.EXPLORE) {
                            popUpTo(Destinations.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                onLibraryClick = { /* Already on Library */ }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Bookmark",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF9C27B0)
                    )
                    Text(
                        text = "History",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Normal),
                        color = Color.Black
                    )
                }
                IconButton(onClick = { /* TODO: Implement filter action */ }) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter", tint = Color.Black)
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    Text(
                        text = "Bookmarked Books",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Black
                    )
                    if (bookmarkedBooks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No bookmarked books yet.", color = Color.Gray)
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .height(IntrinsicSize.Max),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(bookmarkedBooks) { book ->
                                BookItem(book = book, onClick = { onBookClick(book.id) })
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "History Books",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Black
                    )
                    if (historyBooks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No history books yet.", color = Color.Gray)
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .height(IntrinsicSize.Max),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(historyBooks) { book ->
                                BookItem(book = book, onClick = { onBookClick(book.id) })
                            }
                        }
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
            selected = currentRoute == Destinations.EXPLORE, // <-- PERBAIKI currentRoute
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