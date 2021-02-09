package jmapps.arabicinyourhands.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.room.model.categories.WordCategories
import jmapps.arabicinyourhands.databinding.BottomsheetAddWordCategoryBinding
import jmapps.arabicinyourhands.ui.other.MainOther
import jmapps.arabicinyourhands.ui.viewmodel.WordsCategoryViewModel

class AddWordCategoryBottomSheet : BottomSheetDialogFragment(), TextWatcher, View.OnClickListener {

    override fun getTheme() = R.style.BottomSheetStyleFull

    private lateinit var binding: BottomsheetAddWordCategoryBinding
    private lateinit var wordsCategoryViewModel: WordsCategoryViewModel

    private var standardColor: String = "#ba68c8"

    companion object {
        const val ARG_ADD_WORD_CATEGORY_BS = "arg_add_word_category_bs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordsCategoryViewModel = ViewModelProvider(this).get(WordsCategoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_add_word_category, container, false)

        retainInstance = true

        DrawableCompat.setTint(binding.textAddWordCategoryColor.background, Color.parseColor(standardColor))

        val categoryNameCharacters = getString(R.string.max_word_category_name_characters, 0)
        binding.textLengthAddWordCategoryCharacters.text = categoryNameCharacters

        binding.editAddWordCategory.addTextChangedListener(this)
        binding.textAddWordCategoryColor.setOnClickListener(this)
        binding.buttonAddWordCategory.setOnClickListener(this)

        return binding.root
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length!! <= 100) {
            val categoryNameCharacters = getString(R.string.max_word_category_name_characters, s.length)
            binding.textLengthAddWordCategoryCharacters.text = categoryNameCharacters
        }

        if (s.length == 100) {
            Toast.makeText(requireContext(), getString(R.string.toast_achieved_max_word_category_name_characters), Toast.LENGTH_SHORT).show()
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.text_add_word_category_color -> {
                MaterialColorPickerDialog
                    .Builder(requireContext())
                    .setTitle(getString(R.string.description_choose_color))
                    .setColorRes(resources.getIntArray(R.array.wordColors).toList())
                    .setColorListener { _, colorHex ->
                        standardColor = colorHex
                        DrawableCompat.setTint(binding.textAddWordCategoryColor.background, Color.parseColor(standardColor))
                    }
                    .show()
            }
            R.id.button_add_word_category -> {
                checkEditText()
            }
        }
    }

    private fun checkEditText() = if (binding.editAddWordCategory.text.toString().isNotEmpty()) {
        Toast.makeText(requireContext(), getString(R.string.toast_category_added), Toast.LENGTH_SHORT).show()
        addWordCategory()
    } else {
        binding.editAddWordCategory.error = getString(R.string.hint_enter_category_name)
    }

    private fun addWordCategory() {
        val addWordCategories = WordCategories(
            0,
            binding.editAddWordCategory.text.toString().trim(),
            standardColor,
            binding.spinnerWordPriority.selectedItemId,
            MainOther().currentTime,
            MainOther().currentTime
        )
        wordsCategoryViewModel.insertWordCategory(addWordCategories)
        dialog?.dismiss()
    }
}