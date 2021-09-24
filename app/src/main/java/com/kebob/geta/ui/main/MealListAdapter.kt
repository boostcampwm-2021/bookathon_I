package com.kebob.geta.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kebob.geta.R
import com.kebob.geta.data.Meal
import com.kebob.geta.databinding.ItemMealBinding
import com.kebob.geta.ui.RegisterActivity
import java.text.SimpleDateFormat
import java.util.*

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {
    private lateinit var mealList: List<Meal>
    private lateinit var userMap: Map<String, String>
    private lateinit var onItemClickListener: OnItemClickListener

    inner class MealViewHolder(private val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            binding.tvMealTitle.text = meal.mealName
            binding.tvMealTime.text = meal.time
            binding.tvMealPerson.text = meal.person
            Glide.with(itemView.context)
                .load(userMap[meal.person])
                .circleCrop()
                .into(binding.ivMealProfile)

            binding.layoutItemMeal.setOnClickListener {
                onItemClickListener.onItemClick(it, adapterPosition)
            }

            var completeString = ""
            when (meal.person) {
                "" -> {
                    binding.layoutItemMeal.setBackgroundResource(R.drawable.bg_item_meal_unchecked)
                    binding.tvMealPerson.visibility = View.GONE
                    binding.ivMealProfile.visibility = View.GONE
                    binding.tvMealTime.visibility = View.GONE
                    binding.tvMealNoneTime.visibility = View.VISIBLE
                    val format = SimpleDateFormat("HH:mm")
                    val currentTime = Date(System.currentTimeMillis())
                    val distanceTime = format.parse(meal.endTime)

                    binding.tvMealNoneTime.text = if (distanceTime.hours > currentTime.hours) {
                        val hour = distanceTime.hours - currentTime.hours
                        "${hour}시간 전"
                    } else {
                        "배식시간 지남"
                    }
                }
                else -> {
                    binding.layoutItemMeal.setBackgroundResource(R.drawable.bg_item_meal_checked)
                    binding.tvMealPerson.visibility = View.VISIBLE
                    binding.tvMealTime.visibility = View.VISIBLE
                    binding.ivMealProfile.visibility = View.VISIBLE
                    binding.tvMealNoneTime.visibility = View.GONE
                    completeString = itemView.context.getString(R.string.common_complete)
                }
            }

            binding.textView2.text = when (meal.mealType) {
                RegisterActivity.MealType.Meal.name -> itemView.context.getString(R.string.common_meal) + completeString
                RegisterActivity.MealType.Snack.name -> itemView.context.getString(R.string.common_snack) + completeString
                RegisterActivity.MealType.Medicine.name -> itemView.context.getString(R.string.common_medicine) + completeString
                else -> ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMealBinding.inflate(layoutInflater, parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(mealList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    fun updateList(newData: List<Meal>) {
        mealList = newData
        notifyDataSetChanged()
    }

    fun updateUserMap(newData: Map<String, String>) {
        userMap = newData
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}
