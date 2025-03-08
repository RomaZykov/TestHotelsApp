package com.example.hotelcompose

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController

class HotelComposeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.hotel_compose, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.hotel_compose)

        val hotelId = arguments?.getString("hotelId")

        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    HotelComposeScreen(onNavigateBack = {
                        val deeplink =
                            NavDeepLinkRequest.Builder.fromUri(
                                Uri.parse(
                                    getString(com.example.navigation.R.string.hotels_fragment_deep_link)
                                )
                            )
                                .build()
                        findNavController().navigate(deeplink)
                    }, hotelId?.toInt()!!)
                }
            }
            return view
        }
    }
}