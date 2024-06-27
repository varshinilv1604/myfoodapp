package com.myapp_1.myapplication

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.myapp_1.myapplication.data.Food
import com.myapp_1.myapplication.data.readFromFile
import kotlinx.coroutines.launch
import values.MyApplicationTheme
import values.getGreenColor
import java.io.File

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyApplication)
        createFile(this, "food_data.txt")
        setContent {
            MyApplicationTheme {
                val context=LocalContext.current
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                //Toast.makeText(context,"this is a toast", Toast.LENGTH_SHORT).show()
                            },
                            backgroundColor = getGreenColor(),
                            contentColor = Color.White
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Add")
                        }
                    }
                ) { paddingValues ->
                    // Pass the padding values to the content
                    Box(modifier = Modifier.padding(paddingValues)) {
                        TabLayout()
                    }
                }
            }
        }
    }
}

private fun createFile(context: Context, fileName: String) {
    val file = File(context.filesDir, fileName)
    if (!file.exists()) {
        file.createNewFile()
    }
}



@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout() {
    val pagerState = rememberPagerState(pageCount = 2)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val tabTitles = listOf("Food", "Fun")
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = getGreenColor(),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 4.dp,
                color = getGreenColor()
            )
        }
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                text = {
                    Text(
                        title,
                        color = if (pagerState.currentPage == index) getGreenColor() else Color.Gray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {
    val context= LocalContext.current
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> FoodList(context = context)
            1 -> TabContentScreen(data = "Welcome to Fun Screen")
        }
    }
}
//data class FoodItem(val food:food, var isChecked: Boolean=false)
@Composable
fun FoodList(context: Context){
    val foodItems= remember {
        readFromFile(context, "food_data.txt").toMutableStateList()
    }
    val checkStates=remember {
        mutableStateMapOf<Food, Boolean>().apply {
            foodItems.forEach { food -> this[food] = false }
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(foodItems) { food ->
            ListItem(
                item = food,
                isChecked = checkStates[food] ?: false,
                onCheckChange = { isChecked ->
                    checkStates[food] = isChecked
                }
            )
        }
    }
}
@Composable
fun ListItem(item: Food, isChecked:Boolean, onCheckChange: (Boolean) ->Unit){
    Surface(
        color=Color.White,
        border=BorderStroke(3.dp,Color(116,201,49)),
        modifier=Modifier.padding(vertical=10.dp,horizontal=20.dp)
    ){
        Row(verticalAlignment=Alignment.CenterVertically){
            Checkbox(
                checked=isChecked,
                colors=CheckboxDefaults.colors(checkedColor=Color.Black),
                onCheckedChange=onCheckChange
            )
            Text(
                text = "${item.name} - ${item.price}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun TabContentScreen(data: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = data,
            style = MaterialTheme.typography.h5,
            color = getGreenColor(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun TabContentScreenPreview() {
    MyApplicationTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /* Handle FAB click */ },
                    backgroundColor = getGreenColor(),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                TabLayout()
            }
        }
    }
}




