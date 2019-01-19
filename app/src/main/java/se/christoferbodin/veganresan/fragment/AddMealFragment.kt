package se.christoferbodin.veganresan.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_add_meal.meal_image
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.utils.GlideApp
import java.io.File

class AddMealFragment : Fragment() {
    private var imageUri: File? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedInstanceState?.apply {
            imageUri = getSerializable(KEY_IMAGE_FILE) as File?
            loadImage()
        }

        meal_image.setOnClickListener {
            captureImage()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_IMAGE_FILE, imageUri)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageUri = getCachedImageFile()
            loadImage()
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
        if (imageUri != null) {
            GlideApp.with(this)
                .load(imageUri)
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
