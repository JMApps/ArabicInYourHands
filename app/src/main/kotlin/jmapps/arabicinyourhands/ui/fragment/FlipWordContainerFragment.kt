package jmapps.arabicinyourhands.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.databinding.FlipWordBackBinding
import jmapps.arabicinyourhands.databinding.FlipWordFrontBinding
import jmapps.arabicinyourhands.ui.viewmodel.WordsItemViewModel

class FlipWordContainerFragment : Fragment() {

    private lateinit var bindingFront: FlipWordFrontBinding
    private lateinit var bindingBack: FlipWordBackBinding
    private lateinit var wordsItemViewModel: WordsItemViewModel

    private var sectionNumber: Int? = null
    private var flipModeState: Boolean? = null
    private var sectionId: Long? = null

    companion object {
        private const val ARG_FLIP_WORD_SECTION_NUMBER = "arg_flip_word_section_number"
        private const val ARG_FLIP_WORD_MODE_STATE = "arg_flip_word_mode_state"
        private const val ARG_FLIP_WORD_SECTION_ID = "arg_flip_word_section_id"

        @JvmStatic
        fun newInstance(sectionNumber: Int, flipModeState: Boolean, sectionId: Long): FlipWordContainerFragment {
            return FlipWordContainerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_FLIP_WORD_SECTION_NUMBER, sectionNumber)
                    putBoolean(ARG_FLIP_WORD_MODE_STATE, flipModeState)
                    putLong(ARG_FLIP_WORD_SECTION_ID, sectionId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordsItemViewModel = ViewModelProvider(this).get(WordsItemViewModel::class.java)
        sectionNumber = arguments?.getInt(ARG_FLIP_WORD_SECTION_NUMBER)
        flipModeState = arguments?.getBoolean(ARG_FLIP_WORD_MODE_STATE)
        sectionId = arguments?.getLong(ARG_FLIP_WORD_SECTION_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        retainInstance = true

        return if (flipModeState!!) {
            bindingFront = DataBindingUtil.inflate(inflater, R.layout.flip_word_front, container, false)
            initFlipFront()
            bindingFront.flViewWords.setOnClickListener {
                bindingFront.flViewWords.flipTheView()
            }
            bindingFront.root
        } else {
            bindingBack = DataBindingUtil.inflate(inflater, R.layout.flip_word_back, container, false)
            initFlipBack()
            bindingBack.flViewWords.setOnClickListener {
                bindingBack.flViewWords.flipTheView()
            }
            bindingBack.root
        }
    }

    private fun initFlipFront() {
        wordsItemViewModel.getAllWordsList(sectionId!!, "").observe(viewLifecycleOwner, {
            val current = it[sectionNumber!! - 1]
            bindingFront.flipContainerFront.textFlipWord.text = current.word
            if (current.wordTranscription.isNotEmpty()) {
                bindingFront.flipContainerBack.textFlipWordTranscription.text = current.wordTranscription
            } else {
                bindingFront.flipContainerBack.textFlipWordTranscription.visibility = View.GONE
            }
            bindingFront.flipContainerBack.textFlipWordTranslate.text = current.wordTranslate
        })
    }

    private fun initFlipBack() {
        wordsItemViewModel.getAllWordsList(sectionId!!, "").observe(viewLifecycleOwner, {
            val current = it[sectionNumber!! - 1]
            bindingBack.flipContainerFront.textFlipWord.text = current.word
            if (current.wordTranscription.isNotEmpty()) {
                bindingBack.flipContainerBack.textFlipWordTranscription.text = current.wordTranscription
            } else {
                bindingBack.flipContainerBack.textFlipWordTranscription.visibility = View.GONE
            }
            bindingBack.flipContainerBack.textFlipWordTranslate.text = current.wordTranslate
        })
    }
}