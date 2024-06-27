package com.myapp_1.myapplication.data

import android.content.Context
import java.io.File
data class Food(
    var name: String,
    var price:String
)

private fun writeFoodListToFile(context: Context, fileName: String, foodList: List<Food>) {
    val file = File(context.filesDir, fileName)
    file.printWriter().use { writer ->
        foodList.forEach { food ->
            writer.write("${food.name},${food.price}")
        }
    }
}

fun readFromFile(context: Context, fileName: String): List<Food> {
    val file = File(context.filesDir, fileName)
    val foodList = mutableListOf<Food>()
    if(!file.exists()){
        println("File not found,creating with default data")
        val defaultData= listOf(Food("Pizza","50.0"),Food("Burger","5.0"))
    }
    file.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size >= 2) {
                val name = parts[0]
                val price = parts[1]
                foodList.add(Food(name,price))
            }
        }
    }
    return foodList
}
