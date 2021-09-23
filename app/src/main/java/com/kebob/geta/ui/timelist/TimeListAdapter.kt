package com.kebob.geta.ui.timelist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kebob.geta.R
import com.kebob.geta.data.Meal
import com.kebob.geta.databinding.ItemRegisteredMealListBinding
import com.kebob.geta.ui.RegisterActivity
import java.text.SimpleDateFormat
import java.util.*

class TimeListAdapter(var dataSet: List<Meal>) : RecyclerView.Adapter<TimeListAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemRegisteredMealListBinding, listener: OnItemLongClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemLongClick(itemView, position)
                }

                false
            }
        }

        fun bind(meal: Meal) {
            binding.tvMeal.text = meal.mealName
            binding.tvTimeRange.text = "${meal.startTime} ~ ${meal.endTime}"

            binding.textView2.text = when (meal.mealType) {
                RegisterActivity.MealType.Meal.name -> itemView.context.getString(R.string.common_meal)
                RegisterActivity.MealType.Snack.name -> itemView.context.getString(R.string.common_snack)
                else -> ""
            }
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    private lateinit var onItemLongClickListener: OnItemLongClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRegisteredMealListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view, onItemLongClickListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun setOnMyItemLongClickListener(listener: OnItemLongClickListener) {
        this.onItemLongClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataSet(newData: List<Meal>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}
