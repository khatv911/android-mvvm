package kay.clonedcoinio.views

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

    inner class CoinViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindTo(coin: Coin) = with(itemView) {
            tv_item_coin_long_name.text = coin.longName ?: "N/A"
            tv_item_coin_short_name.text = coin.shortName ?: "N/A"
            tv_item_coin_price.text = "%f".format(coin.price)
        }
    }


    companion object {
        val DIFF_CALLBACK = CoinDiffItemCallback()

        class CoinDiffItemCallback : DiffUtil.ItemCallback<Coin>() {

            override fun getChangePayload(oldItem: Coin?, newItem: Coin?): Any? {
                return null
            }

            override fun areItemsTheSame(oldItem: Coin?, newItem: Coin?): Boolean {
                return oldItem?.shortName == newItem?.shortName
            }

            override fun areContentsTheSame(oldItem: Coin?, newItem: Coin?): Boolean {
                return oldItem == newItem
            }
        }
    }


}