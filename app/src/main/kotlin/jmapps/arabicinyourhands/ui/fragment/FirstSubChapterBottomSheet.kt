package jmapps.arabicinyourhands.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.lists.ChapterLists
import jmapps.arabicinyourhands.data.database.lists.SubChapterLists
import jmapps.arabicinyourhands.databinding.BottomSheetFirstSubChapterBinding
import jmapps.arabicinyourhands.ui.adapter.SubChapterAdapter
import jmapps.arabicinyourhands.ui.model.ChapterModel
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class FirstSubChapterBottomSheet(private val sectionNumber: Int) : BottomSheetDialogFragment(),
    SubChapterAdapter.OnSubChapterItemClick {

    override fun getTheme() = R.style.BottomSheetStyle

    private lateinit var binding: BottomSheetFirstSubChapterBinding
    private lateinit var chapterList: MutableList<ChapterModel>
    private lateinit var subChapterList: MutableList<SubChapterModel>
    private lateinit var subChapterAdapter: SubChapterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chapterList = ChapterLists(context).getFirstChapters
        subChapterList = SubChapterLists(context).getFirstSubChapters(sectionNumber)
        subChapterAdapter = SubChapterAdapter(context, subChapterList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_first_sub_chapter, container, false)

        binding.tvChapterName.text = chapterList[sectionNumber - 1].chapterTitle

        val verticalLayout = LinearLayoutManager(context)
        binding.rvFirstSubChapter.layoutManager = verticalLayout
        binding.rvFirstSubChapter.adapter = subChapterAdapter

        return binding.root
    }

    override fun onItemClick(subChapterId: Int) {

    }

    companion object {
        const val firstSubChapterTag = "first_sub_chapter_tag"
    }
}