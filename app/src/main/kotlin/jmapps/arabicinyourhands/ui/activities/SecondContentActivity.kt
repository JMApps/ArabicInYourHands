package jmapps.arabicinyourhands.ui.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.content.ContentLists
import jmapps.arabicinyourhands.data.database.lists.SubChapterLists
import jmapps.arabicinyourhands.databinding.ActivitySecondContentBinding
import jmapps.arabicinyourhands.ui.adapter.ContentAdapter
import jmapps.arabicinyourhands.ui.model.ContentModel
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class SecondContentActivity : AppCompatActivity(), ContentAdapter.OnContentItemClick,
    MediaPlayer.OnCompletionListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivitySecondContentBinding

    private var chapterId: Int? = null
    private var subChapterId: Int? = null
    private var subChapterPosition: Int? = null

    private lateinit var subChapterList: MutableList<SubChapterModel>
    private lateinit var contentList: MutableList<ContentModel>
    private lateinit var contentAdapter: ContentAdapter

    private var player: MediaPlayer? = null
    private var playIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_content)
        setSupportActionBar(binding.toolbar)

        LockOrientation(this).lock()

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        getIdsFromIntent()
        initTextViewsInAppBar(chapterId!!, subChapterId!!)
        initContentList(subChapterId!!)

        binding.btnNext.setOnClickListener(this)
        binding.btnPrevious.setOnClickListener(this)

        binding.tbPlay.setOnCheckedChangeListener(this)
        binding.tbRepeat.setOnCheckedChangeListener(this)
        binding.tbSerialPlay.setOnCheckedChangeListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getIdsFromIntent() {
        chapterId = intent?.getIntExtra("key_second_chapter_id", 1)
        subChapterId = intent?.getIntExtra("key_second_sub_chapter_id", 1)
        subChapterPosition = intent?.getIntExtra("key_second_sub_chapter_position", 1)
    }

    private fun initTextViewsInAppBar(chapterId: Int, subChapterId: Int) {
        subChapterList = SubChapterLists(this).getSecondSubChapters(chapterId)
        val currentText = subChapterList[subChapterId]
        binding.tvDialog.text = currentText.dialog
        binding.tvDialogTitle.text = currentText.dialogTitle
    }

    private fun initContentList(subChapterId: Int) {
        contentList = ContentLists(this).getSecondVolumeContents(subChapterId)
        contentAdapter = ContentAdapter(this, contentList, this)
        val verticalLayout = LinearLayoutManager(this)
        binding.rvSecondVolumeContent.layoutManager = verticalLayout
        binding.rvSecondVolumeContent.adapter = contentAdapter
    }

    private fun initPlayer(playIndex: Int) {
        binding.appBar.setExpanded(false, true)
        clearPlayer()
        val resId: Int? = resources?.getIdentifier(contentList[playIndex].NameAudio,
            "raw", "jmapps.arabicinyourhands")
        player = MediaPlayer.create(this, resId!!)
        player?.setOnCompletionListener(this)
        binding.rvSecondVolumeContent.smoothScrollToPosition(playIndex)
        contentAdapter.itemSelected(playIndex)
    }

    private fun onePlay(playIndex: Int) {
        clearPlayer()
        val resId: Int? = resources?.getIdentifier(contentList[playIndex].NameAudio,
            "raw", "jmapps.arabicinyourhands")
        player = MediaPlayer.create(this, resId!!)
        player?.start()
        player?.setOnCompletionListener {
            player = null
            binding.tbPlay.isChecked = false
            contentAdapter.itemSelected(- 1)
        }
    }

    override fun onItemClick(contentId: Int, contentPosition: Int) {
        onePlay(contentPosition)
        contentAdapter.itemSelected(contentPosition)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        player?.isLooping = binding.tbRepeat.isChecked
        if (binding.tbSerialPlay.isChecked) {
            if (playIndex < contentList.size - 1) {
                playIndex++
                binding.tbPlay.isChecked = false
                initPlayer(playIndex)
                binding.tbPlay.isChecked = true
                player?.start()
            } else {
                playIndex = 0
                initPlayer(playIndex)
                player?.start()
                player?.setOnCompletionListener(this)
            }
        } else {
            if (playIndex < contentList.size - 1) {
                playIndex++
                binding.tbPlay.isChecked = false
                initPlayer(playIndex)
                binding.tbPlay.isChecked = true
                player?.start()
            } else {
                contentAdapter.itemSelected(- 1)
                binding.rvSecondVolumeContent.smoothScrollToPosition(0)
                binding.appBar.setExpanded(true, true)
                playIndex = 0
                binding.tbRepeat.isChecked = false
                binding.tbPlay.isChecked = false
                player = null
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btnPrevious -> {
                if (playIndex > 0) {
                    playIndex--
                    initPlayer(playIndex)
                    player?.start()
                }
            }

            R.id.btnNext -> {
                if (playIndex < contentList.size - 1) {
                    playIndex++
                    initPlayer(playIndex)
                    player?.start()
                }
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {

            R.id.tbPlay -> {
                if (isChecked) {
                    if (player == null) {
                        initPlayer(playIndex)
                        player?.start()
                    } else {
                        player?.start()
                    }
                } else {
                    player?.pause()
                }
            }

            R.id.tbRepeat -> {
                if (isChecked) {
                    Toast.makeText(this, "Повтор включён", Toast.LENGTH_SHORT).show()
                    if (binding.tbSerialPlay.isChecked) {
                        binding.tbSerialPlay.isChecked = false
                    }
                } else {
                    Toast.makeText(this, "Повтор отключён", Toast.LENGTH_SHORT).show()
                }
                player?.isLooping = isChecked
            }

            R.id.tbSerialPlay -> {
                if (isChecked) {
                    Toast.makeText(this, "Последовательное воспроизведение вкл.", Toast.LENGTH_SHORT).show()
                    if (binding.tbRepeat.isChecked) {
                        binding.tbRepeat.isChecked = false
                    }
                } else {
                    Toast.makeText(this, "Последовательное воспроизведение выкл.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearPlayer()
    }

    private fun clearPlayer() {
        if (player != null) {
            player?.stop()
            player?.release()
            player = null
        }
    }
}