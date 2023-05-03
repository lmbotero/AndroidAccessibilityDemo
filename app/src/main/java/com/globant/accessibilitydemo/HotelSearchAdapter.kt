package com.globant.accessibilitydemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import com.globant.accessibilitydemo.databinding.ItemSearchHistoryBinding

class HotelSearchAdapter : RecyclerView.Adapter<HotelSearchAdapter.ViewHolder>() {

    private var mHotelSearchHistory: MutableList<HotelSearchItem> = mutableListOf()

    fun addItems(hotelSearchHistory: MutableList<HotelSearchItem>) {
        mHotelSearchHistory = hotelSearchHistory
        notifyItemRangeInserted(0, hotelSearchHistory.size)
    }

    fun removeSearchItemFromHistory(position: Int) {
        mHotelSearchHistory.removeAt(position)
        notifyItemRemoved(position)
    }

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
                //Accessibility services read the hotel name and date from the focused position
                holder.itemView.contentDescription = "Search ${position + 1}: $hotelName on $date"

                itemBinding.textviewHotelName.text = hotelName
                itemBinding.textviewDate.text = date
                itemBinding.imageviewHotel.setImageResource(hotelImage)
            }
        }

        //Add custom accessibility action to remove the selected item same as if user was performed slide to remove
        ViewCompat.setAccessibilityDelegate(
            holder.itemView,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)

                    //Add accessibility action to remove item allowing the CLICK action
                    info.addAction(
                        AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            AccessibilityNodeInfoCompat.ACTION_CLICK,
                            "Remove search item from history"
                        )
                    )

                    info.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS)
                }

                override fun performAccessibilityAction(
                    host: View,
                    action: Int,
                    args: Bundle?
                ): Boolean {
                    //Perform accessibility action to remove item when click action event is executed
                    if (action == AccessibilityNodeInfoCompat.ACTION_CLICK) {
                        removeSearchItemFromHistory(holder.bindingAdapterPosition)
                    }

                    return super.performAccessibilityAction(host, action, args)
                }
            })
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = mHotelSearchHistory.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ViewHolder(val itemBinding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}