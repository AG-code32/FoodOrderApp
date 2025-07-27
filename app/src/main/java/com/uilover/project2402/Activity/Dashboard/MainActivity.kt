// app/src/main/java/com/uilover/project2402/Activity/Dashboard/MainActivity.kt
package com.uilover.project2402.Activity.Dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.uilover.project2402.Domain.CategoryModel
import com.uilover.project2402.Domain.FoodModel
import com.uilover.project2402.R
import com.uilover.project2402.ViewModel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1️⃣ Para que Compose se expanda bajo las barras de sistema:
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 2️⃣ Ocultamos solo la barra de navegación y la dejamos "sticky" para swipe:
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // 3️⃣ Finalmente cargamos tu UI:
        setContent {
            MainScreen()
        }
    }
}


@Composable
fun MainScreen(viewModel: MainViewModel = MainViewModel()) {
    // 1️⃣ Estado para búsqueda
    var searchQuery by rememberSaveable { mutableStateOf("") }

    // 2️⃣ Datos de categorías
    var categories by remember { mutableStateOf(listOf<CategoryModel>()) }
    var loadingCategories by remember { mutableStateOf(true) }

    // 3️⃣ Datos de productos
    val originalFoods = remember { mutableStateListOf<FoodModel>() }
    val displayedFoods = remember { mutableStateListOf<FoodModel>() }
    var loadingFoods by remember { mutableStateOf(true) }

    // 4️⃣ Carga inicial
    LaunchedEffect(Unit) {
        viewModel.loadCategory().observeForever { list ->
            categories = list
            loadingCategories = false
        }
        viewModel.loadBestFood().observeForever { list ->
            originalFoods.clear()
            originalFoods.addAll(list)
            displayedFoods.clear()
            displayedFoods.addAll(list)
            loadingFoods = false
        }
    }

    Scaffold(
        bottomBar = { MyBottomBar() },
        scaffoldState = rememberScaffoldState()
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.lightGrey))
                .padding(paddingValues),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 🔍 TopBar
            item(span = { GridItemSpan(2) }) {
                TopBar(
                    text = searchQuery,
                    onTextChange = { searchQuery = it },
                    onSearch = {
                        // temporal: recarga toda la lista sin filtrar
                        displayedFoods.clear()
                        displayedFoods.addAll(originalFoods)
                    }
                )
            }

            // 📂 Categorías (pizza, burger, chicken…)
            item(span = { GridItemSpan(2) }) {
                // Llamada posicional para evitar errores de named params
                CategorySection(categories, loadingCategories)
            }

            // 🏷 Título de sección
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Foods for you",
                    color = colorResource(R.color.darkPurple),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            // 🍽 Lista de productos
            if (loadingFoods) {
                item(span = { GridItemSpan(2) }) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                items(displayedFoods) { food ->
                    // tu card de producto
                    FoodItemCardGrid(item = food)
                }
            }
        }
    }
}