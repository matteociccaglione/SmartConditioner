package it.smarting.smartconditioner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import it.smarting.smartconditioner.adapter.RoomAdapter
import it.smarting.smartconditioner.databinding.ActivityMainBinding
import it.smarting.smartconditioner.decorator.GridSpacingDecorator
import it.smarting.smartconditioner.http.HttpSingleton
import it.smarting.smartconditioner.model.Group
import it.smarting.smartconditioner.model.User
import it.smarting.smartconditioner.viewmodel.GroupsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val groupsViewModel : GroupsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()
        setLiveData()
        loadData()
    }

    private fun setUI(){
        binding.rvRooms.invalidate()
        binding.rvRooms.invalidateItemDecorations()
        binding.rvRooms.adapter = null
        binding.rvRooms.layoutManager = null
        val layoutManager: RecyclerView.LayoutManager
        for(i in 0 until binding.rvRooms.itemDecorationCount){
            binding.rvRooms.removeItemDecorationAt(i)
        }
        layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.rvRooms.layoutManager = layoutManager
        if (binding.rvRooms.itemDecorationCount == 0) {
            binding.rvRooms.addItemDecoration(
                GridSpacingDecorator(
                    resources.getDimensionPixelSize(R.dimen.cardView_margin),
                    2
                )
            )
        }
    }

    private fun loadData(){
        val httpSingleton = HttpSingleton.getInstance(this)
        val user = User.getInstance()
        httpSingleton.getAllGroups(user.username, user.key, groupsViewModel, this)
    }

    private fun setLiveData(){
        val groupObserver = Observer<List<Group>>{
            setUI()
            binding.rvRooms.adapter = RoomAdapter(it, this)
            if (it.isEmpty())
                binding.tvNoRooms.visibility = View.VISIBLE
            else
                binding.tvNoRooms.visibility = View.GONE
        }

        groupsViewModel.groupList.observe(this, groupObserver)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}