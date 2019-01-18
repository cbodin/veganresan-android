package se.christoferbodin.veganresan.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_meal.view.meal_date
import kotlinx.android.synthetic.main.item_meal.view.meal_image
import kotlinx.android.synthetic.main.item_meal.view.meal_name
import kotlinx.android.synthetic.main.item_meal.view.meal_time
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.model.Meal
import se.christoferbodin.veganresan.utils.GlideApp

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
        holder.bind(data!![position], itemCount - position)
    }

}

class MealItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView = itemView.meal_image
    private val nameView = itemView.meal_name
    private val dateView = itemView.meal_date
    private val timeView = itemView.meal_time

    fun bind(meal: Meal, position: Int) {
        GlideApp.with(imageView)
            .load(meal.photo)
            .transition(transition)
            .error(R.drawable.ic_image_broken)
            .centerCrop()
            .into(imageView)

        nameView.text = meal.name
        dateView.text = DateFormat.getMediumDateFormat(itemView.context).format(meal.published)
        timeView.text = DateFormat.getTimeFormat(itemView.context).format(meal.published)
    }

    companion object {
        private val transition = DrawableTransitionOptions.withCrossFade()
    }
}
