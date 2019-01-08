package se.christoferbodin.veganresan.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_meal.view.meal_date
import kotlinx.android.synthetic.main.item_meal.view.meal_image
import kotlinx.android.synthetic.main.item_meal.view.meal_name
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.model.Meal

class MealListAdapter : RecyclerView.Adapter<MealItemHolder>() {
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

class MealItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.meal_image
    private val nameView: TextView = itemView.meal_name
    private val dateView: TextView = itemView.meal_date

    fun bind(meal: Meal) {
        Glide.with(imageView)
            .load(meal.photo)
            .into(imageView)

        val date = DateFormat.getLongDateFormat(itemView.context).format(meal.published)
        val time = DateFormat.getTimeFormat(itemView.context).format(meal.published)

        nameView.text = meal.name
        dateView.text = "$date, $time"
    }
}
