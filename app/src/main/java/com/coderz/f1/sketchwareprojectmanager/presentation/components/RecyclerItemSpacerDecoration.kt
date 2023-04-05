package com.coderz.f1.sketchwareprojectmanager.presentation.components

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemSpacerDecoration(private val itemSpacing: Int = 0): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacing = convertDpToPx(view.context,itemSpacing)
        outRect.bottom = spacing
    }

    private fun convertDpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}