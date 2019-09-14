package com.example.snacktruck

class Snack(val category: String,
            val name: String){

    var selected = false

    companion object Categories{
        val Veggie = "Veggie"
        val NonVeggie = "Non-Veggie"
    }
}