package kay.clonedcoinio.views

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.kay.core.extension.inject
import com.kay.core.simple.SimpleListFragment
import com.kay.core.utils.ItemHandler
import com.kay.core.utils.Retriable
import kay.clonedcoinio.R
import kay.clonedcoinio.models.entities.Coin
import kay.clonedcoinio.viewmodels.CoinListViewModel

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinListFragment : SimpleListFragment<List<Coin>, CoinListViewModel>(), Retriable {


    internal class CoinClickHandler : ItemHandler<Coin> {
        override fun invoke(p1: Coin) {
            //
        }
    }

    internal class ToggleFavorite : ItemHandler<Coin> {
        override fun invoke(p1: Coin) {

        }
    }


    private val mAdapter = CoinAdapter()

    override fun getViewModel(): CoinListViewModel = VIEW_MODEL_FACTORY.inject(this, CoinListViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.fragment_master

    override fun getActionBarTitle(): String = "Cloned Coin Cap"

    override fun setupRecyclerView() {


        mRecyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(object : DividerItemDecoration(context, LinearLayoutManager.VERTICAL) {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    val position = parent.getChildAdapterPosition(view)
                    // hide the divider for the last child
                    if (position == parent.adapter.itemCount - 1) {
                        outRect.setEmpty()
                    } else {
                        super.getItemOffsets(outRect, view, parent, state)
                    }
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.getAllCoins()
    }


    override fun onDataChanged(t: List<Coin>) {
        mAdapter.update(t)
    }

    /**
     *
     */
    override fun retry() {
        mViewModel.retry()
    }

    /**
     *
     */
    override fun doOnRefresh() {
        mViewModel.refresh()
    }
}