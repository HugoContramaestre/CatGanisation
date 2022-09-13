package com.task.catganisation.ui.breeds.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.catganisation.R
import com.task.catganisation.databinding.ItemBreedBinding
import com.task.catganisation.parcel.toParcel
import com.task.catganisation.ui.common.EmptyListener
import com.task.catganisation.ui.common.BaseListAdapter
import com.task.catganisation.ui.common.extensions.bindingInflate
import com.task.domain.CatBreed
import com.task.catganisation.parcel.CatBreed as CatBreedParcel
import com.task.domain.Image

typealias OnBreedItemSelected = (CatBreedParcel) -> Unit

class CatBreedsAdapter(
    private val onItemSelected: OnBreedItemSelected? = null,
    onEmptyListener: EmptyListener? = null,
) : BaseListAdapter<CatBreed, ComicViewHolder>(
    diffCallback = UIMODEL_COMPARATOR,
    skeletonLayoutRes = R.layout.item_breed,
    onEmptyListener = onEmptyListener
) {
    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<CatBreed>() {
            override fun areItemsTheSame(oldItem: CatBreed, newItem: CatBreed): Boolean = false
            override fun areContentsTheSame(oldItem: CatBreed, newItem: CatBreed): Boolean = false
        }
    }

    override fun getPlaceholderItem(): CatBreed {
        return CatBreed(
            id = "placeholder",
            name = "",
            origin = "",
            countryCode = "",
            countryCodes = "",
            description = "",
            wikipediaUrl = "",
            image = Image(
                id = "-1",
                width = 0,
                height = 0,
                url = ""
            ),
            temperament = ""
        )
    }

    override fun onCreateNormalViewHolder(parent: ViewGroup): ComicViewHolder =
        ComicViewHolder(parent.bindingInflate(R.layout.item_breed, false))

    override fun onBindNormalViewHolder(holder: ComicViewHolder, position: Int, item: CatBreed) {
        holder.dataBinding.item = item
        holder.dataBinding.root.setOnClickListener { onItemSelected?.invoke(item.toParcel()) }
    }
}

class ComicViewHolder(val dataBinding: ItemBreedBinding) :
    RecyclerView.ViewHolder(dataBinding.root)