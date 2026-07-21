package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Mic
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, 
    bottomPadding: androidx.compose.ui.unit.Dp = 0.dp,
    onNavigateToCircleDeals: () -> Unit = {},
    onNavigateToCategories: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val isHeaderVisible by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 5 }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentPadding = PaddingValues(bottom = bottomPadding + 16.dp)
        ) {
            item {
                HeroBannerSection()
            }
            item {
                CategoriesSection(
                    title = null, 
                    size = 72.dp, 
                    onViewAllClick = onNavigateToCategories,
                    onCategoryClick = onCategoryClick
                )
            }
            item {
                CircleDealsSection(onNavigateToCircleDeals)
            }
            item {
                ProductGridSection("Best Selling")
            }
            item {
                CategoryProductsRow("Electronics", allProductsMockData.take(4))
            }
            item {
                CategoryProductsRow("Fashion", allProductsMockData.drop(2).take(4))
            }
            item {
                CategoryProductsRow("Home & Lifestyle", allProductsMockData.drop(4).take(4))
            }
            item {
                CategoryProductsRow("Beauty", allProductsMockData.drop(1).take(4))
            }
        }

        AnimatedVisibility(
            visible = isHeaderVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Surface(
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                        .height(48.dp)
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 12.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Storefront,
                            contentDescription = "Logo",
                            modifier = Modifier.size(24.dp).clip(CircleShape),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Circle Bazar", fontWeight = FontWeight.Bold, color = Color.Black, style = MaterialTheme.typography.titleMedium)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.Black)
                        }
                        IconButton(onClick = { }) {
                            Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeroBannerSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        // Banner Image
        Image(
            painter = painterResource(id = R.drawable.img_hero_banner_1784631455947),
            contentDescription = "Main Banner",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(Color.White.copy(alpha = 0.6f), Color.Transparent, Color.White.copy(alpha = 0.1f)),
                        startY = 0f,
                        endY = 300f
                    )
                )
        )

        // Floating Search Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 0.dp)
                .height(44.dp)
                .align(Alignment.TopCenter),
            shape = RoundedCornerShape(22.dp),
            color = Color.White,
            shadowElevation = 4.dp,
            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.LightGray.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Search in Circle Bazar...",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Camera Search",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Voice Search",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { }
                )
            }
        }
    }
}

@Composable
fun CircleDealsSection(onNavigateToCircleDeals: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Circle Deals",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(12.dp))
                CountdownTimer()
            }
            Text(
                text = "See More >",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToCircleDeals() }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(circleDealsMockData) { product ->
                CircleDealCard(product = product)
            }
        }
    }
}

@Composable
fun CountdownTimer() {
    var timeLeft by remember { mutableIntStateOf(12 * 3600 + 34 * 60 + 56) } // 12h 34m 56s

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    val hours = timeLeft / 3600
    val minutes = (timeLeft % 3600) / 60
    val seconds = timeLeft % 60

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimerBox(hours.toString().padStart(2, '0'))
        Text(":", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        TimerBox(minutes.toString().padStart(2, '0'))
        Text(":", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        TimerBox(seconds.toString().padStart(2, '0'))
    }
}

@Composable
fun TimerBox(time: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFFFFD54F))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            color = Color.Black,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

data class Product(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val offerPrice: String,
    val oldPrice: String,
    val discountPercent: String,
    val stockLeft: Int,
    val totalStock: Int,
    val rating: String = "4.5",
    val soldCount: String = "1.2k Sold"
)

val allProductsMockData = listOf(
    Product(1, "Premium Smartphone Pro Max", R.drawable.img_product_phone_1784631474592, "$999", "$1299", "-23%", 5, 20),
    Product(2, "Smart Watch Series 8", R.drawable.img_product_watch_1784631484667, "$349", "$449", "-22%", 10, 50),
    Product(3, "Noise Cancelling Headphones", R.drawable.img_product_headphones_1784631497033, "$249", "$349", "-28%", 2, 10),
    Product(4, "Ultra Slim Laptop 15\"", R.drawable.img_product_phone_1784631474592, "$1299", "$1499", "-13%", 15, 30),
    Product(5, "Classic Leather Band Watch", R.drawable.img_product_watch_1784631484667, "$199", "$250", "-20%", 8, 40),
    Product(6, "Sport Wireless Earbuds", R.drawable.img_product_headphones_1784631497033, "$129", "$179", "-27%", 25, 100),
    Product(7, "Gaming Phone X", R.drawable.img_product_phone_1784631474592, "$899", "$999", "-10%", 10, 20),
    Product(8, "Fitness Tracker Band", R.drawable.img_product_watch_1784631484667, "$49", "$79", "-37%", 50, 100)
)

val circleDealsMockData = allProductsMockData.take(3)

@Composable
fun CircleDealCard(product: Product) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .clickable { },
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                )
                // Discount Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = product.discountPercent,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Wishlist Icon
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val circleLabelId = "circleLabel"
            val text = androidx.compose.ui.text.buildAnnotatedString {
                appendInlineContent(circleLabelId, "[Circle]")
                append(" ")
                append(product.name)
            }
            
            val inlineContent = mapOf(
                Pair(
                    circleLabelId,
                    androidx.compose.foundation.text.InlineTextContent(
                        androidx.compose.ui.text.Placeholder(
                            width = 38.sp,
                            height = 14.sp,
                            placeholderVerticalAlign = androidx.compose.ui.text.PlaceholderVerticalAlign.TextCenter
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(2.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Circle",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                )
            )

            Text(
                text = text,
                inlineContent = inlineContent,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.height(40.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = product.offerPrice,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = product.oldPrice,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black,
                    textDecoration = TextDecoration.LineThrough
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Progress Bar with Text Inside
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                // The filled part
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = product.stockLeft.toFloat() / product.totalStock.toFloat())
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.CenterStart)
                )
                // Text over the progress bar
                Text(
                    text = "${product.stockLeft} left",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CategoriesSection(
    title: String? = null, 
    size: androidx.compose.ui.unit.Dp = 72.dp,
    onViewAllClick: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    val categories = listOf(
        "Electronics", "Fashion", "Beauty", "Home", "Sports", 
        "Toys", "Groceries", "Automotive", "Books", "Health"
    )
    
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, 
                    modifier = Modifier.clickable { onCategoryClick(category) }.width(size)
                ) {
                    Box(
                        modifier = Modifier
                            .size(size)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        coil.compose.AsyncImage(
                            model = "https://picsum.photos/seed/${category.hashCode()}/200/200",
                            contentDescription = category,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            // View All Item
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, 
                    modifier = Modifier.clickable { onViewAllClick() }.width(size)
                ) {
                    Box(
                        modifier = Modifier
                            .size(size)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "View All",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(size / 2)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "View All",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProductGridSection(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        // Simulate a grid with LazyRow for now, or a Column of Rows
        // Just displaying a simple row of products for the mockup
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(circleDealsMockData.reversed()) { product ->
                StandardProductCard(product)
            }
        }
    }
}

@Composable
fun StandardProductCard(product: Product, modifier: Modifier = Modifier.width(160.dp)) {
    Card(
        modifier = modifier.clickable { },
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().padding(12.dp)
                )
                // Discount Badge
                if (product.discountPercent.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = product.discountPercent,
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                // Wishlist Icon
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.height(40.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = product.offerPrice,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                if (product.oldPrice.isNotEmpty()) {
                    Text(
                        text = product.oldPrice,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = product.rating,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = product.soldCount,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black
                    )
                }
                
                // Add to Cart Button
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Add to Cart",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
