package se.christoferbodin.veganresan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.model.Link

class MealLinksAdapter(val clickListener: (Link) -> Unit) : RecyclerView.Adapter<MealLinksAdapter.LinkItemHolder>() {
    var data: List<Link>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_link, parent, false)
        return LinkItemHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: LinkItemHolder, position: Int) {
        holder.bind(data!![position])
    }

    inner class LinkItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView as TextView
        private var link: Link? = null

        init {
            itemView.setOnClickListener {
                clickListener(link!!)
            }
        }

        fun bind(link: Link) {
            this.link = link
            textView.text = link.title
        }
    }
}
