package it.smarting.smartconditioner.http

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.smarting.smartconditioner.model.Groups
import it.smarting.smartconditioner.viewmodel.GroupsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

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

    private val requestQueue : RequestQueue = Volley.newRequestQueue(context)

    private fun getGroup(username: String, key: String, viewModel: GroupsViewModel) {
        val myUrl = url + "/${username}/groups"
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, myUrl, null, {
            val listType = object : TypeToken<List<Groups>>() {}.type
            val listGroups = Gson().fromJson<List<Groups>>(it.toString(), listType)

            CoroutineScope(Dispatchers.Main).launch {
                viewModel.groupList.value = listGroups
                Log.d("HTTP", "request groups successful")
            }
        }) {
            // TODO: handle error
        }
        jsonArrayRequest.headers.put("X-AIO_Key", key)
        requestQueue.add(jsonArrayRequest)
    }

}