package com.marcpetit.minddenmvvm.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.marcpetit.minddenmvvm.databinding.ItemProductBinding
import com.marcpetit.minddenmvvm.domain.products.Product
import com.squareup.picasso.Picasso

class ProductsViewHolder(private val binding: ItemProductBinding): ViewHolder(binding.root) {

    fun bind(product: Product, onClick: (Product) -> Unit) {
        with(binding) {
            Picasso.get().load(product.image).into(binding.productImage)
            productName.text = product.title
            productPrice.text = "${product.price} €"
            root.setOnClickListener {
                onClick(product)
            }
        }
    }
}