package com.example.snacktruck

import android.provider.Settings.Global.getString

class Snack(val category: String,
            val name: String){

    var selected = false

    companion object Categories{
        // Should match values in strings.xml (TODO: Test)?
        const val Veggie = "Veggie"
        const val NonVeggie = "Non-Veggie"
    }
}