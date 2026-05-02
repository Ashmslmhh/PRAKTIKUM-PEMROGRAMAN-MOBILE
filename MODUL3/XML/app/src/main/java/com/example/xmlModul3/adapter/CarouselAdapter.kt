package com.example.xmlModul3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animexml.model.Anime
import com.example.xmlModul3.databinding.ItemCarouselBinding

class CarouselAdapter(private val animeList: List<Anime>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    override fun getItemCount() = Int.MAX_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = ItemCarouselBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(animeList[position % animeList.size])
    }

    inner class CarouselViewHolder(private val binding: ItemCarouselBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: Anime) {
            binding.textCarouselTitle.text = anime.title
            binding.textCarouselPremiered.text = anime.premiered
            Glide.with(binding.root)
                .load(anime.imageBg)
                .centerCrop()
                .into(binding.imageCarousel)
        }
    }
}