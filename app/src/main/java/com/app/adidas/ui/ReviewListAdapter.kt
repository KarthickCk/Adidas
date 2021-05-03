package com.app.adidas.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.adidas.BR
import com.app.adidas.R
import com.app.adidas.domain.Product
import com.app.adidas.utils.DataBindingViewHolder
import com.app.adidas.utils.RxRecyclerAdapter

class ReviewListAdapter: RxRecyclerAdapter<Product.Review, DataBindingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binder = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_review_item,
            parent,
            false
        )
        return DataBindingViewHolder(binder)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.binding.setVariable(BR.data, list[position])
    }
}