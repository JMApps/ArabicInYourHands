package jmapps.arabicinyourhands.ui.fragment

import android.annotation.SuppressLint
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
import jmapps.arabicinyourhands.databinding.BottomSheetToolsBinding

class ToolsBottomSheet : BottomSheetDialogFragment(), SeekBar.OnSeekBarChangeListener,
    RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    override fun getTheme(): Int = R.style.BottomSheetStyle
    private lateinit var binding: BottomSheetToolsBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var textSizeValues = (16..30).toList().filter { it % 2 == 0 }

    companion object {
        const val ToolsTag = "ToolsBottomSheet"

        const val ArabicTextSize = "KeyArabicTextSize"
        const val TranslationTextSize = "KeyTranslationTextSize"

        const val ArabicFontOne = "KeyArabicFontOne"
        const val ArabicFontTwo = "KeyArabicFontTwo"
        const val ArabicFontThree = "KeyArabicFontThree"

        const val TranslationFontOne = "KeyTranslationFontOne"
        const val TranslationFontTwo = "KeyTranslationFontTwo"
        const val TranslationFontThree = "KeyTranslationFontThree"

        const val SwitchArabicShow = "KeySwitchArabicShow"
        const val SwitchTranslationShow = "KeySwitchTranslationShow"
        const val SwitchShareButtonShow = "KeySwitchShareButtonShow"
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_tools, container, false)

        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences.edit()

        binding.apply {
            val arabicProgress = preferences.getInt(ArabicTextSize, 1)
            val translationProgress = preferences.getInt(TranslationTextSize, 1)

            sbTextSizeArabic.progress = arabicProgress
            sbTextSizeTranslation.progress = translationProgress

            tvTextSizeArabicCounter.text = textSizeValues[arabicProgress].toString()
            tvTextSizeTranslationCounter.text = textSizeValues[translationProgress].toString()

            val arabicFontOne = preferences.getBoolean(ArabicFontOne, true)
            val arabicFontTwo = preferences.getBoolean(ArabicFontTwo, false)
            val arabicFontThree = preferences.getBoolean(ArabicFontThree, false)

            val translationFontOne = preferences.getBoolean(TranslationFontOne, true)
            val translationFontTwo = preferences.getBoolean(TranslationFontTwo, false)
            val translationFontThree = preferences.getBoolean(TranslationFontThree, false)

            rbArabicFontOne.isChecked = arabicFontOne
            rbArabicFontTwo.isChecked = arabicFontTwo
            rbArabicFontThree.isChecked = arabicFontThree

            rbTranslationFontOne.isChecked = translationFontOne
            rbTranslationFontTwo.isChecked = translationFontTwo
            rbTranslationFontThree.isChecked = translationFontThree

            val switchArabicState = preferences.getBoolean(SwitchArabicShow, true)
            val switchTranslationState = preferences.getBoolean(SwitchTranslationShow, true)
            val switchShareButtonState = preferences.getBoolean(SwitchShareButtonShow, true)

            swShowArabic.isChecked = switchArabicState
            swShowTranslation.isChecked = switchTranslationState
            swShowShareButton.isChecked = switchShareButtonState
        }

        binding.sbTextSizeArabic.setOnSeekBarChangeListener(this)
        binding.sbTextSizeTranslation.setOnSeekBarChangeListener(this)

        binding.rgFontArabic.setOnCheckedChangeListener(this)
        binding.rgFontTranslation.setOnCheckedChangeListener(this)

        binding.swShowArabic.setOnCheckedChangeListener(this)
        binding.swShowTranslation.setOnCheckedChangeListener(this)
        binding.swShowShareButton.setOnCheckedChangeListener(this)

        return binding.root
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when(seekBar?.id) {
            R.id.sbTextSizeArabic -> {
                binding.tvTextSizeArabicCounter.text = textSizeValues[progress].toString()
                editor.putInt(ArabicTextSize, progress).apply()
            }

            R.id.sbTextSizeTranslation -> {
                binding.tvTextSizeTranslationCounter.text = textSizeValues[progress].toString()
                editor.putInt(TranslationTextSize, progress).apply()
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (group?.id) {
            R.id.rgFontArabic -> {
                editor.putBoolean(ArabicFontOne, binding.rbArabicFontOne.isChecked).apply()
                editor.putBoolean(ArabicFontTwo, binding.rbArabicFontTwo.isChecked).apply()
                editor.putBoolean(ArabicFontThree, binding.rbArabicFontThree.isChecked).apply()
            }

            R.id.rgFontTranslation -> {
                editor.putBoolean(TranslationFontOne, binding.rbTranslationFontOne.isChecked).apply()
                editor.putBoolean(TranslationFontTwo, binding.rbTranslationFontTwo.isChecked).apply()
                editor.putBoolean(TranslationFontThree, binding.rbTranslationFontThree.isChecked).apply()
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id) {
            R.id.swShowArabic -> {
                editor.putBoolean(SwitchArabicShow, isChecked).apply()
                if (!isChecked) {
                    if (!binding.swShowTranslation.isChecked) {
                        binding.swShowTranslation.isChecked = true
                    }
                }
            }

            R.id.swShowTranslation -> {
                editor.putBoolean(SwitchTranslationShow, isChecked).apply()
                if (!isChecked) {
                    if (!binding.swShowArabic.isChecked) {
                        binding.swShowArabic.isChecked = true
                    }
                }
            }

            R.id.swShowShareButton -> {
                editor.putBoolean(SwitchShareButtonShow, isChecked).apply()
            }
        }
    }
}