package com.kay.coin.view

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.util.AdapterListUpdateCallback
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kay.appdb.coin.CoinItemViewModel
import com.kay.coin.R
import com.kay.core.ui.ItemInsertedAwareCallback
import com.kay.core.ui.ListDiffer
import com.kay.core.utils.inflate
import kotlinx.android.synthetic.main.item_view_coin.view.*

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinAdapter(private val firstInserted: (Int) -> Unit) : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    @Suppress("LeakingThis")
    private val itemInsertedCallback = ItemInsertedAwareCallback(AdapterListUpdateCallback(this))

    private val listDiffer = ListDiffer(itemInsertedCallback, DIFF_ITEM_CALLBACK).apply {
        onFirstInserted = firstInserted
    }

    fun submitList(list: List<CoinItemViewModel>) = listDiffer.submitList(list)

    fun getItem(position: Int): CoinItemViewModel {
        return listDiffer.getItem(position)
    }

    override fun getItemCount(): Int {
        return listDiffer.getItemCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(parent.inflate(R.layout.item_view_coin))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int, payloads: MutableList<Any>) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }

        for (payload in payloads) {
            when (payload) {
                PRICE_UP -> holder.priceUp(getItem(position))
                PRICE_DOWN -> holder.priceDown(getItem(position))
            }
        }
    }

    inner class CoinViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var animator: ValueAnimator? = null

        private fun getAnimator(from: Int, to: Int) =
                ValueAnimator.ofObject(ArgbEvaluator(), from, to, from).apply {
                    val background = itemView.background as ColorDrawable
                    duration = 1000 // milliseconds.
                    addUpdateListener { animator -> background.color = animator.animatedValue as Int }
                }

        private val sellColorAnimation by lazy {
            getAnimator(Color.WHITE, COLOR_SELL)
        }


        private val buyColorAnimation by lazy {
            getAnimator(Color.WHITE, COLOR_BUY)

        }


        fun bindTo(coin: CoinItemViewModel?) = with(itemView) {
            coin?.apply {
                tv_item_coin_long_name.text = longName
                tv_item_coin_short_name.text = shortName
                with(tv_item_coin_price) {
                    text = context.resources.getString(R.string.dollar_sign_format, price)
//                    setTextColor(Color.BLACK)
                }
            }

        }

        fun priceUp(coin: CoinItemViewModel?) = with(itemView) {
            coin?.apply {
                with(tv_item_coin_price) {
                    text = context.resources.getString(R.string.dollar_sign_format, price)
                    setTextColor(COLOR_SELL)
                }
            }
        }

        fun priceDown(coin: CoinItemViewModel?) = with(itemView) {
            coin?.apply {

                with(tv_item_coin_price) {
                    text = context.resources.getString(R.string.dollar_sign_format, price)
                    setTextColor(COLOR_BUY)
                }
            }
        }
    }


    companion object {

        val PRICE_UP = Any()
        val PRICE_DOWN = Any()

        val COLOR_BUY = Color.parseColor("#34bc7d")
        val COLOR_SELL = Color.parseColor("#ef7c7c")

        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<CoinItemViewModel>() {
            override fun areItemsTheSame(oldItem: CoinItemViewModel, newItem: CoinItemViewModel): Boolean {
                return oldItem.shortName == newItem.shortName
            }

            override fun areContentsTheSame(oldItem: CoinItemViewModel, newItem: CoinItemViewModel): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: CoinItemViewModel, newItem: CoinItemViewModel): Any {
                return when {
                    oldItem.price < newItem.price -> PRICE_UP
                    else -> PRICE_DOWN
                }
            }
        }
    }


}