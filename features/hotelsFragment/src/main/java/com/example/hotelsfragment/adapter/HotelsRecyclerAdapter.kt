package com.example.hotelsfragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelsfragment.R
import com.example.hotelsfragment.databinding.HotelCardBinding
import com.example.hotelsfragment.model.HotelUi

class HotelsRecyclerAdapter(hotels: List<HotelUi>) :
    RecyclerView.Adapter<HotelsRecyclerAdapter.HotelViewHolder>() {

    var onHotelClicked: (HotelUi) -> Unit = {}
    private val oldList = hotels.toMutableList()

    fun sortHotels(filterByDistance: Boolean = false, filterBySuites: Boolean = false) {
        val sortedList = mutableListOf<HotelUi>()
        if (filterByDistance) {
            sortedList.addAll(oldList.sortedByDescending { it.distance })
        }
        if (filterBySuites) {
            sortedList.addAll(oldList.sortedByDescending { it.suitesAvailability.size })
        }
        val diffResult = DiffUtil.calculateDiff(HotelsDiffUtilCallback(oldList, sortedList))
        oldList.clear()
        oldList.addAll(sortedList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotel_card, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(oldList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = oldList.size

    inner class HotelViewHolder(hotelCard: View) : RecyclerView.ViewHolder(hotelCard) {
        private val binding = HotelCardBinding.bind(hotelCard)
        private val context = hotelCard.context

        fun bind(hotel: HotelUi) {
            binding.root.setOnClickListener {
                onHotelClicked.invoke(hotel)
            }
            binding.hotelName.text = hotel.name
            binding.hotelStars.text = context.getString(R.string.hotel_stars, hotel.stars)
            binding.hotelAddress.text = hotel.address
            binding.hotelDistance.text = context.getString(R.string.hotel_distance, hotel.distance)
            binding.hotelSuitesAvailable.text =
                context.getString(R.string.total_suites_available, hotel.suitesAvailability.size)
        }
    }

    inner class HotelsDiffUtilCallback(
        private val oldList: List<HotelUi>,
        private val sortedList: List<HotelUi>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return sortedList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == sortedList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === sortedList[newItemPosition]
        }
    }
}

