package jmapps.arabicinyourhands.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.databinding.FragmentFirstVolumeBinding
import jmapps.arabicinyourhands.ui.viewmodel.MainChaptersViewModel

class FirstVolumeFragment : Fragment() {

    private lateinit var binding: FragmentFirstVolumeBinding
    private lateinit var mainChapterViewModel: MainChaptersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainChapterViewModel = ViewModelProvider(this).get(MainChaptersViewModel::class.java).apply {
            setIndex(1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_volume, container, false)
        mainChapterViewModel.texts.observe(viewLifecycleOwner, Observer<String> {

        })
        return binding.root
    }
}