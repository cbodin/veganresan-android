package se.christoferbodin.veganresan.fragment

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_meal_list.meal_list
import kotlinx.android.synthetic.main.fragment_meal_list.meal_list_error
import kotlinx.android.synthetic.main.fragment_meal_list.meal_list_retry
import kotlinx.android.synthetic.main.fragment_meal_list.refresh_layout
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.adapter.MealListAdapter
import se.christoferbodin.veganresan.model.Meal
import se.christoferbodin.veganresan.viewmodel.MealsViewModel

class MealListFragment : Fragment() {
    private var mealListAdapter = MealListAdapter(::itemClickListener)
    private lateinit var viewModel: MealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this).get(MealsViewModel::class.java)
        viewModel.getMeals().observe(this, Observer { meals ->
            mealListAdapter.data = meals
            mealListAdapter.notifyDataSetChanged()
        })
        viewModel.isLoading().observe(this, Observer { loading ->
            refresh_layout.isRefreshing = loading
        })
        viewModel.hasError().observe(this, Observer { error ->
            meal_list.visibility = if (error) View.GONE else View.VISIBLE
            meal_list_error.visibility = if (error) View.VISIBLE else View.GONE
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_upload) {
            uploadMeal()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun uploadMeal() {
        val password = PreferenceManager.getDefaultSharedPreferences(context)
            .getString("UPLOAD_PASSWORD", null)

        if (password == null) {
            DialogLoginFragment.newInstance().show(fragmentManager, null)
        } else {
            findNavController().navigate(R.id.action_add_meal)
        }
    }

    private fun itemClickListener(meal: Meal) {
        val direction = MealListFragmentDirections.actionMealDetail()
        direction.meal = meal

        findNavController().navigate(direction)
    }
}
