package jmapps.arabicinyourhands.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.databinding.FragmentThirdVolumeBinding

class ThirdVolumeFragment : Fragment() {

    private lateinit var binding: FragmentThirdVolumeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third_volume, container, false)
        return binding.root
    }
}