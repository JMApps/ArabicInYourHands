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
import jmapps.arabicinyourhands.databinding.BottomSheetSecondSubChapterBinding
import jmapps.arabicinyourhands.ui.activities.SecondContentActivity
import jmapps.arabicinyourhands.ui.adapter.SubChapterAdapter
import jmapps.arabicinyourhands.ui.model.ChapterModel
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class SecondSubChapterBottomSheet() : BottomSheetDialogFragment(),
    SubChapterAdapter.OnSubChapterItemClick {

    override fun getTheme() = R.style.BottomSheetStyle

    private var sectionNumber: Int? = null

    private lateinit var binding: BottomSheetSecondSubChapterBinding
    private lateinit var chapterList: MutableList<ChapterModel>
    private lateinit var subChapterList: MutableList<SubChapterModel>
    private lateinit var subChapterAdapter: SubChapterAdapter

    companion object {

        const val secondSubChapterTag = "second_sub_chapter_tag"
        private const val ARG_SECTION_NUMBER = "second_section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): SecondSubChapterBottomSheet {
            return SecondSubChapterBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)
        chapterList = ChapterLists(context).getSecondChapters
        subChapterList = SubChapterLists(context).getSecondSubChapters(sectionNumber!!)
        subChapterAdapter = SubChapterAdapter(context, subChapterList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_second_sub_chapter, container, false)

        binding.tvChapterName.text = chapterList[sectionNumber!! - 1].chapterTitle

        val verticalLayout = LinearLayoutManager(context)
        binding.rvSecondSubChapter.layoutManager = verticalLayout
        binding.rvSecondSubChapter.adapter = subChapterAdapter

        return binding.root
    }

    override fun onItemClick(subChapterId: Int, subChapterPosition: Int) {
        val toSecondContentActivity = Intent(context, SecondContentActivity::class.java)
        toSecondContentActivity.putExtra("key_second_chapter_id", sectionNumber)
        toSecondContentActivity.putExtra("key_second_sub_chapter_id", subChapterId)
        toSecondContentActivity.putExtra("key_second_sub_chapter_position", subChapterPosition)
        startActivity(toSecondContentActivity)
        dialog?.dismiss()
    }
}