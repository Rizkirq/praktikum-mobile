package com.example.BookApp.ui.screens.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.BookApp.ui.navigation.Destinations
import com.example.BookApp.models.User // Untuk Preview
import com.example.BookApp.ui.screens.Explore.HomeBottomNavigationBar
import kotlinx.coroutines.flow.MutableStateFlow // Untuk Preview
import kotlinx.coroutines.flow.asStateFlow // Untuk Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: androidx.navigation.NavHostController,
    onBackClick: () -> Unit = {},
    onLogout: () -> Unit
) {
    val profileState by viewModel.profileState.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val logoutSuccess by viewModel.logoutSuccess.collectAsState()

    LaunchedEffect(logoutSuccess) {
        if (logoutSuccess) {
            onLogout()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Destinations.SEARCH) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
                    }
                    IconButton(onClick = { /* Already on Profile */ }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            HomeBottomNavigationBar(
                currentRoute = currentRoute ?: Destinations.PROFILE,
                onHomeClick = {
                    if (currentRoute != Destinations.HOME) {
                        navController.navigate(Destinations.HOME) { popUpTo(Destinations.HOME) { inclusive = true } }
                    }
                },
                onExploreClick = {
                    if (currentRoute != Destinations.EXPLORE) {
                        navController.navigate(Destinations.EXPLORE) { popUpTo(Destinations.HOME) { saveState = true }; launchSingleTop = true; restoreState = true }
                    }
                },
                onLibraryClick = {
                    if (currentRoute != Destinations.LIBRARY) {
                        navController.navigate(Destinations.LIBRARY) { popUpTo(Destinations.HOME) { saveState = true }; launchSingleTop = true; restoreState = true }
                    }
                }
            )
        }
    ) { paddingValues ->
        when (profileState) {
            is ProfileState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ProfileState.Error -> {
                val errorMessage = (profileState as ProfileState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.fetchUserProfile() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            is ProfileState.Success -> {
                val user = (profileState as ProfileState.Success).user
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color(0xFFF5F9FF)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        // Profile Picture Section
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray), // Placeholder circle
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile Picture Placeholder",
                                modifier = Modifier.size(80.dp),
                                tint = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = { /* TODO: Implement Upload Photo */ },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.Upload, contentDescription = "Upload Photo", Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upload Photo")
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Name Section
                        ProfileInfoRow(
                            label = "Name",
                            value = user.name ?: "N/A",
                            onEditClick = { /* TODO: Implement Edit Name */ }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Email Section
                        ProfileInfoRow(
                            label = "Email",
                            value = user.email ?: "N/A",
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        ProfileInfoRow(
                            label = "Password",
                            value = "**********",
                            onEditClick = { /* TODO: Implement Edit Password */ }
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = { viewModel.logout() },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text(text = "Logout", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(
    label: String,
    value: String,
    onEditClick: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Gray
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            onEditClick?.let {
                TextButton(onClick = it) {
                    Text("Edit", color = Color(0xFF007BFF), fontWeight = FontWeight.Bold)
                }
            }
        }
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp), color = Color.LightGray)
    }
}

