package jmapps.arabicinyourhands.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainChaptersViewModel : ViewModel() {

    private val currentIndex = MutableLiveData<Int?>()

    fun setIndex(index: Int) {
        currentIndex.value = index
    }

    private val currentPicture = mutableListOf(
        "first_icon",
        "second_icon",
        "third_icon"
    )

    private val currentText = mutableListOf(
        "Легендарный учебник в виде андроид-приложения",
        "Аудиосопровождение",
        "Новые возможности"
    )

    val pictures: LiveData<String> = Transformations.map(currentIndex) {
        currentPicture[currentIndex.value!! - 1]
    }

    val texts: LiveData<String> = Transformations.map(currentIndex) {
        currentText[currentIndex.value!! - 1]
    }
}