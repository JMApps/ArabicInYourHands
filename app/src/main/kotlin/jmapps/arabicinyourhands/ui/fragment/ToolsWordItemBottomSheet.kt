package jmapps.arabicinyourhands.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.databinding.BottomsheetToolsWordBinding
import jmapps.arabicinyourhands.ui.preferences.SharedLocalProperties

class ToolsWordItemBottomSheet : BottomSheetDialogFragment(), SeekBar.OnSeekBarChangeListener,
    CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    override fun getTheme() = R.style.BottomSheetStyleFull

    private lateinit var binding: BottomsheetToolsWordBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var sharedLocalPreferences: SharedLocalProperties

    private var textSizeValues: List<Int> = (16..34).toList().filter { it % 2 == 0 }

    companion object {
        const val ARG_TOOLS_WORD_ITEM_BS = "arg_tools_word_item_bs"
        const val ARG_WORD_GRID_COUNT = "arg_word_grid_count"
        const val ARG_WORDS_TEXT_SIZE = "arg_words_text_size"
        const val ARG_WORDS_TEXT_SIZE_PROGRESS = "arg_words_text_size_progress"
        const val ARG_WORD_LEFT_ALIGN = "arg_word_left_align"
        const val ARG_WORD_CENTER_ALIGN = "arg_word_center_align"
        const val ARG_WORD_RIGHT_ALIGN = "arg_word_right_align"
        const val ARG_WORD_STATE = "arg_word_state"
        const val ARG_WORD_TRANSCRIPTION_STATE = "arg_word_transcription_state"
        const val ARG_WORD_TRANSLATE_STATE = "arg_word_translate_state"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_tools_word, container, false)

        retainInstance = true

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sharedLocalPreferences = SharedLocalProperties(preferences)

        val lastWordGridValueProgress = sharedLocalPreferences.getIntValue(ARG_WORD_GRID_COUNT, 2)
        val lastWordTextSizeValueProgress = sharedLocalPreferences.getIntValue(ARG_WORDS_TEXT_SIZE_PROGRESS, 1)
        val lastLeftAlignState = sharedLocalPreferences.getBooleanValue(ARG_WORD_LEFT_ALIGN, true)
        val lastCenterAlignState = sharedLocalPreferences.getBooleanValue(ARG_WORD_CENTER_ALIGN, false)
        val lastRightAlignState = sharedLocalPreferences.getBooleanValue(ARG_WORD_RIGHT_ALIGN, false)
        val lastWordState = sharedLocalPreferences.getBooleanValue(ARG_WORD_STATE, true)
        val lastWordTranscriptionState = sharedLocalPreferences.getBooleanValue(ARG_WORD_TRANSCRIPTION_STATE, true)
        val lastWordTranslateState = sharedLocalPreferences.getBooleanValue(ARG_WORD_TRANSLATE_STATE, true)

        binding.apply {
            seekBarWordGrinCount.progress = lastWordGridValueProgress
            textGridCount.text = (lastWordGridValueProgress + 1).toString()
            seekBarWordTextSize.progress = lastWordTextSizeValueProgress
            textTextSizeCount.text = textSizeValues[lastWordTextSizeValueProgress].toString()
            radioButtonAlignTextLeft.isChecked = lastLeftAlignState
            radioButtonAlignTextCenter.isChecked = lastCenterAlignState
            radioButtonAlignTextRight.isChecked = lastRightAlignState
            switchShowWord.isChecked = lastWordState
            switchShowWordTranscription.isChecked = lastWordTranscriptionState
            switchShowWordTranslate.isChecked = lastWordTranslateState
        }

        binding.seekBarWordGrinCount.setOnSeekBarChangeListener(this)
        binding.seekBarWordTextSize.setOnSeekBarChangeListener(this)
        binding.radioGroupAlignWordText.setOnCheckedChangeListener(this)
        binding.switchShowWord.setOnCheckedChangeListener(this)
        binding.switchShowWordTranscription.setOnCheckedChangeListener(this)
        binding.switchShowWordTranslate.setOnCheckedChangeListener(this)

        return binding.root
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id) {
            R.id.seek_bar_word_grin_count -> {
                binding.textGridCount.text = (progress + 1).toString()
                sharedLocalPreferences.saveIntValue(ARG_WORD_GRID_COUNT, progress)
            }
            R.id.seek_bar_word_text_size -> {
                binding.textTextSizeCount.text = textSizeValues[progress].toString()
                sharedLocalPreferences.saveIntValue(ARG_WORDS_TEXT_SIZE_PROGRESS, progress)
                sharedLocalPreferences.saveIntValue(ARG_WORDS_TEXT_SIZE, textSizeValues[progress])
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (group?.id) {
            R.id.radio_group_align_word_text -> {
                binding.apply {
                    sharedLocalPreferences.saveBooleanValue(ARG_WORD_LEFT_ALIGN, radioButtonAlignTextLeft.isChecked)
                    sharedLocalPreferences.saveBooleanValue(ARG_WORD_CENTER_ALIGN, radioButtonAlignTextCenter.isChecked)
                    sharedLocalPreferences.saveBooleanValue(ARG_WORD_RIGHT_ALIGN, radioButtonAlignTextRight.isChecked)
                }
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.switch_show_word -> {
                sharedLocalPreferences.saveBooleanValue(ARG_WORD_STATE, isChecked)
                if (!isChecked) {
                    if (!binding.switchShowWordTranslate.isChecked) {
                        binding.switchShowWordTranslate.isChecked = true
                    }
                }
            }
            R.id.switch_show_word_transcription -> {
                sharedLocalPreferences.saveBooleanValue(ARG_WORD_TRANSCRIPTION_STATE, isChecked)
            }
            R.id.switch_show_word_translate -> {
                sharedLocalPreferences.saveBooleanValue(ARG_WORD_TRANSLATE_STATE, isChecked)
                if (!isChecked) {
                    if (!binding.switchShowWord.isChecked) {
                        binding.switchShowWord.isChecked = true
                    }
                }
            }
        }
    }
}