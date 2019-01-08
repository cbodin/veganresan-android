package se.christoferbodin.veganresan.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_meal_list.view.meal_list
import kotlinx.android.synthetic.main.fragment_meal_list.view.refresh_layout
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.adapter.MealListAdapter
import se.christoferbodin.veganresan.viewmodel.MealViewModel

class MealListFragment : Fragment() {
    private var mealListAdapter: MealListAdapter = MealListAdapter()
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var model: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProviders.of(this).get(MealViewModel::class.java)
        model.meals(savedInstanceState == null).observe(this, Observer { meals ->
            mealListAdapter.data = meals
            mealListAdapter.notifyDataSetChanged()
        })
        model.mealsError().observe(this, Observer { error ->
            // TODO: Handle error
        })
        model.mealsLoading().observe(this, Observer { loading ->
            refreshLayout.isRefreshing = loading
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meal_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout = view.refresh_layout
        refreshLayout.setOnRefreshListener {
            model.meals(true)
        }

        recyclerView = view.meal_list
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = mealListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}
