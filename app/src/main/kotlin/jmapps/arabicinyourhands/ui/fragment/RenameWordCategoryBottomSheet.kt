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
import jmapps.arabicinyourhands.databinding.BottomsheetRenameWordCategoryBinding
import jmapps.arabicinyourhands.ui.other.MainOther
import jmapps.arabicinyourhands.ui.viewmodel.WordsCategoryViewModel

class RenameWordCategoryBottomSheet : BottomSheetDialogFragment(), TextWatcher,
    View.OnClickListener {

    override fun getTheme() = R.style.BottomSheetStyleFull

    private lateinit var binding: BottomsheetRenameWordCategoryBinding
    private lateinit var wordsCategoryViewModel: WordsCategoryViewModel

    private var categoryId: Long? = null
    private var categoryTitle: String? = null
    private var categoryColor: String? = null
    private var categoryPriority: Long? = null
    private var newCategoryColor: String? = null

    companion object {
        const val ARG_RENAME_WORD_CATEGORY_BS = "arg_rename_word_category_bs"
        private const val ARG_RENAME_WORD_CATEGORY_ID = "arg_rename_word_category_id"
        private const val ARG_RENAME_WORD_CATEGORY_TITLE = "arg_rename_word_category_title"
        private const val ARG_RENAME_WORD_CATEGORY_COLOR = "arg_rename_word_category_color"
        private const val ARG_RENAME_WORD_CATEGORY_PRIORITY = "arg_rename_word_category_priority"

        @JvmStatic
        fun toInstance(
            categoryId: Long,
            categoryTitle: String,
            categoryColor: String,
            categoryPriority: Long): RenameWordCategoryBottomSheet {
            return RenameWordCategoryBottomSheet().apply {
                arguments = Bundle().apply {
                    putLong(ARG_RENAME_WORD_CATEGORY_ID, categoryId)
                    putString(ARG_RENAME_WORD_CATEGORY_TITLE, categoryTitle)
                    putString(ARG_RENAME_WORD_CATEGORY_COLOR, categoryColor)
                    putLong(ARG_RENAME_WORD_CATEGORY_PRIORITY, categoryPriority)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordsCategoryViewModel = ViewModelProvider(this).get(WordsCategoryViewModel::class.java)

        categoryId = arguments?.getLong(ARG_RENAME_WORD_CATEGORY_ID, 0)
        categoryTitle = arguments?.getString(ARG_RENAME_WORD_CATEGORY_TITLE)
        categoryColor = arguments?.getString(ARG_RENAME_WORD_CATEGORY_COLOR)
        categoryPriority = arguments?.getLong(ARG_RENAME_WORD_CATEGORY_PRIORITY, 0)

        newCategoryColor = categoryColor
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_rename_word_category, container, false)

        retainInstance = true

        binding.editRenameWordCategory.setText(categoryTitle)
        binding.editRenameWordCategory.setSelection(categoryTitle!!.length)
        DrawableCompat.setTint(binding.textNewWordCategoryColor.background, Color.parseColor(categoryColor))

        val categoryNameCharacters = getString(R.string.max_word_category_name_characters, categoryTitle!!.length)
        binding.textLengthChangeWordCategoryCharacters.text = categoryNameCharacters

        binding.spinnerWordPriority.setSelection(categoryPriority!!.toInt())

        binding.editRenameWordCategory.addTextChangedListener(this)
        binding.textNewWordCategoryColor.setOnClickListener(this)
        binding.buttonRenameWordCategory.setOnClickListener(this)

        return binding.root
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length!! <= 100) {
            val categoryNameCharacters = getString(R.string.max_word_category_name_characters, s.length)
            binding.textLengthChangeWordCategoryCharacters.text = categoryNameCharacters
        }

        if (s.length == 100) {
            Toast.makeText(requireContext(), getString(R.string.toast_achieved_max_word_category_name_characters), Toast.LENGTH_SHORT).show()
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.text_new_word_category_color -> {
                MaterialColorPickerDialog
                    .Builder(requireContext())
                    .setTitle(getString(R.string.description_choose_color))
                    .setColorRes(resources.getIntArray(R.array.wordColors).toList())
                    .setColorListener { _, colorHex ->
                        newCategoryColor = colorHex
                        DrawableCompat.setTint(binding.textNewWordCategoryColor.background, Color.parseColor(newCategoryColor))
                    }
                    .show()
            }
            R.id.button_rename_word_category -> {
                checkName()
            }
        }
    }

    private fun checkName() {
        when {
            binding.editRenameWordCategory.text.toString().isEmpty() -> {
                binding.editRenameWordCategory.error = getString(R.string.hint_enter_new_category_name)
            }
            binding.editRenameWordCategory.text.toString().isNotEmpty() &&
                    binding.editRenameWordCategory.text.toString() != categoryTitle ||
                    categoryColor != newCategoryColor || categoryPriority != binding.spinnerWordPriority.selectedItemId -> {
                renameWordCategory()
                Toast.makeText(requireContext(), getString(R.string.toast_category_changed), Toast.LENGTH_SHORT).show()
            }
            else -> {
                dialog?.dismiss()
            }
        }
    }

    private fun renameWordCategory() {
        wordsCategoryViewModel.updateWordCategory(
            binding.editRenameWordCategory.text.toString().trim(),
            newCategoryColor!!,
            MainOther().currentTime,
            binding.spinnerWordPriority.selectedItemId,
            categoryId!!
        )

        if (newCategoryColor != categoryColor) {
            wordsCategoryViewModel.updateWordItemColor(newCategoryColor!!, categoryId!!)
        }
        dialog?.dismiss()
    }
}