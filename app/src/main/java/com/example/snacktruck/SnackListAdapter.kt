package com.example.snacktruck

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SnackListAdapter(context: Context,
                       private val snackList: List<Snack>):
ArrayAdapter<Snack>(context, 0, snackList)
// Overriding GetView, so don't have to pass item layout to constructor
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val container: View = convertView ?:
        (this.context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.snack_item, parent, false)

        val snackTextView = container.findViewById<TextView>(R.id.snackName)
        val currentSnack = snackList[position]

        snackTextView.text = currentSnack.name
        when (currentSnack.category){
            Snack.Categories.Veggie -> snackTextView.setTextColor(Color.rgb(0, 191, 0))
            Snack.Categories.NonVeggie -> snackTextView.setTextColor(Color.RED)
        }

        return container
    }
}
