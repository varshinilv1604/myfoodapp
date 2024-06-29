package com.myapp_1.myapplication.com.myapp_1.myapplication
//import com.myapp_1.myapplication2.ui.theme.getGreenColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import values.MyApplicationTheme

//import values.MyApplicationTheme

class Food : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                AddFoodItemScreen()
            }
        }
    }
}

@Composable
fun AddFoodItemScreen() {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var canteen by remember { mutableStateOf(TextFieldValue("")) }
    var price by remember { mutableStateOf(TextFieldValue("")) }
    var type by remember { mutableStateOf("") }
    var cuisine by remember { mutableStateOf(TextFieldValue("")) }
    val types = listOf("Main Course", "Starters", "Dessert")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Food Item",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        CustomTextField(value = name, onValueChange = { name = it }, label = "Name")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = canteen, onValueChange = { canteen = it }, label = "Canteen")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = price, onValueChange = { price = it }, label = "Price")
        Spacer(modifier = Modifier.height(8.dp))
        DropdMenu(types, type, { type = it }, "Type")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = cuisine, onValueChange = { cuisine = it }, label = "Cuisine")
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle save action */ },
            colors = ButtonDefaults.buttonColors(Color.Green),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Save", color = Color.White)
        }
    }
}
@Composable
fun CustomTextField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, label: String) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green, shape = MaterialTheme.shapes.small)
            .padding(8.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
    ) {
        if (value.text.isEmpty()) {
            Text(text = label, color = Color.White.copy(alpha = 0.5f))
        }
        it()
    }
}

@Composable
fun DropdMenu(options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit, label: String) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Green, shape = MaterialTheme.shapes.small)
        .padding(8.dp)
        .clickable { expanded = !expanded }
    ) {
        Text(
            text = if (selectedOption.isEmpty()) label else selectedOption,
            color = Color.White,
            fontSize = 16.sp
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AddFoodItemScreenPreview() {
    MyApplicationTheme {
        AddFoodItemScreen()
    }
}


