package com.uilover.project2402.ViewModel

import android.R
import androidx.lifecycle.LiveData
import com.uilover.project2402.Domain.CategoryModel
import com.uilover.project2402.Domain.FoodModel
import com.uilover.project2402.Repository.MainRepository

class MainViewModel {
    private val repository = MainRepository()

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadBestFood(): LiveData<MutableList<FoodModel>> {
        return repository.loadBestFood()
    }

    fun loadFiltered(id: String): LiveData<MutableList<FoodModel>>{
        return repository.loadFiltered(id)
    }
}