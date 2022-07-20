package it.smarting.smartconditioner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.smarting.smartconditioner.databinding.ActivityLoginBinding

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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // to avoid to return to login activity when pushing the back button
            finish()
        }
    }
}