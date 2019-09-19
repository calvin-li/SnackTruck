package com.example.snacktruck


class Snack(val category: String,
            val name: String,
            var selected: Boolean = false){

    companion object Categories{
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