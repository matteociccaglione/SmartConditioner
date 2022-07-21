package it.smarting.smartconditioner

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import it.smarting.smartconditioner.databinding.ActivityLoginBinding
import it.smarting.smartconditioner.model.User

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()
    }

    private fun setUI(){
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val key = binding.etAPIKey.text.toString()
            if (username.isEmpty() || key.isEmpty()) {
                Toast.makeText(this, "Username and key can not be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val user = User.getInstance()
            user.username = username
            user.key = key

            // save login in cache, for future use
            val sharedPrefs = this.getSharedPreferences("CloudAccount", Activity.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("username", username)
            editor.putString("key", key)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // to avoid to return to login activity when pushing the back button
            finish()
        }
    }
}