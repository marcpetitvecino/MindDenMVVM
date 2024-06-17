package com.marcpetit.minddenmvvm.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.marcpetit.minddenmvvm.databinding.ItemProductBinding
import com.marcpetit.minddenmvvm.domain.products.Product

class ProductsAdapter(private val onClick: (Product) -> Unit)
    : ListAdapter<Product, ProductsViewHolder>(ProductsDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(ItemProductBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    override fun getItemCount(): Int = currentList.size

    private class ProductsDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id
    }
}