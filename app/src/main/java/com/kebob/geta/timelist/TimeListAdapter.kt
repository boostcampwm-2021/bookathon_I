package com.kebob.geta.timelist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kebob.geta.R
import com.kebob.geta.Meal

class TimeListAdapter(private var dataSet: List<Meal>)
    : RecyclerView.Adapter<TimeListAdapter.ViewHolder>() {

    class ViewHolder(view: View, listener: OnItemLongClickListener) : RecyclerView.ViewHolder(view) {
        val tvMeal: TextView = view.findViewById(R.id.tv_meal)
        val tvTime: TextView = view.findViewById(R.id.tv_time_range)

        init {
            view.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemLongClick(view, position)
                }

                false
            }
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    private lateinit var onItemLongClickListener: OnItemLongClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_registered_meal_list, viewGroup, false)

        return ViewHolder(view, onItemLongClickListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMeal.text = dataSet[position].mealType
        holder.tvTime.text = "${dataSet[position].startTime} ~ ${dataSet[position].endTime}"
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
