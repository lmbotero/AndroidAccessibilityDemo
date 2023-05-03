package com.globant.accessibilitydemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globant.accessibilitydemo.databinding.ItemSearchHistoryBinding

class HotelSearchAdapter(private val mHotelSearchHistory: List<HotelSearchItem>) :
    RecyclerView.Adapter<HotelSearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotelSearchAdapter.ViewHolder {
        val binding =
            ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotelSearchAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(mHotelSearchHistory[position]) {
                holder.itemView.tag = position

                //Accessibility services read the hotel name and date from the focused position
                holder.itemView.contentDescription = "Search ${position + 1}: $hotelName on $date"

                itemBinding.textviewHotelName.text = hotelName
                itemBinding.textviewDate.text = date
                itemBinding.imageviewHotel.setImageResource(hotelImage)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = mHotelSearchHistory.size

    override fun getItemId(position: Int): Long {
        return mHotelSearchHistory[position].id.toLong()
    }

    inner class ViewHolder(val itemBinding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}