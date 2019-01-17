package se.christoferbodin.veganresan.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_meal_list.meal_list
import kotlinx.android.synthetic.main.fragment_meal_list.meal_list_error
import kotlinx.android.synthetic.main.fragment_meal_list.meal_list_retry
import kotlinx.android.synthetic.main.fragment_meal_list.refresh_layout
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.adapter.MealListAdapter
import se.christoferbodin.veganresan.viewmodel.MealViewModel

class MealListFragment : Fragment() {
    private var mealListAdapter: MealListAdapter = MealListAdapter()
    private lateinit var viewModel: MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MealViewModel::class.java)
        viewModel.getMeals().observe(this, Observer { meals ->
            mealListAdapter.data = meals
            mealListAdapter.notifyDataSetChanged()
        })
        viewModel.isLoading().observe(this, Observer { loading ->
            refresh_layout.isRefreshing = loading
        })
        viewModel.hasError().observe(this, Observer { error ->
            val visibility =  if (error) View.VISIBLE else View.GONE
            meal_list.visibility = visibility
            meal_list_error.visibility = visibility
        })

        viewModel.loadMeals()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meal_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refresh_layout.setOnRefreshListener {
            viewModel.loadMeals(true)
        }

        meal_list_retry.setOnClickListener {
            viewModel.loadMeals(true)
        }

        meal_list.setHasFixedSize(true)
        meal_list.adapter = mealListAdapter
        meal_list.layoutManager = LinearLayoutManager(context)
    }
}
