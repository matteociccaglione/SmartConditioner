package it.smarting.smartconditioner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import it.smarting.smartconditioner.databinding.ActivityMainBinding
import it.smarting.smartconditioner.decorator.GridSpacingDecorator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
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
}