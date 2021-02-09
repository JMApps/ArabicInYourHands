package jmapps.arabicinyourhands.mvp.other

import androidx.fragment.app.Fragment

interface OtherContract {

    interface OtherView {
        fun replaceFragment(fragment: Fragment, title: String)

        fun getTools()

        fun getNightMode(state: Boolean)

        fun isNightMode(state: Boolean)

        fun getAboutUs()
    }

    interface OtherPresenter {
        fun replaceFragment(fragment: Fragment, title: String)

        fun setTools()

        fun setNightMode(state: Boolean)

        fun setAboutUs()

        fun otherApp()

        fun rateApp()

        fun shareLink()
    }
}