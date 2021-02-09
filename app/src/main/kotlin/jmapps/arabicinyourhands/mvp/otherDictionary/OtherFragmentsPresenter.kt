package jmapps.arabicinyourhands.mvp.otherDictionary

class OtherFragmentsPresenter(_otherView: ContractInterface.OtherView) :
    ContractInterface.Presenter {

    private var otherView: ContractInterface.OtherView = _otherView
    private var model: ContractInterface.Model = OtherFragmentsModel()

    override fun initView(orderIndex: Int) {
        otherView.initView(getOrderBy(orderIndex))
    }

    override fun defaultState() {
        otherView.defaultState()
    }

    override fun recyclerCategory() = model.recyclerCategory()

    override fun descriptionMain() = model.descriptionMain()

    override fun updateState(list: List<Any>) {
        model.updateState(list)
        otherView.updateState()
    }

    override fun getOrderBy(orderIndex: Int) = model.orderBy(orderIndex)
}