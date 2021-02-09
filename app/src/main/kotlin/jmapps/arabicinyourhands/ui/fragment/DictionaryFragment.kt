package jmapps.arabicinyourhands.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.databinding.FragmentDictionaryCategoryBinding
import jmapps.arabicinyourhands.mvp.otherDictionary.ContractInterface
import jmapps.arabicinyourhands.mvp.otherDictionary.OtherFragmentsPresenter
import jmapps.arabicinyourhands.ui.activities.AddWordActivity
import jmapps.arabicinyourhands.ui.activities.AddWordActivity.Companion.KEY_WORD_CATEGORY_COLOR
import jmapps.arabicinyourhands.ui.activities.AddWordActivity.Companion.KEY_WORD_CATEGORY_ID
import jmapps.arabicinyourhands.ui.activities.AddWordActivity.Companion.KEY_WORD_CATEGORY_POSITION
import jmapps.arabicinyourhands.ui.activities.AddWordActivity.Companion.KEY_WORD_CATEGORY_PRIORITY
import jmapps.arabicinyourhands.ui.activities.AddWordActivity.Companion.KEY_WORD_CATEGORY_TITLE
import jmapps.arabicinyourhands.ui.adapter.WordCategoriesAdapter
import jmapps.arabicinyourhands.ui.fragment.AddWordCategoryBottomSheet.Companion.ARG_ADD_WORD_CATEGORY_BS
import jmapps.arabicinyourhands.ui.fragment.RenameWordCategoryBottomSheet.Companion.ARG_RENAME_WORD_CATEGORY_BS
import jmapps.arabicinyourhands.ui.fragment.ToolsWordCategoryBottomSheet.Companion.ARG_TOOLS_WORD_CATEGORY_BS
import jmapps.arabicinyourhands.ui.other.DeleteAlertUtil
import jmapps.arabicinyourhands.ui.preferences.SharedLocalProperties
import jmapps.arabicinyourhands.ui.viewmodel.WordsCategoryViewModel

