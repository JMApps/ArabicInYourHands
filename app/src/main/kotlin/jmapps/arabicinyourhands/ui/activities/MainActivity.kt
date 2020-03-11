package jmapps.arabicinyourhands.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.databinding.ActivityMainBinding
import jmapps.arabicinyourhands.mvp.other.OtherContract
import jmapps.arabicinyourhands.mvp.other.OtherPresenterImpl
import jmapps.arabicinyourhands.ui.fragment.FirstVolumeFragment
import jmapps.arabicinyourhands.ui.fragment.SecondVolumeFragment
import jmapps.arabicinyourhands.ui.fragment.ThirdVolumeFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener, OtherContract.OtherView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var otherPresenterImpl: OtherPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainAppBar.toolbarMain)

        otherPresenterImpl = OtherPresenterImpl(this)
        otherPresenterImpl.replaceFragment(FirstVolumeFragment())

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
            R.id.nav_tools -> {}

            R.id.nav_night_mode -> {}

            R.id.nav_about_us -> {}

            R.id.nav_rate -> {}

            R.id.nav_share -> {}

            R.id.bottom_nav_first_volume -> otherPresenterImpl.replaceFragment(FirstVolumeFragment())

            R.id.bottom_nav_second_volume -> otherPresenterImpl.replaceFragment(SecondVolumeFragment())

            R.id.bottom_nav_third_volume -> otherPresenterImpl.replaceFragment(ThirdVolumeFragment())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun replaceFragment(fragment: Fragment) {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerMain, fragment)
        fragmentTransaction.commit()
    }
}