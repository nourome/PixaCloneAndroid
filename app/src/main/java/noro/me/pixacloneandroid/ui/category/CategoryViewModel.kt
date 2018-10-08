package noro.me.pixacloneandroid.ui.category

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import noro.me.pixacloneandroid.model.PhotosCategory
import android.app.Application

class CategoryViewModel(private val app: Application) : AndroidViewModel(app) {


    val size = PhotosCategory.values().size


    var cells: List<Pair<String, Int>> = listOf()
        get() {
            return PhotosCategory.values().map { Pair(app.resources.getString(it.value).capitalize(), it.image) }.sortedBy { it.first }
        }


    fun selectedCategory(cellName: String): String? {

        val category = PhotosCategory.values().filter { app.resources.getString(it.value) == cellName }
        if (category.isNotEmpty())
            return category.first().category


        return null
    }

}