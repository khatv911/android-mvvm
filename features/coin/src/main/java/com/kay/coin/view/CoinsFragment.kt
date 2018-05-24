package com.kay.coin.view

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.kay.appdb.coin.CoinItemViewModel
import com.kay.coin.R
import com.kay.core.resolver.DefaultResolution
import com.kay.core.resolver.DefaultUiResolver
import com.kay.core.simple.SimpleRecyclerViewFragment
import com.kay.core.utils.Retriable
import com.kay.core.utils.inject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_master.*
import java.util.concurrent.TimeUnit


/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class CoinsFragment : SimpleRecyclerViewFragment<List<CoinItemViewModel>, CoinListViewModel>(), Retriable {


    private val searchViewDisposable = CompositeDisposable()

    private var mKeyword: String = ""

    private lateinit var searchView: SearchView


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(summary_toolbar)
    }

    /**
     * Composition over inheritance
     */
    override fun getResolution() = DefaultResolution(mutableListOf(DefaultUiResolver(this)))

    private val mAdapter = CoinAdapter(
            {
                view?.post {
                    base_recycler_view.smoothScrollToPosition(it)
                }
            }
    )

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

    private val expandListener = object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
            // Do something when action item collapses
            return true // Return true to collapse action view
        }

        override fun onMenuItemActionExpand(item: MenuItem): Boolean {
            // Do something when expanded
            return true // Return true to expand action view
        }
    }

    private fun setupSearchView(searchMenuItem: MenuItem?) {
        searchMenuItem?.setOnActionExpandListener(expandListener)
        searchView = searchMenuItem?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE;
        if (mKeyword.isNotEmpty()) {
            searchMenuItem.expandActionView()
            searchView.isIconified = false
            searchView.setQuery(mKeyword, true)

        }
        searchViewDisposable += RxSearchView.queryTextChanges(searchView)
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onQueryChange, this::onQueryError)
    }


    private fun onQueryError(e: Throwable) {
        uiResolution.resolve(e)
    }

    private fun onQueryChange(charSequence: CharSequence) {
        mKeyword = charSequence.toString()
        mViewModel.queryCoinsWithKeyword(mKeyword)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_coin, menu)
        val search = menu?.findItem(R.id.app_bar_search)
        setupSearchView(search)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_KEYWORD, mKeyword)
        super.onSaveInstanceState(outState)
    }


    /**
     * Dispose due to fragment lifecycle
     * https://www.techsfo.com/blog/wp-content/uploads/2014/08/complete_android_fragment_lifecycle.png
     */
    override fun onStop() {
        searchViewDisposable.clear()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            mKeyword = it.getString(STATE_KEYWORD, null)
        }
        mViewModel.queryCoinsWithKeyword(mKeyword)
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

    companion object {

        const val STATE_KEYWORD = "keyword"

        fun newInstance() = CoinsFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}