class DictionaryFragment : Fragment(), ContractInterface.OtherView, SearchView.OnQueryTextListener,
    WordCategoriesAdapter.OnItemClickWordCategory, WordCategoriesAdapter.OnLongClickWordCategory,
    DeleteAlertUtil.OnClickDelete {

    private lateinit var binding: FragmentDictionaryCategoryBinding
    private lateinit var wordCategoriesViewModel: WordsCategoryViewModel

    private lateinit var preferences: SharedPreferences
    private lateinit var sharedLocalPreferences: SharedLocalProperties
    private lateinit var otherFragmentsPresenter: OtherFragmentsPresenter

    private lateinit var searchView: SearchView
    private lateinit var wordCategoriesAdapter: WordCategoriesAdapter

    private var defaultOrderIndex = 0
    private lateinit var deleteAlertDialog: DeleteAlertUtil

    companion object {
        private const val KEY_ORDER_WORD_CATEGORY_INDEX = "key_order_word_category_index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wordCategoriesViewModel = ViewModelProvider(this).get(WordsCategoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dictionary_category, container, false)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sharedLocalPreferences = SharedLocalProperties(preferences)

        retainInstance = true
        setHasOptionsMenu(true)

        deleteAlertDialog = DeleteAlertUtil(requireContext(), this)

        defaultOrderIndex = sharedLocalPreferences.getIntValue(KEY_ORDER_WORD_CATEGORY_INDEX, 0)

        otherFragmentsPresenter = OtherFragmentsPresenter(this)
        otherFragmentsPresenter.initView(defaultOrderIndex)

        return binding.root
    }

    override fun initView(orderBy: String) {
        wordCategoriesViewModel.allWordCategories(orderBy).observe(this, {
            it.let {
                val verticalLayout = LinearLayoutManager(requireContext())
                binding.rvWordCategories.layoutManager = verticalLayout
                wordCategoriesAdapter = WordCategoriesAdapter(requireContext(), it, this, this)
                binding.rvWordCategories.adapter = wordCategoriesAdapter
                otherFragmentsPresenter.updateState(it)
                otherFragmentsPresenter.defaultState()
            }
        })
    }

    override fun defaultState() {
        binding.rvWordCategories.visibility = otherFragmentsPresenter.recyclerCategory()
        binding.textMainDictionaryContainerDescription.visibility = otherFragmentsPresenter.descriptionMain()
    }

    override fun updateState() {
        binding.rvWordCategories.visibility = otherFragmentsPresenter.recyclerCategory()
        binding.textMainDictionaryContainerDescription.visibility = otherFragmentsPresenter.descriptionMain()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_word_categories, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search_categories).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_word_categories -> {
                val addWordCategoryBottomSheet = AddWordCategoryBottomSheet()
                addWordCategoryBottomSheet.show(childFragmentManager, ARG_ADD_WORD_CATEGORY_BS)
            }
            R.id.action_tools_word_categories -> {
                val toolsWordCategoryBottomSheet = ToolsWordCategoryBottomSheet()
                toolsWordCategoryBottomSheet.show(childFragmentManager, ARG_TOOLS_WORD_CATEGORY_BS)
            }
            R.id.item_order_by_add_time -> {
                changeOrderList(defaultOrderIndex = 0)
            }
            R.id.item_order_by_change_time -> {
                changeOrderList(defaultOrderIndex = 1)
            }
            R.id.item_order_by_color -> {
                changeOrderList(defaultOrderIndex = 3)
            }
            R.id.item_order_by_alphabet -> {
                changeOrderList(defaultOrderIndex = 4)
            }
            R.id.item_order_by_priority -> {
                changeOrderList(defaultOrderIndex = 5)
            }
            R.id.action_delete_all_categories -> {
                deleteAlertDialog.showAlertDialog(getString(R.string.dialog_message_are_sure_you_want_word_categories), 0, 0, getString(R.string.action_categories_deleted))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (TextUtils.isEmpty(newText)) {
            wordCategoriesAdapter.filter.filter("")
        } else {
            wordCategoriesAdapter.filter.filter(newText)
        }
        return true
    }

    override fun onItemClickWordCategory(wordCategoryId: Long, wordCategoryPosition: Int, wordCategoryTitle: String, wordCategoryColor: String, wordCategoryPriority: Long) {
        toWordActivity(wordCategoryId, wordCategoryPosition, wordCategoryTitle, wordCategoryColor, wordCategoryPriority)
    }

    override fun itemClickRenameCategory(wordCategoryId: Long, wordCategoryTitle: String, wordCategoryColor: String, wordCategoryPriority: Long) {
        val renameWordCategoryBottomSheet = RenameWordCategoryBottomSheet.toInstance(wordCategoryId, wordCategoryTitle, wordCategoryColor, wordCategoryPriority)
        renameWordCategoryBottomSheet.show(childFragmentManager, ARG_RENAME_WORD_CATEGORY_BS)
    }

    override fun itemClickDeleteCategory(wordCategoryId: Long, wordCategoryTitle: String) {
        val deleteTaskCategoryDescription = getString(R.string.dialog_message_are_sure_you_want_word_category, "<b>$wordCategoryTitle</b>")
        deleteAlertDialog.showAlertDialog(deleteTaskCategoryDescription, 1, wordCategoryId, getString(R.string.action_category_deleted))
    }

    override fun onClickDeleteOnly(_id: Long) {
        wordCategoriesViewModel.deleteWordCategory(_id)
        wordCategoriesViewModel.deleteWordItem(_id)
    }

    override fun onClickDeleteAll() {
        wordCategoriesViewModel.deleteAllWordCategories()
        wordCategoriesViewModel.deleteAllWordItems()
    }

    private fun changeOrderList(defaultOrderIndex: Int) {
        otherFragmentsPresenter.initView(defaultOrderIndex)
        sharedLocalPreferences.saveIntValue(KEY_ORDER_WORD_CATEGORY_INDEX, defaultOrderIndex)
    }

    private fun toWordActivity(_id: Long, position: Int, categoryTitle: String, categoryColor: String, categoryPriority: Long) {
        val toAddWordActivity = Intent(requireContext(), AddWordActivity::class.java)
        toAddWordActivity.putExtra(KEY_WORD_CATEGORY_ID, _id)
        toAddWordActivity.putExtra(KEY_WORD_CATEGORY_POSITION, position)
        toAddWordActivity.putExtra(KEY_WORD_CATEGORY_TITLE, categoryTitle)
        toAddWordActivity.putExtra(KEY_WORD_CATEGORY_COLOR, categoryColor)
        toAddWordActivity.putExtra(KEY_WORD_CATEGORY_PRIORITY, categoryPriority)
        startActivity(toAddWordActivity)
    }
}