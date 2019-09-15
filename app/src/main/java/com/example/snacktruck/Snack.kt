package com.example.snacktruck

import android.provider.Settings.Global.getString

class Snack(val category: String,
            val name: String,
            var selected: Boolean = false){

    companion object Categories{
        // Should match values in strings.xml (TODO: Test)?
        const val Veggie = "Veggie"
        const val NonVeggie = "Non-Veggie"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Snack) {
            category == other.category &&
                    name == other.name &&
                    selected == other.selected
        } else{
            super.equals(other)
        }
    }
}