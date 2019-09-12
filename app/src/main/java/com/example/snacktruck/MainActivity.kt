package com.example.snacktruck

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

class MainActivity : AppCompatActivity() {
    private lateinit var snacks: JsonObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        snacks = getSnacksFromServer()
        val snackListView: ListView = this.findViewById(R.id.snackList)
        val snackAdapter = SnackListAdapter(this, getSnackList(snacks))
        snackListView.adapter = snackAdapter
    }

    // Converts the JSON object to a list of Snack objects
    private fun getSnackList(snacks: JsonObject): Array<Snack> {
        val categories = listOf(Snack.Categories.Veggie, Snack.Categories.NonVeggie)

        // For each category, find the snack names, then create a Snack object for each,
        // and return them all as a flat list
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
}
