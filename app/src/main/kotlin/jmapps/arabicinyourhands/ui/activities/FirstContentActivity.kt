package jmapps.arabicinyourhands.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.lists.SubChapterLists
import jmapps.arabicinyourhands.databinding.ActivityFirstContentBinding
import jmapps.arabicinyourhands.ui.adapter.FirstVolumeContentPagerAdapter
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class FirstContentActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private lateinit var binding: ActivityFirstContentBinding

    private var chapterId: Int? = null
    private var subChapterId: Int? = null
    private var subChapterPosition: Int? = null

    private lateinit var subChapterList: MutableList<SubChapterModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_content)
        setSupportActionBar(binding.toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        chapterId = intent?.getIntExtra("key_first_chapter_id", 1)
        subChapterId = intent?.getIntExtra("key_first_sub_chapter_id", 1)
        subChapterPosition = intent?.getIntExtra("key_first_sub_chapter_position", 1)
        subChapterList = SubChapterLists(this).getFirstSubChapters(chapterId!!)

        initViewPagerContainer()
        initTextViewsInAppBar(subChapterPosition!!)
        binding.vpFirstContentContainer.addOnPageChangeListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        initTextViewsInAppBar(position)
    }

    private fun initViewPagerContainer() {
        val firstVolumeContentPagerAdapter = FirstVolumeContentPagerAdapter(supportFragmentManager, subChapterList, chapterId!!)
        binding.apply {
            vpFirstContentContainer.adapter = firstVolumeContentPagerAdapter
            diFirstContent.attachViewPager(binding.vpFirstContentContainer)
            diFirstContent.setDotTintRes(R.color.white)
            vpFirstContentContainer.currentItem = subChapterPosition!!
        }
    }

    private fun initTextViewsInAppBar(position: Int) {
        subChapterList = SubChapterLists(this).getFirstSubChapters(chapterId!!)
        val currentText = subChapterList[position]
        binding.tvDialog.text = currentText.dialog
        binding.tvDialogTitle.text = currentText.dialogTitle
    }
}