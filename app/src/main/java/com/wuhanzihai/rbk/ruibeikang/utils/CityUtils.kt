package com.wuhanzihai.rbk.ruibeikang.utils

import android.content.Context
import android.util.Log
import com.hhjt.baselibrary.common.BaseApplication
import com.hhjt.baselibrary.common.BaseConstant
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*

class CityUtils() {

    companion object {
        val instance by lazy { CityUtils() }
    }

    fun getProvinceList(context: Context): List<String> {
        val newstringBuilder = StringBuilder()
        var list = mutableListOf<String>()
        try {
            var inputStream = context.resources.assets.open("city.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            val text:List<String> = reader.readLines()
            for(line in text){
                newstringBuilder.append(line)
            }
            reader.close()
            isr.close()
            inputStream.close()

            val json = newstringBuilder.toString()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                list.add(jsonArray.optJSONObject(i).getString("name"))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun getProvinceCode(context: Context,province: String):String {
        val newstringBuilder = StringBuilder()
        try {
            var inputStream = context.resources.assets.open("city.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            val text:List<String> = reader.readLines()
            for(line in text){
                newstringBuilder.append(line)
            }
            reader.close()
            isr.close()
            inputStream.close()

            val json = newstringBuilder.toString()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                if (jsonArray.optJSONObject(i).getString("name") == province){
                    return jsonArray.optJSONObject(i).getString("code")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return "00000"
    }

    fun getProvince(context: Context,provinceCode: String):String {
        val newstringBuilder = StringBuilder()
        try {
            var inputStream = context.resources.assets.open("city.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            val text:List<String> = reader.readLines()
            for(line in text){
                newstringBuilder.append(line)
            }
            reader.close()
            isr.close()
            inputStream.close()

            val json = newstringBuilder.toString()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                if (jsonArray.optJSONObject(i).getString("code") == provinceCode){
                    return jsonArray.optJSONObject(i).getString("name")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getCityList(context: Context, province: String): List<String> {
        val newstringBuilder = StringBuilder()
        var list = mutableListOf<String>()
        try {
            var inputStream = context.resources.assets.open("city.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            val text:List<String> = reader.readLines()
            for(line in text){
                newstringBuilder.append(line)
            }
            reader.close()
            isr.close()
            inputStream.close()

            val json = newstringBuilder.toString()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                if (jsonArray.optJSONObject(i).getString("name") == province){
                    val jsonCity = jsonArray.optJSONObject(i).getJSONArray("city")
                    for (i1 in 0 until jsonCity.length()) {
                        list.add(jsonCity.optJSONObject(i1).getString("name"))
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun getCityCode(context: Context, province: String,city: String): String {
        val newstringBuilder = StringBuilder()
        try {
            var inputStream = context.resources.assets.open("city.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            val text:List<String> = reader.readLines()
            for(line in text){
                newstringBuilder.append(line)
            }
            reader.close()
            isr.close()
            inputStream.close()

            val json = newstringBuilder.toString()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                if (jsonArray.optJSONObject(i).getString("name") == province){
                    val jsonCity = jsonArray.optJSONObject(i).getJSONArray("city")
                    for (i1 in 0 until jsonCity.length()) {
                        if (jsonCity.optJSONObject(i1).getString("name") == city){
                            return jsonCity.optJSONObject(i1).getString("code")
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return "0000"
    }

    fun getCity(context: Context, provinceCode: String,cityCode: String): String {
        val newstringBuilder = StringBuilder()
        try {
            var inputStream = context.resources.assets.open("city.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            val text:List<String> = reader.readLines()
            for(line in text){
                newstringBuilder.append(line)
            }
            reader.close()
            isr.close()
            inputStream.close()

            val json = newstringBuilder.toString()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                if (jsonArray.optJSONObject(i).getString("code") == provinceCode){
                    val jsonCity = jsonArray.optJSONObject(i).getJSONArray("city")
                    for (i1 in 0 until jsonCity.length()) {
                        if (jsonCity.optJSONObject(i1).getString("code") == cityCode){
                            return jsonCity.optJSONObject(i1).getString("name")
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getAreaList(context: Context,province:String, city: String): List<String> {
        val newstringBuilder = StringBuilder()
        var list = mutableListOf<String>()
        try {
            var inputStream = context.resources.assets.open("city.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            val text:List<String> = reader.readLines()
            for(line in text){
                newstringBuilder.append(line)
            }
            inputStream.close()

            val json = newstringBuilder.toString()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                if (jsonArray.optJSONObject(i).getString("name") == province){
                    val jsonCity = jsonArray.optJSONObject(i).getJSONArray("city")
                    for (i1 in 0 until jsonCity.length()) {
                        if (jsonCity.optJSONObject(i1).getString("name") == city){
                            val jsonArea = jsonCity.optJSONObject(i1).getJSONArray("area")
                            for (i2 in 0 until jsonArea.length()) {
                                list.add(jsonArea.optJSONObject(i2).getString("name"))
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun getAreaCode(context: Context,province:String, city: String, area: String): String {
        val newstringBuilder = StringBuilder()
        var list = mutableListOf<String>()
        try {
            var inputStream = context.resources.assets.open("city.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            val text:List<String> = reader.readLines()
            for(line in text){
                newstringBuilder.append(line)
            }
            inputStream.close()

            val json = newstringBuilder.toString()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                if (jsonArray.optJSONObject(i).getString("name") == province){
                    val jsonCity = jsonArray.optJSONObject(i).getJSONArray("city")
                    for (i1 in 0 until jsonCity.length()) {
                        if (jsonCity.optJSONObject(i1).getString("name") == city){
                            val jsonArea = jsonCity.optJSONObject(i1).getJSONArray("area")
                            for (i2 in 0 until jsonArea.length()) {
                                if (jsonArea.optJSONObject(i2).getString("name") ==area){
                                    return jsonArea.optJSONObject(i2).getString("code")
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return "0000"
    }

    fun getArea(context: Context,provinceCode:String, cityCode: String, areaCode: String): String {
        val newstringBuilder = StringBuilder()
        var list = mutableListOf<String>()
        try {
            var inputStream = context.resources.assets.open("city.json")
            val isr = InputStreamReader(inputStream)
            val reader = BufferedReader(isr)
            val text:List<String> = reader.readLines()
            for(line in text){
                newstringBuilder.append(line)
            }
            inputStream.close()

            val json = newstringBuilder.toString()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                if (jsonArray.optJSONObject(i).getString("code") == provinceCode){
                    val jsonCity = jsonArray.optJSONObject(i).getJSONArray("city")
                    for (i1 in 0 until jsonCity.length()) {
                        if (jsonCity.optJSONObject(i1).getString("code") == cityCode){
                            val jsonArea = jsonCity.optJSONObject(i1).getJSONArray("area")
                            for (i2 in 0 until jsonArea.length()) {
                                if (jsonArea.optJSONObject(i2).getString("code") ==areaCode){
                                    return jsonArea.optJSONObject(i2).getString("name")
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ""
    }
}