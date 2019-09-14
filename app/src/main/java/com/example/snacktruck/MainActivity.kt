package com.example.snacktruck

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

class MainActivity : AppCompatActivity() {
    private lateinit var snacksJson: JsonObject
    private lateinit var snackMenu: List<Snack>
    private lateinit var snackListView: ListView

    private val filterCategories = mutableMapOf(
        Snack.Veggie to true, Snack.NonVeggie to true
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        snackListView =  this.findViewById(R.id.snackList)

        resetSnackList()
    }

    private fun resetSnackList() {
        snacksJson = getSnacksFromServer()
        snackMenu = getSnackList(snacksJson).toMutableList()
        updateSnackList()
    }

    private fun updateSnackList() {
        val snackAdapter = SnackListAdapter(this,
            snackMenu.filter { filterCategories[it.category] as Boolean })
        snackListView.adapter = snackAdapter
    }

    // Converts the JSON object to a list of Snack objects
    private fun getSnackList(snacks: JsonObject): Array<Snack> {
        val categories = listOf(Snack.Veggie, Snack.NonVeggie)

        // For each category, find the snack names, then create a Snack object for each,
        // and return them all as a flat list
        // TODO: Add unit test
        return (categories.flatMap { c ->
            (snacks[c] as JsonArray<String>).map {
                Snack(c, it)
            }
        }).toTypedArray()
    }

    private fun getSnacksFromServer(): JsonObject {
        // TODO: get list from server. Hard code for now
        val snackStream = this.resources.openRawResource(R.raw.snacks)

        @Suppress("UNCHECKED_CAST")
        val snackList = Parser.default().parse(snackStream) as JsonObject
        snackStream.close()

        return snackList
    }

    fun onSnackCheckboxClick(view: View){
        // TODO: find some way to test this
        val textView: TextView = (view.parent as LinearLayout).findViewById(R.id.snackName)
        snackMenu = snackMenu.map {
            if (it.name == textView.text){
                it.selected = !it.selected
            }
            it
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun submitOrderOnClick(view: View){
        if(snackMenu.none { it.selected }) return

        val orderList = snackMenu.filter{it.selected}.map { it.name }
        val orderListView = ListView(this)
        orderListView.adapter = ArrayAdapter(this, R.layout.order_list, orderList)

        val confirmDialog = AlertDialog.Builder(this)
            .setTitle("Order Summary")
            .setView(orderListView)
            .setPositiveButton("Confirm") { _, _ -> sendOrder()}
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
        confirmDialog.show()
    }

    private fun sendOrder(){
        // TODO: Send the order to the server

        resetSnackList()
    }

    fun toggleFilter(view: View){
        val textView: TextView = (view.parent as LinearLayout).getChildAt(1) as TextView
        filterCategories[textView.text.toString()]  = !(filterCategories[textView.text] as Boolean)
        updateSnackList()
    }

}
