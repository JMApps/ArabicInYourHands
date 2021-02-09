package jmapps.arabicinyourhands.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.room.model.words.WordItems
import jmapps.arabicinyourhands.databinding.BottomsheetAddWordBinding
import jmapps.arabicinyourhands.ui.other.MainOther
import jmapps.arabicinyourhands.ui.viewmodel.WordsItemViewModel

class AddWordItemBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {

    override fun getTheme() = R.style.BottomSheetStyleFull

    private lateinit var wordItemViewModel: WordsItemViewModel
    private lateinit var binding: BottomsheetAddWordBinding

    private var displayBy: Long? = null
    private var categoryColor: String? = null
    private var categoryPriority: Long? = null

    companion object {
        const val ARG_ADD_WORD_ITEM_BS = "arg_add_word_item_bs"
        private const val ARG_ADD_WORD_CATEGORY_ID = "arg_add_word_category_id"
        private const val ARG_ADD_WORD_CATEGORY_COLOR = "arg_add_word_category_color"
        private const val ARG_ADD_WORD_CATEGORY_PRIORITY = "arg_add_word_category_priority"

        @JvmStatic
        fun toInstance(displayBy: Long, categoryColor: String, categoryPriority: Long): AddWordItemBottomSheet {
            return AddWordItemBottomSheet().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ADD_WORD_CATEGORY_ID, displayBy)
                    putString(ARG_ADD_WORD_CATEGORY_COLOR, categoryColor)
                    putLong(ARG_ADD_WORD_CATEGORY_PRIORITY, categoryPriority)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordItemViewModel = ViewModelProvider(this).get(WordsItemViewModel::class.java)

        displayBy = arguments?.getLong(ARG_ADD_WORD_CATEGORY_ID)!!
        categoryColor = arguments?.getString(ARG_ADD_WORD_CATEGORY_COLOR)!!
        categoryPriority = arguments?.getLong(ARG_ADD_WORD_CATEGORY_PRIORITY)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_add_word, container, false)

        retainInstance = true

        binding.buttonAddMoreWords.setOnClickListener(this)
        binding.buttonAddWordAndClose.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_add_more_words -> {
                if (checkWord() && checkWordTranslate()) {
                    addWordItem()
                    clearedEditTexts()
                }
            }
            R.id.button_add_word_and_close -> {
                if (checkWord() && checkWordTranslate()) {
                    addWordItem()
                    dialog?.dismiss()
                }
            }
        }
    }

    private fun checkWord(): Boolean {
        return if (binding.editAddWord.text.toString().isNotEmpty()) {
            true
        } else {
            binding.editAddWord.error = getString(R.string.hint_enter_word)
            false
        }
    }

    private fun checkWordTranslate(): Boolean {
        return if (binding.editAddWordTranslate.text.toString().isNotEmpty()) {
            true
        } else {
            binding.editAddWordTranslate.error = getString(R.string.hint_enter_word_translate)
            false
        }
    }

    private fun clearedEditTexts() {
        binding.editAddWord.text?.clear()
        binding.editAddWordTranscription.text?.clear()
        binding.editAddWordTranslate.text?.clear()
        binding.editAddWord.requestFocus()
    }

    private fun addWordItem() {
        val addWordItems = WordItems(
            0,
            displayBy!!,
            binding.editAddWord.text.toString(),
            binding.editAddWordTranscription.text.toString(),
            binding.editAddWordTranslate.text.toString(),
            categoryColor!!,
            MainOther().currentTime,
            MainOther().currentTime,
            categoryPriority!!
        )
        Toast.makeText(requireContext(), getString(R.string.toast_word_added), Toast.LENGTH_SHORT).show()
        wordItemViewModel.insertWordItem(addWordItems)
    }
}