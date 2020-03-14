package jmapps.arabicinyourhands.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.content.ContentLists
import jmapps.arabicinyourhands.data.database.lists.SubChapterLists
import jmapps.arabicinyourhands.databinding.FragmentFirstContentBinding
import jmapps.arabicinyourhands.ui.adapter.ContentAdapter
import jmapps.arabicinyourhands.ui.model.ContentModel
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class FirstContentFragment : Fragment(), ContentAdapter.OnContentItemClick {

    private var sectionNumber: Int? = null
    private var chapterId: Int? = null

    private lateinit var binding: FragmentFirstContentBinding
    private lateinit var subChapterList: MutableList<SubChapterModel>
    private lateinit var contentList: MutableList<ContentModel>
    private lateinit var contentAdapter: ContentAdapter

    companion object {

        private const val ARG_SECTION_NUMBER = "first_content_section"
        private const val ARG_CHAPTER_NUMBER = "chapter_section"

        @JvmStatic
        fun newInstance(sectionNumber: Int, chapterId: Int): FirstContentFragment {
            return FirstContentFragment().apply {
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

        subChapterList = SubChapterLists(context).getFirstSubChapters(chapterId!!)
        val current = subChapterList[sectionNumber!! - 1]

        contentList = ContentLists(context).getFirstVolumeContents(current.subChapterId)
        contentAdapter = ContentAdapter(context, contentList, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_content, container, false)

        val verticalLayout = LinearLayoutManager(context)
        binding.rvFirstVolumeContent.layoutManager = verticalLayout
        binding.rvFirstVolumeContent.adapter = contentAdapter

        return binding.root
    }

    override fun onItemClick(contentId: Int, contentPosition: Int) {

    }
}