package jmapps.arabicinyourhands.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.databinding.BottomsheetToolsWordCategoryBinding
import jmapps.arabicinyourhands.ui.preferences.SharedLocalProperties

class ToolsWordCategoryBottomSheet : BottomSheetDialogFragment(),
    CompoundButton.OnCheckedChangeListener {

    override fun getTheme() = R.style.BottomSheetStyleFull

    private lateinit var binding: BottomsheetToolsWordCategoryBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var sharedLocalPreferences: SharedLocalProperties

    companion object {
        const val ARG_TOOLS_WORD_CATEGORY_BS = "arg_tools_word_category_bs"
        const val ARG_WORD_CATEGORY_ADD_DATE_TIME = "arg_word_category_add_date_time"
        const val ARG_WORD_CATEGORY_CHANGE_DATE_TIME = "arg_word_category_change_date_time"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_tools_word_category, container, false)

        retainInstance = true

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sharedLocalPreferences = SharedLocalProperties(preferences)

        val lastWordCategoryAddDateTimeSwitch = sharedLocalPreferences.getBooleanValue(ARG_WORD_CATEGORY_ADD_DATE_TIME, false)
        val lastWordCategoryChangeDateTimeSwitch = sharedLocalPreferences.getBooleanValue(ARG_WORD_CATEGORY_CHANGE_DATE_TIME, false)

        binding.apply {
            switchAddWordCategoryTime.isChecked = lastWordCategoryAddDateTimeSwitch
            switchChangeWordCategoryTime.isChecked = lastWordCategoryChangeDateTimeSwitch
        }

        binding.switchAddWordCategoryTime.setOnCheckedChangeListener(this)
        binding.switchChangeWordCategoryTime.setOnCheckedChangeListener(this)

        return binding.root
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.switch_add_word_category_time -> {
                sharedLocalPreferences.saveBooleanValue(ARG_WORD_CATEGORY_ADD_DATE_TIME, isChecked)
            }
            R.id.switch_change_word_category_time -> {
                sharedLocalPreferences.saveBooleanValue(ARG_WORD_CATEGORY_CHANGE_DATE_TIME, isChecked)
            }
        }
    }
}