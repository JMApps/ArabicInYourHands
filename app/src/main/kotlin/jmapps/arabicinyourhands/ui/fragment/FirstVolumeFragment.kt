package jmapps.arabicinyourhands.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.lists.ChapterLists
import jmapps.arabicinyourhands.databinding.FragmentFirstVolumeBinding
import jmapps.arabicinyourhands.ui.adapter.ChapterAdapter
import jmapps.arabicinyourhands.ui.model.ChapterModel

class FirstVolumeFragment : Fragment(), ChapterAdapter.OnChapterItemClick {

    private lateinit var binding: FragmentFirstVolumeBinding
    private lateinit var chapterList: MutableList<ChapterModel>
    private lateinit var chapterAdapter: ChapterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chapterList = ChapterLists(context).getFirstChapters
        chapterAdapter = ChapterAdapter(context, chapterList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_volume, container, false)

        val verticalLayout = LinearLayoutManager(context)
        binding.rvChaptersFirstVolume.layoutManager = verticalLayout
        binding.rvChaptersFirstVolume.adapter = chapterAdapter

        return binding.root
    }

    override fun onItemClick(chapterId: Int) {

    }
}