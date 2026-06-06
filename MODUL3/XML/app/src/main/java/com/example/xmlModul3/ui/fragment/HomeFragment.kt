package com.example.xmlModul3.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xmlModul3.AnimeViewModel
import com.example.xmlModul3.R
import com.example.xmlModul3.adapter.AnimeAdapter
import com.example.xmlModul3.adapter.CarouselAdapter
import com.example.xmlModul3.adapter.CarouselWrapperAdapter
import com.example.xmlModul3.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.app_name)
        binding.toolbar.inflateMenu(R.menu.menu_home)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_language -> {
                    findNavController().navigate(R.id.action_home_to_setting)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        val carouselAdapter = CarouselAdapter(viewModel.animeList.value)

        val animeAdapter = AnimeAdapter(viewModel.animeList.value) { anime ->
            val bundle = Bundle().apply {
                putString("title", anime.title)
            }
            findNavController().navigate(R.id.action_home_to_detail, bundle)
        }

        val concatAdapter = ConcatAdapter(
            CarouselWrapperAdapter(
                carouselAdapter = carouselAdapter,
                itemCount = viewModel.animeList.value.size,
                onPageChanged = { _: Int -> }
            ),
            animeAdapter
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = concatAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}