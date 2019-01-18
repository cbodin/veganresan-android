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
import kotlinx.android.synthetic.main.fragment_add_meal.meal_image
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.utils.GlideApp
import java.io.File

class AddMealFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        meal_image.setOnClickListener {
            captureImage()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            GlideApp.with(this)
                .load(getCachedImageFile())
                .into(meal_image)
        }
    }

    private fun getCachedImageFile(): File {
        val imagePath = File(context!!.cacheDir, "uploads")
        imagePath.mkdirs()
        val newFile = File(imagePath, "image.jpg")
        newFile.createNewFile()

        return newFile
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }
}
