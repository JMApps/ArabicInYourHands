package jmapps.arabicinyourhands.ui.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.data.database.content.ContentLists
import jmapps.arabicinyourhands.data.database.lists.SubChapterLists
import jmapps.arabicinyourhands.databinding.ActivityFirstContentBinding
import jmapps.arabicinyourhands.ui.adapter.ContentAdapter
import jmapps.arabicinyourhands.ui.fragment.ToolsBottomSheet
import jmapps.arabicinyourhands.ui.model.ContentModel
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class FirstContentActivity : AppCompatActivity(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener, MediaPlayer.OnCompletionListener,
    ContentAdapter.OnContentItemClick {

    private lateinit var binding: ActivityFirstContentBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var chapterId: Int? = null
    private var subChapterId: Int? = null
    private var subChapterPosition: Int? = null

    private lateinit var subChapterList: MutableList<SubChapterModel>
    private lateinit var contentList: MutableList<ContentModel>
    private lateinit var contentAdapter: ContentAdapter

    private var player: MediaPlayer? = null
    private var playIndex: Int = 0

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_content)
        setSupportActionBar(binding.toolbar)

        LockOrientation(this).lock()

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        getIdsFromIntent()
        initTextViewsInAppBar(chapterId!!, subChapterPosition!!)
        initContentList(subChapterId!!)

        binding.btnNext.setOnClickListener(this)
        binding.btnPrevious.setOnClickListener(this)

        binding.tbPlay.setOnCheckedChangeListener(this)
        binding.tbRepeat.setOnCheckedChangeListener(this)
        binding.tbSerialPlay.setOnCheckedChangeListener(this)

        val switchArabicState = preferences.getBoolean(ToolsBottomSheet.SwitchArabicShow, true)
        val switchTranslationState = preferences.getBoolean(ToolsBottomSheet.SwitchTranslationShow, true)

        binding.swShowArabic.isChecked = switchArabicState
        binding.swShowTranslation.isChecked = switchTranslationState

        binding.swShowArabic.setOnCheckedChangeListener(this)
        binding.swShowTranslation.setOnCheckedChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_first_content, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()

            R.id.action_tools -> {
                val toolsBottomSheet = ToolsBottomSheet()
                toolsBottomSheet.show(supportFragmentManager, ToolsBottomSheet.ToolsTag)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getIdsFromIntent() {
        chapterId = intent?.getIntExtra("key_first_chapter_id", 1)
        subChapterId = intent?.getIntExtra("key_first_sub_chapter_id", 1)
        subChapterPosition = intent?.getIntExtra("key_first_sub_chapter_position", 1)
    }

    private fun initTextViewsInAppBar(chapterId: Int, subChapterPosition: Int) {
        subChapterList = SubChapterLists(this).getFirstSubChapters(chapterId)
        val currentText = subChapterList[subChapterPosition]
        binding.tvDialog.text = currentText.dialog
        binding.tvDialogTitle.text = currentText.dialogTitle
    }

    private fun initContentList(subChapterId: Int) {
        contentList = ContentLists(this).getFirstVolumeContents(subChapterId)
        contentAdapter = ContentAdapter(this, contentList, this)
        val verticalLayout = LinearLayoutManager(this)
        binding.rvFirstVolumeContent.layoutManager = verticalLayout
        binding.rvFirstVolumeContent.adapter = contentAdapter
    }

    private fun initPlayer(playIndex: Int) {
        binding.appBar.setExpanded(false, true)
        clearPlayer()
        val resId: Int? = resources?.getIdentifier(contentList[playIndex].NameAudio,
            "raw", "jmapps.arabicinyourhands")
        player = MediaPlayer.create(this, resId!!)
        player?.setOnCompletionListener(this)
        binding.rvFirstVolumeContent.smoothScrollToPosition(playIndex)
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
                contentAdapter.itemSelected(-1)
                binding.rvFirstVolumeContent.smoothScrollToPosition(0)
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

            R.id.swShowArabic -> {
                editor.putBoolean(ToolsBottomSheet.SwitchArabicShow, isChecked).apply()
                if (!isChecked) {
                    if (!binding.swShowTranslation.isChecked) {
                        binding.swShowTranslation.isChecked = true
                    }
                }
            }

            R.id.swShowTranslation -> {
                editor.putBoolean(ToolsBottomSheet.SwitchTranslationShow, isChecked).apply()
                if (!isChecked) {
                    if (!binding.swShowArabic.isChecked) {
                        binding.swShowArabic.isChecked = true
                    }
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