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
import jmapps.arabicinyourhands.databinding.FragmentFirstContentBinding
import jmapps.arabicinyourhands.ui.adapter.ContentAdapter
import jmapps.arabicinyourhands.ui.model.ContentModel

class FirstContentFragment : Fragment(), ContentAdapter.OnContentItemClick {

    private var sectionNumber: Int? = null

    private lateinit var binding: FragmentFirstContentBinding
    private lateinit var contentList: MutableList<ContentModel>
    private lateinit var contentAdapter: ContentAdapter

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
        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)
        contentList = ContentLists(context).getFirstVolumeContents(1)
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