package com.example.xmlModul3.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animexml.model.Anime
import com.example.xmlModul3.databinding.ItemAnimeBinding

class AnimeAdapter(
    private val animeList: List<Anime>,
    private val onDetailClick: (Anime) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    override fun getItemCount() = animeList.size

    inner class AnimeViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: Anime) {
            binding.textTitle.text = anime.title
            binding.textPremiered.text = anime.premiered
            binding.textDescription.text = itemView.context.getString(anime.description)

            Glide.with(binding.root)
                .load(anime.image)
                .centerCrop()
                .into(binding.imageAnime)

            binding.buttonWatch.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(anime.webUrl)
                    setPackage("com.crunchyroll.crunchyroid")
                }
                if (intent.resolveActivity(itemView.context.packageManager) != null) {
                    itemView.context.startActivity(intent)
                } else {
                    val fallback = Intent(Intent.ACTION_VIEW, Uri.parse(anime.webUrl))
                    itemView.context.startActivity(fallback)
                }
            }

            binding.buttonDetail.setOnClickListener {
                onDetailClick(anime)
            }
        }
    }
}