package jmapps.arabicinyourhands.ui.holder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.arabicinyourhands.R
import jmapps.arabicinyourhands.ui.adapter.SubChapterAdapter

class SubChapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val llContentItem: LinearLayout = itemView.findViewById(R.id.llContentItem)
    val ivSubChapterIcon: ImageView = itemView.findViewById(R.id.ivSubChapterIcon)
    val tvSubChapter: TextView = itemView.findViewById(R.id.tvSubChapter)
    val tvSubChapterTitle: TextView = itemView.findViewById(R.id.tvSubChapterTitle)

    fun findItemClick(onSubChapterItemClick: SubChapterAdapter.OnSubChapterItemClick, subChapterId: Int, subChapterPosition: Int) {
        itemView.setOnClickListener {
            onSubChapterItemClick.onItemClick(subChapterId, subChapterPosition)
        }
    }
}