package se.christoferbodin.veganresan.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_meal_detail.links_recycler
import kotlinx.android.synthetic.main.fragment_meal_detail.meal_date
import kotlinx.android.synthetic.main.fragment_meal_detail.meal_image
import kotlinx.android.synthetic.main.fragment_meal_detail.meal_time
import kotlinx.android.synthetic.main.fragment_meal_detail.meal_title
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.adapter.MealLinksAdapter
import se.christoferbodin.veganresan.model.Link
import se.christoferbodin.veganresan.utils.GlideApp
import se.christoferbodin.veganresan.utils.ImageProgressDrawable

class MealDetailFragment: Fragment() {
    private var mealLinksAdapter = MealLinksAdapter(::linkClickListener)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meal_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val meal = MealDetailFragmentArgs.fromBundle(arguments!!).meal

        links_recycler.layoutManager = LinearLayoutManager(context)
        links_recycler.adapter = mealLinksAdapter
        mealLinksAdapter.data = meal?.links

        meal_title.text = meal?.name
        meal_date.text = DateFormat.getMediumDateFormat(context).format(meal?.published)
        meal_time.text = DateFormat.getTimeFormat(context).format(meal?.published)

        val progress = ImageProgressDrawable(context!!)
        progress.start()

        GlideApp.with(context!!)
            .load(meal?.photo)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_image_broken)
            .placeholder(progress)
            .centerCrop()
            .into(meal_image)
    }

    private fun linkClickListener(link: Link) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
        startActivity(intent)
    }
}
