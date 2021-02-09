package jmapps.arabicinyourhands.mvp.otherActivities

interface ContractInterface {

    interface OtherView {
        fun initView(displayBy: Long, orderBy: String)
        fun defaultState()
        fun updateState()
    }

    interface Model {
        fun recyclerCategory() : Int
        fun descriptionMain() : Int
        fun updateState(list: List<Any>)
        fun orderBy(orderIndex: Int): String
    }

    interface Presenter {
        fun initView(displayBy: Long, orderIndex: Int)
        fun defaultState()
        fun recyclerCategory() : Int
        fun descriptionMain() : Int
        fun updateState(list: List<Any>)
        fun getOrderBy(orderIndex: Int) : String
    }
}