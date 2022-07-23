package it.smarting.smartconditioner

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import it.smarting.smartconditioner.databinding.ActivitySplashScreenBinding
import it.smarting.smartconditioner.model.User


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    companion object {
        private const val SPLASH_SCREEN_DURATION = 1800L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set splash screen

        val sharedPrefs = getSharedPreferences("CloudAccount", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", "")
        val key = sharedPrefs.getString("key", "")

        Handler(Looper.getMainLooper()).postDelayed({
            var intent =Intent(this, MainActivity::class.java)
            if (username!!.isEmpty() || key!!.isEmpty()) {
                intent = Intent(this, LoginActivity::class.java)
            } else {
                val user = User.getInstance()
                user.username = username
                user.key = key
            }

            startActivity(intent)
            // to avoid to return to splash screen when pushing the back button
            finish()
        }, SPLASH_SCREEN_DURATION)
    }

}