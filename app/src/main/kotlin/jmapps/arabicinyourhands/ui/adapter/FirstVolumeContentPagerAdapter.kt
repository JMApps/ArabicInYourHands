package jmapps.arabicinyourhands.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import jmapps.arabicinyourhands.ui.fragment.FirstContentFragment
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class FirstVolumeContentPagerAdapter(
    fragmentManager: FragmentManager,
    private val subChapterList: MutableList<SubChapterModel>,
    private val chapterId: Int) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return FirstContentFragment.newInstance(position + 1, chapterId)
    }

    override fun getCount(): Int {
        return subChapterList.size
    }
}