package jmapps.arabicinyourhands.mvp.other

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import jmapps.arabicinyourhands.R

class OtherPresenterImpl(private val context: Context?, private val otherView: OtherContract.OtherView?) :
    OtherContract.OtherPresenter {

    private val linkDescription = context?.getString(R.string.app_name)
    private val linkOtherApp = "https://telegra.ph/JM-Applications-03-11"
    private val linkApp = "https://play.google.com/store/apps/details?id=jmapps.arabicinyourhands"

    override fun replaceFragment(fragment: Fragment, title: String) {
        otherView?.replaceFragment(fragment, title)
    }

    override fun setTools() {
        otherView?.getTools()
    }

    override fun setNightMode(state: Boolean) {
        otherView?.getNightMode(state)
    }

    override fun setAboutUs() {
        otherView?.getAboutUs()
    }

    override fun otherApp() {
        val rate = Intent(Intent.ACTION_VIEW)
        rate.data = Uri.parse(linkOtherApp)
        context?.startActivity(rate)
    }

    override fun rateApp() {
        val rate = Intent(Intent.ACTION_VIEW)
        rate.data = Uri.parse(linkApp)
        context?.startActivity(rate)
    }

    override fun shareLink() {
        val shareLink = Intent(Intent.ACTION_SEND)
        shareLink.type = "text/plain"
        shareLink.putExtra(Intent.EXTRA_TEXT, "$linkDescription\n$linkApp")
        context?.startActivity(shareLink)
    }
}