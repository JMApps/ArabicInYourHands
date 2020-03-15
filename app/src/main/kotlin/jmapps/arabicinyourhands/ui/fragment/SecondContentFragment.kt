package jmapps.arabicinyourhands.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.lists.SubChapterLists
import jmapps.arabicinyourhands.databinding.FragmentSecondContentBinding
import jmapps.arabicinyourhands.ui.adapter.ContentAdapter
import jmapps.arabicinyourhands.ui.model.ContentModel
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class SecondContentFragment : Fragment() , ContentAdapter.OnContentItemClick {

    private var sectionNumber: Int? = null
    private var chapterId: Int? = null

    private lateinit var binding: FragmentSecondContentBinding
    private lateinit var subChapterList: MutableList<SubChapterModel>
    private lateinit var contentList: MutableList<ContentModel>
    private lateinit var contentAdapter: ContentAdapter

    companion object {

        private const val ARG_SECTION_NUMBER = "second_content_section"
        private const val ARG_CHAPTER_NUMBER = "second_chapter_section"

        @JvmStatic
        fun newInstance(sectionNumber: Int, chapterId: Int): SecondContentFragment {
            return SecondContentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putInt(ARG_CHAPTER_NUMBER, chapterId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)
        chapterId = arguments?.getInt(ARG_CHAPTER_NUMBER)

        subChapterList = SubChapterLists(context).getSecondSubChapters(chapterId!!)
        val current = subChapterList[sectionNumber!! - 1]

//        contentList = ContentLists(context).getSecondVolumeContents(current.subChapterId)
//        contentAdapter = ContentAdapter(context, contentList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second_content, container, false)

        val verticalLayout = LinearLayoutManager(context)
//        binding.rvSecondVolumeContent.layoutManager = verticalLayout
//        binding.rvSecondVolumeContent.adapter = contentAdapter

        return binding.root
    }

    override fun onItemClick(contentId: Int, contentPosition: Int) {

    }
}