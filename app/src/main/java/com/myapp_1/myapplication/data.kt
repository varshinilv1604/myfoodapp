package com.myapp_1.myapplication.data

import android.content.Context
import java.io.File
data class FoodItem(
    var name: String,
    var price:String
)

 fun writeFoodListToFile(context: Context, fileName: String, foodList: List<FoodItem>) {
    val file = File(context.filesDir, fileName)
    file.printWriter().use { writer ->
        foodList.forEach { food ->
            writer.write("${food.name},${food.price}")
        }
    }
}

fun readFromFile(context: Context, fileName: String): List<FoodItem> {
    val file = File(context.filesDir, fileName)
    val foodList = mutableListOf<FoodItem>()
    if(!file.exists()){
        println("File not found,creating with default data")
        val defaultData= listOf(FoodItem("Pizza","50.0"),FoodItem("Burger","5.0"))
    }
    file.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size >= 2) {
                val name = parts[0]
                val price = parts[1]
                foodList.add(FoodItem(name,price))
            }
        }
    }
    return foodList
}
