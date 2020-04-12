package jmapps.arabicinyourhands.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.lists.ChapterLists
import jmapps.arabicinyourhands.databinding.FragmentSecondChapterBinding
import jmapps.arabicinyourhands.ui.adapter.ChapterAdapter
import jmapps.arabicinyourhands.ui.model.ChapterModel

class SecondChapterFragment : Fragment(), ChapterAdapter.OnChapterItemClick {

    private lateinit var binding: FragmentSecondChapterBinding
    private lateinit var chapterList: MutableList<ChapterModel>
    private lateinit var chapterAdapter: ChapterAdapter

    private lateinit var getSecondSubChapterItem: GetSecondSubChapterItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chapterList = ChapterLists(context).getSecondChapters
        chapterAdapter = ChapterAdapter(context, chapterList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second_chapter, container, false)

        val verticalLayout = LinearLayoutManager(context)
        binding.rvSecondChapter.layoutManager = verticalLayout
        binding.rvSecondChapter.adapter = chapterAdapter

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GetSecondSubChapterItem) {
            getSecondSubChapterItem = context
        } else {
            throw RuntimeException("$context must implement SecondSubChapterItem")
        }
    }

    interface GetSecondSubChapterItem {
        fun secondSubChapterItem(chapterId: Int)
    }

    override fun onItemClick(chapterId: Int, chapterPosition: Int) {
        getSecondSubChapterItem.secondSubChapterItem(chapterId)
        chapterAdapter.itemSelected(chapterPosition)
    }
}