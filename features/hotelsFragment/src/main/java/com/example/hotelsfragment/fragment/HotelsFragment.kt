package com.example.hotelsfragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelsfragment.adapter.HotelsRecyclerAdapter
import com.example.hotelsfragment.databinding.FragmentHotelsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotelsFragment : Fragment() {

    private var _binding: FragmentHotelsBinding? = null
    private val binding get() = _binding!!

    private lateinit var hotelsAdapter: HotelsRecyclerAdapter
    private val viewModel by viewModels<HotelsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            )
                .collect {
                    when (it) {
                        is HotelsUiState.FirstLoad -> {
                            binding.rvHotelsList.visibility = View.GONE
                            binding.errorView.visibility = View.GONE
                            binding.loadingView.loadingScreen.visibility = View.VISIBLE
                            viewModel.fetchHotels()
                        }

                        is HotelsUiState.Content -> {
                            binding.rvHotelsList.visibility = View.VISIBLE
                            binding.loadingView.loadingScreen.visibility = View.GONE
                            binding.errorView.visibility = View.GONE

                            val hotelsRecyclerView: RecyclerView = binding.rvHotelsList
                            hotelsRecyclerView.layoutManager =
                                LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            hotelsAdapter = HotelsRecyclerAdapter(it.hotels)
                            hotelsRecyclerView.adapter = hotelsAdapter

                            hotelsAdapter.onHotelClicked = { hotel ->
                                val deeplink =
                                    NavDeepLinkRequest.Builder.fromUri(
                                        getString(com.example.navigation.R.string.hotel_compose_deep_link).replace(
                                            "{hotelId}",
                                            hotel.id.toString()
                                        ).toUri()
                                    )
                                        .build()
                                findNavController().navigate(deeplink)
                            }
                        }

                        is HotelsUiState.Error -> {
                            binding.loadingView.loadingScreen.visibility = View.GONE
                            binding.rvHotelsList.visibility = View.GONE
                            binding.errorView.visibility = View.VISIBLE
                            binding.errorText.text = it.message

                            binding.refreshButton.setOnClickListener {
                                viewModel.fetchHotels()
                            }
                        }
                    }
                }
        }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            "filterOptionsRequestKey",
            this
        ) { _, bundle ->
            if (this::hotelsAdapter.isInitialized) {
                hotelsAdapter.sortHotels(
                    filterByDistance = bundle.getBoolean("filterByDistance"),
                    filterBySuites = bundle.getBoolean("filterBySuitesAvailability")
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}