package com.kebob.geta

import android.graphics.ColorSpace
import android.graphics.drawable.shapes.Shape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kebob.geta.databinding.ItemMealBinding
import java.lang.RuntimeException

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {
    private lateinit var mealList: List<Meal>
    private lateinit var onItemClickListener: OnItemClickListener

    inner class MealViewHolder(private val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            binding.tvMealTitle.text = meal.title
            binding.tvMealTime.text = meal.time
            binding.tvMealPerson.text = meal.person
            // TODO: Glide 사용해서 image도 불러오기

            binding.layoutItemMeal.setOnClickListener {
                onItemClickListener.onItemClick(it, position)
            }
            when (meal.type) {
                Meal.CHECKED -> {
                    binding.layoutItemMeal.setBackgroundResource(R.drawable.bg_item_meal_checked)
                    binding.tvMealPerson.visibility = View.VISIBLE
                    binding.tvMealTime.visibility = View.VISIBLE
                    binding.ivMealProfile.visibility = View.VISIBLE
                    // binding.tvMealDay.visibility = View.VISIBLE
                }
                Meal.UNCHECKED -> {
                    binding.layoutItemMeal.setBackgroundResource(R.drawable.bg_item_meal_unchecked)
                    binding.tvMealPerson.visibility = View.GONE
                    binding.tvMealTime.visibility = View.GONE
                    binding.tvMealDay.visibility = View.GONE
                    binding.ivMealProfile.visibility = View.GONE
                }
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

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}
