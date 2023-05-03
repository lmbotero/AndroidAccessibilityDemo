package com.globant.accessibilitydemo

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globant.accessibilitydemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mHotelSearchHistory = mutableListOf(
        HotelSearchItem(R.drawable.hotel, "Grand Plaza Hotel", "2023/01/31"),
        HotelSearchItem(R.drawable.hotel, "Park Avenue Hotel", "2023/02/14"),
        HotelSearchItem(R.drawable.hotel, "Sunset Beach Hotel", "2023/03/09"),
        HotelSearchItem(R.drawable.hotel, "Regency Inn", "2023/04/06"),
        HotelSearchItem(R.drawable.hotel, "Mountain View Lodge", "2023/05/01"),
        HotelSearchItem(R.drawable.hotel, "Seaside Resort", "2023/05/20")
    )

    private lateinit var mMoodViews: List<ImageView>
    lateinit var adapterSearchHistory: HotelSearchAdapter

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMoodViews = listOf(
            binding.imageviewMoodSad,
            binding.imageviewMoodFlat,
            binding.imageviewMoodSmile,
            binding.imageviewMoodLaugh
        )

        setUpViews()

        setUpAccessibilityOptions()

        setUpListeners()
    }

    private fun setUpViews() {
        adapterSearchHistory = HotelSearchAdapter()
        binding.recyclerviewSearchHistory.adapter = adapterSearchHistory
        binding.recyclerviewSearchHistory.layoutManager = LinearLayoutManager(this)

        adapterSearchHistory.addItems(mHotelSearchHistory)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                adapterSearchHistory.removeSearchItemFromHistory(position)
            }
        }).attachToRecyclerView(binding.recyclerviewSearchHistory)
    }

    private fun setUpListeners() {
        binding.imageviewMoodSad.setOnClickListener {
            binding.imageviewMoodSad.changeMoodImage(this, true)
        }

        binding.imageviewMoodFlat.setOnClickListener {
            binding.imageviewMoodFlat.changeMoodImage(this, true)
        }

        binding.imageviewMoodSmile.setOnClickListener {
            binding.imageviewMoodSmile.changeMoodImage(this, true)
        }

        binding.imageviewMoodLaugh.setOnClickListener {
            binding.imageviewMoodLaugh.changeMoodImage(this, true)
        }
    }

    private fun setUpAccessibilityOptions() {
        ViewCompat.setAccessibilityDelegate(
            binding.imageviewMoodSad,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    //Change the ImageView role to Button, so the accessibility services reads it as Button
                    //Not as image because its behavior is like a button in the App.
                    info.roleDescription = "Button"

                    //When accessibility services has the focus on the ImageView, it will read their action
                    info.addAction(
                        AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            ACTION_CLICK, "Mark as poor"
                        )
                    )
                }
            })

        ViewCompat.setAccessibilityDelegate(
            binding.imageviewMoodFlat,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    //Change the ImageView role to Button, so the accessibility services reads it as Button
                    //Not as image because its behavior is like a button in the App.
                    info.roleDescription = "Button"

                    //When accessibility services has the focus on the ImageView, it will read their action
                    info.addAction(
                        AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            ACTION_CLICK, "Mark as average"
                        )
                    )
                }
            })

        ViewCompat.setAccessibilityDelegate(
            binding.imageviewMoodSmile,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    //Change the ImageView role to Button, so the accessibility services reads it as Button
                    //Not as image because its behavior is like a button in the App.
                    info.roleDescription = "Button"

                    //When accessibility services has the focus on the ImageView, it will read their action
                    info.addAction(
                        AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            ACTION_CLICK, "Mark as good"
                        )
                    )
                }
            })

        ViewCompat.setAccessibilityDelegate(
            binding.imageviewMoodLaugh,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    //Change the ImageView role to Button, so the accessibility services reads it as Button
                    //Not as image because its behavior is like a button in the App.
                    info.roleDescription = "Button"

                    //When accessibility services has the focus on the ImageView, it will read their action
                    info.addAction(
                        AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            ACTION_CLICK, "Mark as excellent"
                        )
                    )
                }
            })
    }

    private fun ImageView.changeMoodImage(context: Context, isSelected: Boolean) {
        mMoodViews.forEach {
            it.imageTintList = if (it == this && isSelected) {
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.mood_selected))
            } else {
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.mood_unselected))
            }
        }
    }
}