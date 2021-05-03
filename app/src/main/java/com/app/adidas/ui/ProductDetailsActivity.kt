package com.app.adidas.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.adidas.BR
import com.app.adidas.R
import com.app.adidas.databinding.ActivityProductDetailsBinding
import com.app.adidas.domain.Product
import io.reactivex.Observable

class ProductDetailsActivity : AppCompatActivity() {

    companion object {

        private const val PRODUCT_DATA = "product_data"

        fun newIntent(product: Product, context: Context): Intent {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra(PRODUCT_DATA, product)
            return intent
        }
    }

    private var product: Product? = null
    private val reviewAdapter: ReviewListAdapter = ReviewListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_product_details)
        product = intent?.getParcelableExtra(PRODUCT_DATA)
        product?.let {
            binding.setVariable(BR.data, it)
            binding.root.findViewById<RecyclerView>(R.id.review_list).apply {
                layoutManager = LinearLayoutManager(
                    this@ProductDetailsActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = reviewAdapter
                Observable.just(it.reviews)
                    .compose(reviewAdapter.diffList())
                    .subscribe()
            }
        }
    }
}