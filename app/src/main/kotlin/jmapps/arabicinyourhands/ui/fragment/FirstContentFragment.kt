package jmapps.arabicinyourhands.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.databinding.FragmentFirstContentBinding

class FirstContentFragment : Fragment() {

    private lateinit var binding: FragmentFirstContentBinding

    companion object {

        private const val ARG_SECTION_NUMBER = "first_content_section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): FirstContentFragment {
            return FirstContentFragment().apply {
                arguments = Bundle().apply {
                    arguments?.putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_content, container, false)

        return binding.root
    }
}