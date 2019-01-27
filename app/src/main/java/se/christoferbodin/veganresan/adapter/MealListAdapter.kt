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
import kotlinx.android.synthetic.main.item_meal.view.meal_number
import kotlinx.android.synthetic.main.item_meal.view.meal_time
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.model.Meal
import se.christoferbodin.veganresan.utils.GlideApp
import se.christoferbodin.veganresan.utils.ImageProgressDrawable

class MealListAdapter(val clickListener: (Meal) -> Unit) : RecyclerView.Adapter<MealListAdapter.MealItemHolder>() {
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

    inner class MealItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.meal_image
        private val numberView = itemView.meal_number
        private val nameView = itemView.meal_name
        private val dateView = itemView.meal_date
        private val timeView = itemView.meal_time
        private var meal: Meal? = null
        private val transition = DrawableTransitionOptions.withCrossFade()

        init {
            itemView.setOnClickListener {
                clickListener(meal!!)
            }
        }

        fun bind(meal: Meal, position: Int) {
            this.meal = meal

            val progress = ImageProgressDrawable(itemView.context)
            progress.start()

            GlideApp.with(imageView)
                .load(meal.photo)
                .transition(transition)
                .error(R.drawable.ic_image_broken)
                .placeholder(progress)
                .centerCrop()
                .into(imageView)

            nameView.text = meal.name
            numberView.text = String.format("%d / %d", position, 32)
            dateView.text = DateFormat.getMediumDateFormat(itemView.context).format(meal.published)
            timeView.text = DateFormat.getTimeFormat(itemView.context).format(meal.published)
        }
    }
}
