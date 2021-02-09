package jmapps.arabicinyourhands.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import jmapps.arabicinyourhands.data.database.room.model.words.WordItems
import jmapps.arabicinyourhands.ui.fragment.FlipWordContainerFragment

class FlipWordsPagerAdapter(
    activity: AppCompatActivity,
    private val wordItemList: MutableList<WordItems>,
    private val flipModeState: Boolean,
    private val sectionId: Long) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return wordItemList.size
    }

    override fun createFragment(position: Int): Fragment {
        return FlipWordContainerFragment.newInstance(position + 1, flipModeState, sectionId)
    }
}