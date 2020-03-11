package jmapps.arabicinyourhands.mvp.other

import androidx.fragment.app.Fragment

interface OtherContract {

    interface OtherView {
        fun replaceFragment(fragment: Fragment)
    }

    interface OtherPresenter {
        fun replaceFragment(fragment: Fragment)
    }
}