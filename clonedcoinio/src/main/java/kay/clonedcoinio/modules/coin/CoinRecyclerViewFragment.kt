package kay.clonedcoinio.modules.coin

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.kay.core.resolver.DefaultResolution
import com.kay.core.resolver.Resolution
import com.kay.core.utils.inject
import com.kay.core.simple.SimpleRecyclerViewFragment
import com.kay.core.utils.ItemHandler
import com.kay.core.utils.Retriable
import kay.clonedcoinio.R
import kay.clonedcoinio.models.entities.Coin
import kay.clonedcoinio.resolver.FcsUiResolver

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinRecyclerViewFragment : SimpleRecyclerViewFragment<List<Coin>, CoinListViewModel>(), Retriable {


    internal class CoinClickHandler : ItemHandler<Coin> {
        override fun invoke(p1: Coin) {
            //
        }
    }

    internal class ToggleFavorite : ItemHandler<Coin> {
        override fun invoke(p1: Coin) {

        }
    }


    /**
     * Composition over inheritance
     */
    override fun getResolution(): Resolution {
        return DefaultResolution(mutableListOf(FcsUiResolver(this)))
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
                    if (position == parent.adapter?.itemCount?.minus(1)) {
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
        mAdapter.submitList(t)
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

    companion object {
        fun newInstance() = CoinRecyclerViewFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}