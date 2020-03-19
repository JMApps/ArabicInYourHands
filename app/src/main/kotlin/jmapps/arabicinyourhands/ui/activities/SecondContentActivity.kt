package jmapps.arabicinyourhands.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.lists.SubChapterLists
import jmapps.arabicinyourhands.databinding.ActivitySecondContentBinding
import jmapps.arabicinyourhands.ui.adapter.SecondVolumeContentPagerAdapter
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class SecondContentActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private lateinit var binding: ActivitySecondContentBinding

    private var chapterId: Int? = null
    private var subChapterId: Int? = null
    private var subChapterPosition: Int? = null

    private lateinit var subChapterList: MutableList<SubChapterModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_content)
        setSupportActionBar(binding.toolbar)

        LockOrientation(this).lock()

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        chapterId = intent?.getIntExtra("key_second_chapter_id", 1)
        subChapterId = intent?.getIntExtra("key_second_sub_chapter_id", 1)
        subChapterPosition = intent?.getIntExtra("key_second_sub_chapter_position", 1)
        subChapterList = SubChapterLists(this).getSecondSubChapters(chapterId!!)

        initViewPagerContainer()
        initTextViewsInAppBar(subChapterPosition!!)
        binding.vpSecondContentContainer.addOnPageChangeListener(this)
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
        val secondVolumeContentPagerAdapter = SecondVolumeContentPagerAdapter(supportFragmentManager, subChapterList, chapterId!!)
        binding.apply {
            vpSecondContentContainer.adapter = secondVolumeContentPagerAdapter
            diSecondContent.attachViewPager(binding.vpSecondContentContainer)
            diSecondContent.setDotTintRes(R.color.white)
            vpSecondContentContainer.currentItem = subChapterPosition!!
        }
    }

    private fun initTextViewsInAppBar(position: Int) {
        subChapterList = SubChapterLists(this).getSecondSubChapters(chapterId!!)
        val currentText = subChapterList[position]
        binding.tvDialog.text = currentText.dialog
        binding.tvDialogTitle.text = currentText.dialogTitle
    }
}