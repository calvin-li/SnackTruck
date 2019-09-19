package com.example.snacktruck

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.*
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

class MainActivity : AppCompatActivity() {
    @VisibleForTesting
    // Denotes that this method would otherwise have been private
    internal lateinit var snacksJson: JsonObject
    @VisibleForTesting
    internal lateinit var snackMenu: List<Snack>
    private lateinit var snackListView: ListView

    private val filterCategories = mutableMapOf(
        Snack.Veggie to true, Snack.NonVeggie to true
    )

    @VisibleForTesting
    internal var newSnacks: List<Snack> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        snackListView =  this.findViewById(R.id.snack_list)

        resetSnackList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add -> {
                val layoutInflater: LayoutInflater = this.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val addView = layoutInflater.inflate(R.layout.add_snack_dialog, null) as LinearLayout
                val editText = addView.findViewById<EditText>(R.id.add_snack_edittext)
                val categoryChooser = addView.findViewById<Spinner>(R.id.category_chooser)

                categoryChooser.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    listOf(Snack.Veggie, Snack.NonVeggie)
                    )

                val addDialog = AlertDialog.Builder(this)
                    .setTitle("Add New Item")
                    .setView(addView)
                    .setPositiveButton("Confirm") { _, _ ->
                        addOrder(categoryChooser.selectedItem as String, editText.text.toString())}
                    .setNegativeButton("Cancel") { _, _ -> }
                    .create()

                // Pressing "enter" will confirm
                if(Build.VERSION.SDK_INT > 14) {
                    editText.setOnEditorActionListener { _, _, _ ->
                        addDialog.getButton(DialogInterface.BUTTON_POSITIVE).callOnClick()
                    }
                }

                addDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED)
                addDialog.show()
                editText.requestFocus()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addOrder(category: String, newSnack: String) {
        newSnacks = newSnacks.plus(Snack(category, newSnack))
        resetSnackList()
    }

    private fun resetSnackList() {
        snacksJson = getSnacksFromServer()
        snackMenu = getSnackList()
        updateSnackList()
    }

    private fun updateSnackList() {
        val snackAdapter = SnackListAdapter(this,
            snackMenu.filter { filterCategories[it.category] as Boolean })
        snackListView.adapter = snackAdapter
    }

    // Converts the JSON object to a list of Snack objects
    @VisibleForTesting
    internal fun getSnackList(): List<Snack> {
        val categories = listOf(Snack.Veggie, Snack.NonVeggie)

        // For each category, find the snack names, then create a Snack object for each,
        // and return them all as a flat list
        // TODO: Add unit test
        val defaultSnackList = (categories.flatMap { c ->
            (snacksJson[c] as JsonArray<String>).map {
                Snack(c, it)
            }
        }).toList()

        val newSnackList = newSnacks.map { it.selected = false; it }

        return defaultSnackList + newSnackList

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
        val textView: TextView = (view.parent as LinearLayout).findViewById(R.id.snack_name)
        toggleSnack(textView.text.toString())
    }

    @VisibleForTesting
    internal fun toggleSnack(snackName: String) {
        snackMenu = snackMenu.map {
            if (it.name == snackName) {
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
        orderListView.id = R.id.order_list
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
