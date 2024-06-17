package com.marcpetit.minddenmvvm.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.marcpetit.minddenmvvm.R
import com.marcpetit.minddenmvvm.databinding.FragmentHomeBinding
import com.marcpetit.minddenmvvm.domain.products.Product
import com.marcpetit.minddenmvvm.domain.products.ProductType
import com.marcpetit.minddenmvvm.ui.home.adapter.ProductsAdapter
import com.marcpetit.minddenmvvm.utils.ViewUtils.gone
import com.marcpetit.minddenmvvm.utils.ViewUtils.invisible
import com.marcpetit.minddenmvvm.utils.ViewUtils.visible
import dagger.hilt.android.AndroidEntryPoint

private const val PRODUCT_ARG_KEY = "product"
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: ProductsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
       with(binding) {
           val navOptions = navOptions {
               anim {
                   enter = R.anim.alpha_in
                   exit = R.anim.alpha_out
               }
           }
           adapter = ProductsAdapter {
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, Bundle().apply {
                    putParcelable(PRODUCT_ARG_KEY, it)
                }, navOptions)
           }
           products.adapter = adapter
       }
    }

    private fun initListeners() {
        with(binding) {
            menWear.setOnClickListener {
                showProductList(ProductType.MEN)
            }
            womenWear.setOnClickListener {
                showProductList(ProductType.WOMEN)
            }
            jewelry.setOnClickListener {
                showProductList(ProductType.JEWELRY)
            }
            closeBtn.setOnClickListener {
                listStatus.gone()
                homeStatus.visible()
                homeHeader.text = "INICIO"
            }
        }
    }

    private fun initObservers() {
        with(binding) {
            viewModel.productList.observe(viewLifecycleOwner) {
                homeStatus.gone()
                adapter.submitList(it) {
                    progressBar.gone()
                    scroll.scrollY = 0
                    listStatus.visible()
                }
            }
            viewModel.errorMessage.observe(viewLifecycleOwner) {
                Log.e("HomeFragment", it)
            }
        }
    }

    private fun showProductList(type: ProductType) {
        with(binding) {
            homeStatus.invisible()
            progressBar.visible()
            viewModel.getProducts(type)
            homeHeader.text = type.title
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                with(binding) {
                    if (homeStatus.isGone) {
                        homeStatus.visible()
                        listStatus.gone()
                    } else {
                        activity?.finish()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}