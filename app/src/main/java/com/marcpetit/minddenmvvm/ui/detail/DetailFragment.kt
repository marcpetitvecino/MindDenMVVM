package com.marcpetit.minddenmvvm.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marcpetit.minddenmvvm.databinding.FragmentDetailBinding
import com.marcpetit.minddenmvvm.domain.products.Product
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

private const val PRODUCT_ARG_KEY = "product"
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var product: Product? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(PRODUCT_ARG_KEY, Product::class.java)
        } else {
            arguments?.getParcelable(PRODUCT_ARG_KEY)
        }
        initViews()
        initListeners()
    }

    private fun initViews() {
        with(binding) {
            Picasso.get().load(product?.image).into(productImage)
            productName.text = product?.title
            productPrice.text = "${product?.price} €"
            productDescription.text = product?.description
        }
    }

    private fun initListeners() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}