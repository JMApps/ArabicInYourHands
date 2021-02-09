package jmapps.arabicinyourhands.mvp.otherDictionary

import android.view.View

class OtherFragmentsModel : ContractInterface.Model {

    private var recycler = View.GONE
    private var description = View.VISIBLE

    private val listOrderCategories = listOf(
        "addDateTime",
        "changeDateTime",
        "executionDateTime",
        "color",
        "alphabet",
        "priority"
    )

    override fun recyclerCategory() = recycler

    override fun descriptionMain() = description

    override fun updateState(list: List<Any>) {
        if (list.isNotEmpty()) {
            recycler = View.VISIBLE
            description = View.GONE
        } else {
            recycler = View.GONE
            description = View.VISIBLE
        }
    }

    override fun orderBy(orderIndex: Int) : String {
        return listOrderCategories[orderIndex]
    }
}