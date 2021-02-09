package jmapps.arabicinyourhands.ui.fragment

import android.content.Intent
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
import jmapps.arabicinyourhands.ui.activities.FirstContentActivity
import jmapps.arabicinyourhands.ui.adapter.SubChapterAdapter
import jmapps.arabicinyourhands.ui.model.ChapterModel
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class FirstSubChapterBottomSheet : BottomSheetDialogFragment(),
    SubChapterAdapter.OnSubChapterItemClick {

    override fun getTheme() = R.style.BottomSheetStyleFMain

    private var sectionNumber: Int? = null

    private lateinit var binding: BottomSheetFirstSubChapterBinding
    private lateinit var chapterList: MutableList<ChapterModel>
    private lateinit var subChapterList: MutableList<SubChapterModel>
    private lateinit var subChapterAdapter: SubChapterAdapter

    companion object {

        const val firstSubChapterTag = "first_sub_chapter_tag"
        private const val ARG_SECTION_NUMBER = "first_section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): FirstSubChapterBottomSheet {
            return FirstSubChapterBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)
        chapterList = ChapterLists(context).getFirstChapters
        subChapterList = SubChapterLists(context).getFirstSubChapters(sectionNumber!!)
        subChapterAdapter = SubChapterAdapter(context, subChapterList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_first_sub_chapter, container, false)

        binding.tvChapterName.text = chapterList[sectionNumber!! - 1].chapterTitle

        val verticalLayout = LinearLayoutManager(context)
        binding.rvFirstSubChapter.layoutManager = verticalLayout
        binding.rvFirstSubChapter.adapter = subChapterAdapter

        return binding.root
    }

    override fun onItemClick(subChapterId: Int, subChapterPosition: Int) {
        val toFirstContentActivity = Intent(context, FirstContentActivity::class.java)
        toFirstContentActivity.putExtra("key_first_chapter_id", sectionNumber)
        toFirstContentActivity.putExtra("key_first_sub_chapter_id", subChapterId)
        toFirstContentActivity.putExtra("key_first_sub_chapter_position", subChapterPosition)
        startActivity(toFirstContentActivity)
        subChapterAdapter.itemSelected(subChapterPosition)
    }
}