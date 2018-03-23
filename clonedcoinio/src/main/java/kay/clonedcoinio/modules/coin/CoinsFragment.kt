package kay.clonedcoinio.modules.coin

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import com.jakewharton.rxbinding2.widget.RxSearchView
import com.kay.core.resolver.DefaultResolution
import com.kay.core.simple.SimpleRecyclerViewFragment
import com.kay.core.utils.ItemHandler
import com.kay.core.utils.Retriable
import com.kay.core.utils.inject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kay.clonedcoinio.R
import kay.clonedcoinio.models.entities.Coin
import kay.clonedcoinio.models.entities.CoinItemViewModel
import kay.clonedcoinio.resolver.FcsUiResolver
import java.util.concurrent.TimeUnit


/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinsFragment : SimpleRecyclerViewFragment<List<CoinItemViewModel>, CoinListViewModel>(), Retriable {


    private lateinit var searchViewDisposable: Disposable

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
    override fun getResolution() = DefaultResolution(mutableListOf(FcsUiResolver(this)))

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_coin, menu)
        val search = menu?.findItem(R.id.app_bar_search)
        val searchView = search?.actionView as SearchView
        searchViewDisposable = RxSearchView.queryTextChanges(searchView)
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mViewModel.searchCoinsWithName(it.toString())
                }
        super.onCreateOptionsMenu(menu, inflater)
    }


    /**
     * Dispose due to fragment lifecycle
     * https://www.techsfo.com/blog/wp-content/uploads/2014/08/complete_android_fragment_lifecycle.png
     */
    override fun onPause() {
        searchViewDisposable.dispose()
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.getAllCoins()
    }


    override fun onDataChanged(t: List<CoinItemViewModel>) {
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


    override fun onDestroyView() {

        super.onDestroyView()
    }

    companion object {
        fun newInstance() = CoinsFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}