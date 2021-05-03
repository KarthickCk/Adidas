package com.app.adidas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.adidas.R
import com.app.adidas.databinding.ActivityMainBinding
import com.app.adidas.domain.Product
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsActivity : AppCompatActivity(), OnProductClick {

    private val productsViewModel by viewModel<ProductsViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        lifecycle.addObserver(productsViewModel)
        setAdapter()
        observeData()
    }

    private fun setAdapter() {
        productAdapter = ProductsListAdapter(this)
        binding.productList.apply {
            layoutManager = LinearLayoutManager(
                this@ProductsActivity,
                RecyclerView.VERTICAL,
                false
            )
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = productAdapter
        }
    }

    private fun observeData() {
        productsViewModel.uiState.observe(this, Observer {
            if (it is ProductsViewModel.UIState.Products) {
                Observable.just(it.list)
                    .compose(productAdapter.diffList())
                    .subscribe()
            } else if (it is ProductsViewModel.UIState.Error) {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onProductClick(product: Product) {
        val intent = ProductDetailsActivity.newIntent(product, this)
        startActivity(intent)
    }
}