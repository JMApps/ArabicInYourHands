package jmapps.arabicinyourhands.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.ui.holder.ChapterHolder
import jmapps.arabicinyourhands.ui.model.ChapterModel

class ChapterAdapter(
    private val context: Context?,
    private val chapterList: MutableList<ChapterModel>,
    private val onChapterItemClick: OnChapterItemClick) : RecyclerView.Adapter<ChapterHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var currentIndex = -1

    interface OnChapterItemClick {
        fun onItemClick(chapterId: Int, chapterPosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
        val chapterItem = inflater.inflate(R.layout.item_chapter, parent, false)
        return ChapterHolder(chapterItem)
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
        val current = chapterList[position]
        val icSource = context?.resources?.getIdentifier(current.chapterPicture,"drawable", "jmapps.arabicinyourhands")

        if (currentIndex == position) {
            holder.llContentItem.setBackgroundColor(ContextCompat.getColor(context!!, R.color.lightColorId))
        } else {
            holder.llContentItem.setBackgroundColor(ContextCompat.getColor(context!!, R.color.reMain))
        }

        holder.tvChapterId.text = current.chapterId.toString()
        holder.ivChapterIcon.setBackgroundResource(icSource!!)
        holder.tvChapterTitle.text = current.chapterTitle
        holder.tvChapterTitleArabic.text = current.chapterTitleArabic

        holder.findItemClick(onChapterItemClick, current.chapterId!!, position)
    }

    fun itemSelected(currentIndex: Int) {
        this.currentIndex = currentIndex
        notifyDataSetChanged()
    }
}