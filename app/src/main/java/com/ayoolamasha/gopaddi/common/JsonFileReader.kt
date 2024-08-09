package com.ayoolamasha.gopaddi.common

import android.content.Context
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CountriesUIData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonFileReader @Inject constructor(@ApplicationContext private val context: Context) {
    private var json = ""
    private var jsonFileData = ArrayList<CountriesUIData>()

//    internal fun readReportJson(fileName: String): List<JsonFileData>{
//        try {
//            val inputStream =  context.assets.open(fileName)
//            json = inputStream.bufferedReader().use{it.readText()}
//
//            val jsonArray = JSONArray(json)
//
//            for (i in 0..jsonArray.length()){
//                val jsonObject = jsonArray.getJSONObject(i)
//             val img =   jsonObject.getString("img")
//             val title =   jsonObject.getString("title")
//             val desc =   jsonObject.getString("description")
//             val price =   jsonObject.getString("price")
//
//                jsonFileData.add(
//                    JsonFileData(
//                    img = img,
//                    title = title,
//                    description = desc,
//                    price = price
//                )
//                )
//
//            }
//
//        }catch (e: IOException){
//            Timber.d(e)
//
//        }
//
//        return listOf(jsonFileData)
//    }

    internal fun readCountriesJsonArray(): List<CountriesUIData> {
        try {
            val inputStream = context.assets.open("countries_list.json")
            json = inputStream.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val flag = jsonObject.getString("flag")
                val iso2 = jsonObject.getString("iso2")
                val iso3 = jsonObject.getString("iso3")

                jsonFileData.add(
                    CountriesUIData(
                        name = name,
                        flag = flag,
                        iso2 = iso2,
                        iso3 = iso3,
                    )
                )

            }

        } catch (e: IOException) {
            Timber.d(e)

        }

        return jsonFileData.toList()
    }

    internal fun parseJsonToModel(jsonString: String): List<CountriesUIData> {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<CountriesUIData>>() {}.type)
    }
}