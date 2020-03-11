package jmapps.arabicinyourhands.mvp.other

import androidx.fragment.app.Fragment

class OtherPresenterImpl(private val otherView: OtherContract.OtherView?) :
    OtherContract.OtherPresenter {

    override fun replaceFragment(fragment: Fragment) {
        otherView?.replaceFragment(fragment)
    }
}