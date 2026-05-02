package com.example.xmlModul3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.xmlModul3.R
import com.example.xmlModul3.databinding.ItemCarouselWrapperBinding

class CarouselWrapperAdapter(
    private val carouselAdapter: CarouselAdapter,
    private val itemCount: Int,
    private val onPageChanged: (Int) -> Unit
) : RecyclerView.Adapter<CarouselWrapperAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCarouselWrapperBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

    inner class ViewHolder(private val binding: ItemCarouselWrapperBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.viewPager.adapter = carouselAdapter
            setupDots()

            binding.viewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    onPageChanged(position)
                    updateDots(position % itemCount)
                }
            })
        }

        private fun setupDots() {
            binding.dotIndicator.removeAllViews()
            repeat(itemCount) { index ->
                val dot = ImageView(binding.root.context).apply {
                    setImageResource(
                        if (index == 0) R.drawable.dot_selected
                        else R.drawable.dot_unselected
                    )
                    val params = ViewGroup.MarginLayoutParams(16, 16)
                    params.setMargins(8, 0, 8, 0)
                    layoutParams = params
                }
                binding.dotIndicator.addView(dot)
            }
        }

        private fun updateDots(selectedIndex: Int) {
            for (i in 0 until binding.dotIndicator.childCount) {
                val dot = binding.dotIndicator.getChildAt(i) as ImageView
                dot.setImageResource(
                    if (i == selectedIndex) R.drawable.dot_selected
                    else R.drawable.dot_unselected
                )
            }
        }
    }
}