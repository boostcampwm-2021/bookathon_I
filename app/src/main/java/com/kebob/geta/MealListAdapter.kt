package com.kebob.geta

import android.graphics.ColorSpace
import android.graphics.drawable.shapes.Shape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kebob.geta.databinding.ItemMealCheckedBinding
import com.kebob.geta.databinding.ItemMealUncheckedBinding
import java.lang.RuntimeException

class MealListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var mealList: List<Meal>
    private lateinit var onItemClickListener: OnItemClickListener

    inner class MealCheckedViewHolder(private val binding: ItemMealCheckedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.tvMealCheckedTitle.text = meal.title
            binding.tvItemCheckedTime.text = meal.time
            binding.tvMealCheckedPerson.text = meal.person
            // Glide 사용해서 image도 불러오기

            binding.layoutItemMealChecked.setOnClickListener {
                onItemClickListener.onItemClick(it, position)
            }
        }
    }

    inner class MealUncheckedViewHolder(private val binding: ItemMealUncheckedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: Meal) {
            binding.tvMealUncheckedTitle.text = meal.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Meal.CHECKED -> {
                val binding = ItemMealCheckedBinding.inflate(layoutInflater, parent, false)
                return MealCheckedViewHolder(binding)
            }
            Meal.UNCHECKED -> {
                val binding = ItemMealUncheckedBinding.inflate(layoutInflater, parent, false)
                return MealUncheckedViewHolder(binding)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (mealList[position].type) {
            Meal.CHECKED -> {
                (holder as MealListAdapter.MealCheckedViewHolder).bind(mealList[position])
                holder.setIsRecyclable(false)
            }
            Meal.UNCHECKED -> {
                (holder as MealUncheckedViewHolder).bind(mealList[position])
                holder.setIsRecyclable(false)
            }
        }
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