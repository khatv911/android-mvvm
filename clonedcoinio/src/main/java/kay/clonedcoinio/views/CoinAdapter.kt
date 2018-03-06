package kay.clonedcoinio.views

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.kay.core.extension.inflate
import kay.clonedcoinio.R
import kay.clonedcoinio.models.entities.Coin
import kotlinx.android.synthetic.main.item_view_coin.view.*

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinAdapter : ListAdapter<Coin, CoinAdapter.CoinViewHolder>(DIFF_CALLBACK) {

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

        fun bindTo(coin: Coin) = with(itemView) {
            tv_item_coin_long_name.text = coin.longName ?: "N/A"
            tv_item_coin_short_name.text = coin.shortName ?: "N/A"
            tv_item_coin_price.text = "\$%f".format(coin.price)
        }

        fun priceUp(coin: Coin) = with(itemView) {

            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), COLOR_SELL, Color.WHITE, COLOR_SELL)
            val background = itemView?.background as GradientDrawable
            colorAnimation.duration = 1500 // milliseconds
            colorAnimation.addUpdateListener { animator -> background.setColor(animator.animatedValue as Int) }
            colorAnimation.start()

            tv_item_coin_price.text = "\$%f".format(coin.price)
            tv_item_coin_price.setTextColor(COLOR_SELL)
        }

        fun priceDown(coin: Coin) = with(itemView) {

            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), COLOR_BUY, Color.WHITE, COLOR_BUY)
            val background = itemView?.background as GradientDrawable
            colorAnimation.duration = 1500 // milliseconds
            colorAnimation.addUpdateListener { animator -> background.setColor(animator.animatedValue as Int) }
            colorAnimation.start()

            tv_item_coin_price.text = "\$%f".format(coin.price)
            tv_item_coin_price.setTextColor(COLOR_BUY)
        }
    }


    companion object {

        val PRICE_UP = Any()
        val PRICE_DOWN = Any()

        val COLOR_BUY = Color.parseColor("#81eab9")
        val COLOR_SELL = Color.parseColor("#ef7c7c")

        val DIFF_CALLBACK = CoinDiffItemCallback()

        class CoinDiffItemCallback : DiffUtil.ItemCallback<Coin>() {

            override fun getChangePayload(oldItem: Coin?, newItem: Coin?): Any? {
                return when {
                    oldItem == newItem -> null
                    oldItem!!.price < newItem!!.price -> PRICE_UP
                    else -> PRICE_DOWN
                }
            }

            override fun areItemsTheSame(oldItem: Coin?, newItem: Coin?): Boolean {
                return oldItem?.shortName == newItem?.shortName
            }

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem.price == newItem.price
            }
        }
    }


}