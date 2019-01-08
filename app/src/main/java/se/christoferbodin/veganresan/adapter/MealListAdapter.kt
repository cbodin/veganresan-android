package se.christoferbodin.veganresan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_meal.view.meal_name
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.model.Meal

class MealListAdapter: RecyclerView.Adapter<MealItemHolder>() {
    var data: List<Meal>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealItemHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: MealItemHolder, position: Int) {
        holder.bind(data!![position])
    }

}

class MealItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val nameView: TextView = itemView.meal_name

    fun bind(meal: Meal) {
        nameView.text = meal.name
    }
}
