package jmapps.arabicinyourhands.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.ui.holder.SubChapterHolder
import jmapps.arabicinyourhands.ui.model.SubChapterModel

class SubChapterAdapter(private val context: Context?,
private val subChapterList: MutableList<SubChapterModel>,
private val onSubChapterItemClick: OnSubChapterItemClick) : RecyclerView.Adapter<SubChapterHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var currentIndex = -1

    interface OnSubChapterItemClick {
        fun onItemClick(subChapterId: Int, subChapterPosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubChapterHolder {
        val subChapterItem = inflater.inflate(R.layout.item_sub_chapter, parent, false)
        return SubChapterHolder(subChapterItem)
    }

    override fun getItemCount(): Int {
        return subChapterList.size
    }

    override fun onBindViewHolder(holder: SubChapterHolder, position: Int) {
        val current = subChapterList[position]
        val icSource = context?.resources?.getIdentifier(current.dialogPicture,"drawable", "jmapps.arabicinyourhands")

        if (currentIndex == position) {
            holder.llContentItem.setBackgroundColor(ContextCompat.getColor(context!!, R.color.lightColorId))
        } else {
            holder.llContentItem.setBackgroundColor(ContextCompat.getColor(context!!, R.color.reMain))
        }

        holder.ivSubChapterIcon.setBackgroundResource(icSource!!)
        holder.tvSubChapter.text = current.dialog
        holder.tvSubChapterTitle.text = current.dialogTitle
        holder.findItemClick(onSubChapterItemClick, current.subChapterId, position)
    }

    fun itemSelected(currentIndex: Int) {
        this.currentIndex = currentIndex
        notifyDataSetChanged()
    }
}