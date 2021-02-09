package jmapps.arabicinyourhands.ui.other

import android.content.Context
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import jmapps.arabicinyourhands.R

class DeleteAlertUtil(private val ctx: Context, private val onClickDelete: OnClickDelete) {
    fun showAlertDialog(message: String, sectionDelete: Int, id: Long, deleteMessage: String) {
        AlertDialog.Builder(ctx).let {
            it.setIcon(R.drawable.ic_warning)
            it.setTitle(R.string.action_warning)
            it.setMessage(Html.fromHtml(message))
            it.setCancelable(false)
            it.setNegativeButton(ctx.getString(R.string.alert_cancel)) { dialog, _ ->
                dialog?.dismiss()
            }
            it.setPositiveButton(ctx.getString(R.string.alert_ok)) { dialog, _ ->
                when (sectionDelete) {
                    0 -> onClickDelete.onClickDeleteAll()
                    1 -> onClickDelete.onClickDeleteOnly(id)
                }
                Toast.makeText(ctx, deleteMessage, Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            }
            it.show()
        }
    }

    interface OnClickDelete {
        fun onClickDeleteAll()
        fun onClickDeleteOnly(_id: Long)
    }
}