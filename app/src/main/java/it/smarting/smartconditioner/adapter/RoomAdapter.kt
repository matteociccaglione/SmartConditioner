package it.smarting.smartconditioner.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import it.smarting.smartconditioner.R
import it.smarting.smartconditioner.RoomSpecActivity
import it.smarting.smartconditioner.databinding.RoomItemBinding
import it.smarting.smartconditioner.model.Group
import java.util.*
import kotlin.math.roundToInt

class RoomAdapter(val groupList: List<Group>, val context: Context) : RecyclerView.Adapter<RoomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RoomItemBinding.inflate(layoutInflater)
        return RoomViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.group = groupList[position]
        holder.binding.root.setOnClickListener {
            val intent = Intent(context, RoomSpecActivity::class.java)
            intent.putExtra("GroupKey", holder.group.key)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

}

class RoomViewHolder(var binding: RoomItemBinding, var context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    var group = Group(Collections.emptyList(), "")
        set(value) {
            field = value
            for (feed in value.feeds) {
                if (feed.key.endsWith("room"))
                    binding.tvTitle.text = feed.last_value
                else if (feed.key.endsWith("sensedtemperature")) {
                    val temp = feed.last_value.toFloat()
                    binding.tvTemperature.text = "%d°C".format(temp.roundToInt())
                } else if (feed.key.endsWith("conditionerstatus")) {
                    val status = feed.last_value
                    binding.tvOnOff.text = status
                    if (status.equals("On")) {
                        binding.ivOnOff.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_baseline_check_24
                            )
                        )
                    } else {
                        binding.ivOnOff.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_baseline_close_24
                            )
                        )
                    }
                }
            }
        }
}