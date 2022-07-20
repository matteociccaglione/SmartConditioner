package it.smarting.smartconditioner

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import it.smarting.smartconditioner.databinding.ActivitySplashScreenBinding

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
        /*
        TODO:
         for now let's go to the main activity after the splash screen. In the future, let's check if
         the login has been already done: if so, go to the MainActivity, else, go to the LoginActivity
         */
        Handler(Looper.getMainLooper()).postDelayed({
            val intent =Intent(this, MainActivity::class.java)
            startActivity(intent)
            // to avoid to return to splash screen when pushing the back button
            finish()
        }, SPLASH_SCREEN_DURATION)
    }

}