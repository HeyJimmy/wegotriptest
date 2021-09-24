package com.example.wegotriptest

import android.content.res.Resources
import java.io.BufferedReader
import java.util.ArrayList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class TourStep(val title: String, val text: String, val imgUrls: ArrayList<String>, val audio: String)

data class Tour(val title: String, val steps: Array<TourStep>) {

    companion object {
        /**
         * Loads a raw JSON at R.raw.tours and converts it into a list of Tours objects
         */
        fun initTourList(resources: Resources): List<Tour> {
            val inputStream = resources.openRawResource(R.raw.tours)
            val jsonProductsString = inputStream.bufferedReader().use(BufferedReader::readText)
            val gson = Gson()
            val tourListType = object : TypeToken<ArrayList<Tour>>() {}.type
            return gson.fromJson<List<Tour>>(jsonProductsString, tourListType)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tour

        if (title != other.title) return false
        if (!steps.contentEquals(other.steps)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + steps.contentHashCode()
        return result
    }
}
