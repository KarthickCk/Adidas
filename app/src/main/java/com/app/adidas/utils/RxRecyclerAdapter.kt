package com.app.adidas.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import java.util.Collections

abstract class RxRecyclerAdapter<T1, T2 : RecyclerView.ViewHolder> : RecyclerView.Adapter<T2>() {

    internal var list: List<T1> = emptyList()

    override fun getItemCount() = list.size

    fun getItemAtPosition(position: Int) = list[position]

    @Throws(NullPointerException::class)
    fun setList(diffResult: DiffUtil.DiffResult, list: List<T1>?) {

        if (list == null) {
            return
        }
        this.list = Collections.unmodifiableList(ArrayList(list))
        diffResult.dispatchUpdatesTo(this)
    }

    fun setList(list: List<T1>?) {
        if (list == null) {
            return
        }

        this.list = Collections.unmodifiableList(ArrayList(list))
        this.notifyDataSetChanged()
    }

    /** Flowable is to reduce the load on calculate the diff*/

    fun diffList(): ObservableTransformer<List<T1>, List<T1>> {
        return ObservableTransformer {
            it.toFlowable(BackpressureStrategy.BUFFER)
                .distinctUntilChanged()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .concatMap { newList ->
                    val diffResult: DiffUtil.DiffResult? =
                        when (list.isEmpty()) {
                            false -> calculateDiff(list, newList)
                            true -> null
                        }

                    if (diffResult == null) {
                        return@concatMap Observable.just(newList)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext {
                                setList(newList)
                            }
                            .observeOn(Schedulers.io())
                    } else
                        return@concatMap Observable.just(diffResult)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext {
                                setList(diffResult, newList)
                            }
                            .observeOn(Schedulers.io())
                            .map {
                                newList
                            }
                }
        }
    }

    private fun calculateDiff(oldList: List<T1>, newList: List<T1>): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getNewListSize() = newList.size

            override fun getOldListSize() = oldList.size
        })
    }
}
