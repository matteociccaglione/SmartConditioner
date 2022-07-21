package it.smarting.smartconditioner.model

import android.content.Context
import it.smarting.smartconditioner.http.HttpSingleton

class User() {
    lateinit var username : String
    lateinit var key : String

    companion object {

        @Volatile
        private var INSTANCE: User? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: User().also {
                INSTANCE = it
            }
        }
    }
}