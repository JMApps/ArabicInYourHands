package jmapps.arabicinyourhands.ui.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.databinding.ActivityMainBinding
import jmapps.arabicinyourhands.mvp.other.OtherContract
import jmapps.arabicinyourhands.mvp.other.OtherPresenterImpl
import jmapps.arabicinyourhands.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener, OtherContract.OtherView,
    FirstChapterFragment.GetFirstSubChapterItem, SecondChapterFragment.GetSecondSubChapterItem {

    private lateinit var binding: ActivityMainBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var otherPresenterImpl: OtherPresenterImpl

    private lateinit var swNightMode: Switch
    private var valNightMode: Boolean = false

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainAppBar.toolbarMain)

        LockOrientation(this).lock()

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        valNightMode = preferences.getBoolean("key_night_mode", false)
        isNightMode(valNightMode)

        otherPresenterImpl = OtherPresenterImpl(this, this)
        otherPresenterImpl.replaceFragment(FirstChapterFragment(), getString(R.string.app_name))

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.mainAppBar.toolbarMain,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener(this)
        binding.mainAppBar.bottomNavigationMain.setOnNavigationItemSelectedListener(this)

        navigationView.menu.findItem(R.id.nav_night_mode).actionView = Switch(this)
        swNightMode = navigationView.menu.findItem(R.id.nav_night_mode).actionView as Switch
        swNightMode.isClickable = false
        swNightMode.isChecked = valNightMode
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_tools -> otherPresenterImpl.setTools()

            R.id.nav_night_mode -> otherPresenterImpl.setNightMode(!swNightMode.isChecked)

            R.id.nav_about_us -> otherPresenterImpl.setAboutUs()

            R.id.nav_other_apps -> otherPresenterImpl.otherApp()

            R.id.nav_rate -> otherPresenterImpl.rateApp()

            R.id.nav_share -> otherPresenterImpl.shareLink()

            R.id.bottom_nav_first_volume -> otherPresenterImpl.replaceFragment(FirstChapterFragment(), getString(R.string.action_volume_one))

            R.id.bottom_nav_second_volume -> otherPresenterImpl.replaceFragment(SecondChapterFragment(), getString(R.string.action_volume_two))

            R.id.bottom_nav_third_volume -> otherPresenterImpl.replaceFragment(ThirdChapterFragment(), getString(R.string.action_volume_three))

            R.id.bottom_nav_dictionary -> otherPresenterImpl.replaceFragment(DictionaryFragment(), getString(R.string.action_dictionary))
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun replaceFragment(fragment: Fragment, title: String) {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerMain, fragment)
        fragmentTransaction.commit()
        supportActionBar?.title = title
    }

    override fun getTools() {
        val toolsBottomSheet = ToolsBottomSheet()
        toolsBottomSheet.show(supportFragmentManager, ToolsBottomSheet.ToolsTag)
    }

    override fun getNightMode(state: Boolean) {
        isNightMode(state)
        swNightMode.isChecked = state
        editor.putBoolean("key_night_mode", state).apply()
        binding.mainAppBar.bottomNavigationMain.selectedItemId = R.id.bottom_nav_first_volume
    }

    override fun isNightMode(state: Boolean) {
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun getAboutUs() {
        val aboutUsBottomSheet = AboutUsBottomSheet()
        aboutUsBottomSheet.show(supportFragmentManager, AboutUsBottomSheet.AboutUsTag)
    }

    override fun firstSubChapterItem(chapterId: Int) {
        val firstSubChapterBottomSheet = FirstSubChapterBottomSheet.newInstance(chapterId)
        firstSubChapterBottomSheet.show(supportFragmentManager, FirstSubChapterBottomSheet.firstSubChapterTag)
    }

    override fun secondSubChapterItem(chapterId: Int) {
        if (chapterId <= 9) {
            val secondSubChapterBottomSheet = SecondSubChapterBottomSheet.newInstance(chapterId)
            secondSubChapterBottomSheet.show(supportFragmentManager, SecondSubChapterBottomSheet.secondSubChapterTag)
        } else {
            Toast.makeText(this, "Пока не готово...", Toast.LENGTH_SHORT).show()
        }
    }
}