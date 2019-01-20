package se.christoferbodin.veganresan.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_add_meal.btn_save
import kotlinx.android.synthetic.main.fragment_add_meal.meal_error
import kotlinx.android.synthetic.main.fragment_add_meal.meal_image
import kotlinx.android.synthetic.main.fragment_add_meal.meal_title_input
import kotlinx.android.synthetic.main.fragment_add_meal.progress_circular
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.utils.GlideApp
import se.christoferbodin.veganresan.viewmodel.EditMealViewModel
import java.io.File

class AddMealFragment : Fragment() {
    private lateinit var viewModel: EditMealViewModel
    private var imageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(EditMealViewModel::class.java)
        viewModel.isLoading().observe(this, Observer { loading ->
            btn_save.isEnabled = !loading
            meal_title_input.isEnabled = !loading
            meal_image.isEnabled = !loading
            progress_circular.visibility = if (loading) View.VISIBLE else View.GONE
        })
        viewModel.hasError().observe(this, Observer { error ->
            meal_error.visibility = if (error) View.VISIBLE else View.GONE
        })
        viewModel.onSave().observe(this, Observer {
            Toast.makeText(context, R.string.meal_uploaded, Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedInstanceState?.apply {
            imageFile = getSerializable(KEY_IMAGE_FILE) as File?
            loadImage()
        }

        meal_image.setOnClickListener {
            captureImage()
        }

        btn_save.setOnClickListener {
            uploadMeal()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_IMAGE_FILE, imageFile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageFile = getCachedImageFile()
            loadImage()
        }
    }

    private fun uploadMeal() {
        val name = meal_title_input.text.toString().trim()
        val image = imageFile

        if (name.isNotEmpty() && image != null) {
            viewModel.uploadMeal(name, image)
        }
    }

    private fun captureImage() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context!!.packageManager)?.also {
                val photoPath = getCachedImageFile()
                val photoUri = getUriForFile(context!!, "se.christoferbodin.fileprovider", photoPath)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun getCachedImageFile(): File {
        val imagePath = File(context!!.cacheDir, "uploads")
        imagePath.mkdirs()
        val newFile = File(imagePath, "image.jpg")
        newFile.createNewFile()

        return newFile
    }

    private fun loadImage() {
        if (imageFile != null) {
            GlideApp.with(this)
                .load(imageFile)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(meal_image)
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val KEY_IMAGE_FILE = "IMAGE_FILE"
    }
}
