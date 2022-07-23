package it.smarting.smartconditioner

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import it.smarting.smartconditioner.databinding.ActivityRoomSpecBinding
import it.smarting.smartconditioner.dialog.RenameRoomDialog
import it.smarting.smartconditioner.http.HttpSingleton
import it.smarting.smartconditioner.model.Group
import it.smarting.smartconditioner.model.User
import it.smarting.smartconditioner.viewmodel.SingleGroupViewModel
import java.util.*
import kotlin.math.roundToInt
import kotlin.system.exitProcess

class RoomSpecActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomSpecBinding
    private var group = Group(Collections.emptyList(), "")
    private val groupViewModel : SingleGroupViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomSpecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val groupKey = intent.getStringExtra("GroupKey") ?: exitProcess(-1)
        setUI()
        setLiveData()
        loadData(groupKey)
    }

    private fun setUI(){

        if (group.feeds.isEmpty() || group.key.isEmpty())
            return

        for (feed in group.feeds) {
            if (feed.key.endsWith("room")) {
                binding.tvTitleSpec.text = feed.last_value
                binding.ibEdit.setOnClickListener{
                    val dialog = RenameRoomDialog.getInstance()
                    dialog.setOnOkPressed {
                        val httpSingleton = HttpSingleton.getInstance(this)
                        val user = User.getInstance()
                        val value = it
                        httpSingleton.updateSingleFeed(user.username, user.key, feed.key, value)
                        binding.tvTitleSpec.text = value
                    }
                    dialog.show(supportFragmentManager,"DIALOG")
                }
            }
            else if (feed.key.endsWith("sensedtemperature")) {
                val temp = feed.last_value.toFloat()
                binding.tvTemp2.text = "%d°C".format(temp.roundToInt())
            } else if (feed.key.endsWith("conditionerstatus")) {
                val status = feed.last_value
                binding.swOnOff.isChecked = status.equals("On")
                binding.swOnOff.setOnCheckedChangeListener { _, _ ->
                    val httpSingleton = HttpSingleton.getInstance(this)
                    val user = User.getInstance()
                    val value = if (binding.swOnOff.isChecked) "On" else "Off"
                    httpSingleton.updateSingleFeed(user.username, user.key, feed.key, value)
                }
            }else if (feed.key.endsWith("conditionertemperature")){
                val temp = feed.last_value.toFloat()
                binding.tvTemp3.text = "%d°C".format(temp.roundToInt())
                binding.button.setOnClickListener{
                    val httpSingleton = HttpSingleton.getInstance(this)
                    val user = User.getInstance()
                    val value = binding.editTextNumber.text.toString()
                    httpSingleton.updateSingleFeed(user.username,user.key,feed.key,value)
                    binding.tvTemp3.text = "%s°C".format(value)
                }
            }
        }

    }

    private fun setLiveData(){
        val observer = Observer<Group>{
            group = it
            setUI()
        }
        groupViewModel.group.observe(this, observer)
    }

    private fun loadData(groupKey : String){
        val httpSingleton = HttpSingleton.getInstance(this)
        val user = User.getInstance()
        httpSingleton.getSingleGroup(user.username, user.key, groupKey, groupViewModel)
    }
}