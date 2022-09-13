package com.task.catganisation.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.task.catganisation.R
import com.task.catganisation.ui.common.extensions.inflate
import com.task.domain.ContentContract


@Suppress("UNCHECKED_CAST")
abstract class BaseListAdapter<T : ContentContract, V : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val skeletonLayoutRes: Int,
    private val onEmptyListener: EmptyListener? = null,
    private val onDeleteFirstListener: EmptyListener? = null,
) : ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    var deletedItems = mutableListOf<String>()

    fun isEmpty(): Boolean {
        return itemCount - deletedItems.size <= 0
    }

    fun clearItems() {
        submitList(null)
    }

    fun clearDeleted() {
        deletedItems.clear()
    }

    fun showPlaceholders() {
        val item = getPlaceholderItem()
        val p = listOf(item, item, item, item, item, item, item)
        submitList(p)
    }

    internal abstract fun getPlaceholderItem(): T

    abstract fun onCreateNormalViewHolder(parent: ViewGroup): V

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NORMAL_VIEW_TYPE -> onCreateNormalViewHolder(parent)
            PLACEHOLDER_VIEW_TYPE -> PlaceholderViewHolder.from(parent, skeletonLayoutRes)
            else -> DeletedViewHolder.from(parent)
        }
    }

    abstract fun onBindNormalViewHolder(holder: V, position: Int, item: T)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PlaceholderViewHolder) {
            holder.bind()
        } else if (holder !is DeletedViewHolder) {
            getItem(position)?.let { item ->
                onBindNormalViewHolder(holder as V, position, item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item?.id() == PLACEHOLDER_ID) return PLACEHOLDER_VIEW_TYPE
        return NORMAL_VIEW_TYPE
    }

    companion object {
        private const val NORMAL_VIEW_TYPE = 2
        private const val PLACEHOLDER_VIEW_TYPE = 3
    }
}

class DeletedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): DeletedViewHolder =
            DeletedViewHolder(parent.inflate(R.layout.item_deleted, false))
    }
}

class PlaceholderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val skeletonLayout = view.findViewById<SkeletonLayout>(R.id.skeletonLayout)

    fun bind() {
        skeletonLayout.showSkeleton()
    }
    companion object {
        fun from(parent: ViewGroup, itemLayoutRes: Int): PlaceholderViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(itemLayoutRes, null, false)
            val s = parent.inflate(R.layout.item_skeleton_base, false) as SkeletonLayout
            s.addView(v)
            return PlaceholderViewHolder(s)
        }
    }
}