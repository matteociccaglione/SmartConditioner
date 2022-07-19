package it.smarting.smartconditioner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.smarting.smartconditioner.databinding.ActivityRoomSpecBinding

class RoomSpecActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomSpecBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomSpecBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}