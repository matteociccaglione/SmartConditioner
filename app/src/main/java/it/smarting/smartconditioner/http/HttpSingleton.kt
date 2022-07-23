package it.smarting.smartconditioner.http

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.smarting.smartconditioner.model.Group
import it.smarting.smartconditioner.viewmodel.GroupsViewModel
import it.smarting.smartconditioner.viewmodel.SingleGroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class HttpSingleton constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: HttpSingleton? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HttpSingleton(context).also {
                INSTANCE = it
            }
        }
    }

    private val url = "https://io.adafruit.com/api/v2/"
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getAllGroups(username: String, key: String, viewModel: GroupsViewModel, context: Context) {
        val myUrl = url + "${username}/groups"
        val jsonArrayRequest = object : JsonArrayRequest(Request.Method.GET, myUrl, null, Response.Listener<JSONArray>{
            val listType = object : TypeToken<List<Group>>() {}.type
            val listGroups = Gson().fromJson<List<Group>>(it.toString(), listType)

            CoroutineScope(Dispatchers.Main).launch {
                viewModel.groupList.value = listGroups
                Log.d("MyRequest", "request group successful")
            }
        }, Response.ErrorListener{
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.groupList.value = Collections.emptyList()
                val sharedPrefs = context.getSharedPreferences("CloudAccount", Activity.MODE_PRIVATE)
                val editor = sharedPrefs.edit()
                editor.clear()
                editor.apply()
            }
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["X-AIO-Key"] = key
                return headers
            }
        }

        requestQueue.add(jsonArrayRequest)
    }

    fun getSingleGroup(username: String, key: String, groupKey: String, viewModel : SingleGroupViewModel){
        val myUrl = url + "${username}/groups/${groupKey}"
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, myUrl, null, Response.Listener<JSONObject>{
            val jsonType = object : TypeToken<Group>() {}.type
            val group = Gson().fromJson<Group>(it.toString(), jsonType)

            CoroutineScope(Dispatchers.Main).launch {
                viewModel.group.value = group
                Log.d("MyRequest", "request group successful")
            }
        }, Response.ErrorListener{
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.group.value = null
            }
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["X-AIO-Key"] = key
                return headers
            }
        }

        requestQueue.add(jsonObjectRequest)
    }


    fun updateSingleFeed(username: String, key: String, feedKey:String, value:String){
        val myUrl = url + "${username}/feeds/${feedKey}/data"
        val requestBody = "feed=\"${value}\"".toByteArray()
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST, myUrl, JSONObject("{\"value\": \"${value}\"}"),
            Response.Listener<JSONObject>{},
        Response.ErrorListener{}) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["X-AIO-Key"] = key
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        Log.d("updateRequest",String(jsonObjectRequest.body))
        requestQueue.add(jsonObjectRequest)
    }

}