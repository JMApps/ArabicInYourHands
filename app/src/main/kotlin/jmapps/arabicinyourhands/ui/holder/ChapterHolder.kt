package jmapps.arabicinyourhands.ui.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.ui.adapter.ChapterAdapter

class ChapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val llContentItem: LinearLayoutCompat = itemView.findViewById(R.id.llContentItem)
    val tvChapterId: TextView = itemView.findViewById(R.id.tvChapterId)
    val ivChapterIcon: ImageView = itemView.findViewById(R.id.ivChapterIcon)
    val tvChapterTitle: TextView = itemView.findViewById(R.id.tvChapterTitle)
    val tvChapterTitleArabic: TextView = itemView.findViewById(R.id.tvChapterTitleArabic)

    fun findItemClick(onChapterItemClick: ChapterAdapter.OnChapterItemClick, chapterId: Int, chapterPosition: Int) {
        itemView.setOnClickListener {
            onChapterItemClick.onItemClick(chapterId, chapterPosition)
        }
    }
}