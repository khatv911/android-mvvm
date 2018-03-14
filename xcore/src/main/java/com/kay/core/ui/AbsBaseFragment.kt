package com.kay.core.ui

import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.kay.core.R
import com.kay.core.error.DefaultResolution
import com.kay.core.error.DefaultUiResolver
import com.kay.core.error.Resolution
import com.kay.core.error.UiResolver
import com.kay.core.utils.CanSetTitle
import com.kay.core.utils.LoadingState
import com.kay.core.viewmodel.AbsBaseViewModel
import com.kay.core.viewmodel.LifecycleOwnerExt
import com.kay.core.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
abstract class AbsBaseFragment :DaggerFragment(), LifecycleOwnerExt {


    /**
     * a default resolver , lazy init
     */
    protected val defaultUiResolvers by lazy {
        val list: MutableList<UiResolver> = mutableListOf()
        list.add(DefaultUiResolver(this))
        list
    }

    /**
     * provide a resolution lazily.
     * fallback to default
     */
    private val uiResolution by lazy {
        getResolution() ?: DefaultResolution(defaultUiResolvers)
    }


    /**
     * Override this method to provide proper resolution
     */
    protected open fun getResolution(): Resolution? = null

    /**
     * The ViewModelFactory
     */
    @Inject
    lateinit var VIEW_MODEL_FACTORY: ViewModelFactory

    /**
     * The common loading view. it should respond to the ViewModel#mLoadingEvent
     * If you wish to have the ability to swipe&refresh,
     */
    protected lateinit var mLoadingView: ContentLoadingProgressBar

    /**
     * The content view, inflated from a single layout file
     */
    protected lateinit var mContentView: View




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_base, container, false) as FrameLayout
        val contentFragmentView = inflater.inflate(getLayoutId(), view, false)
        contentFragmentView.id = R.id.base_content
        view.addView(contentFragmentView, FrameLayout.LayoutParams(-1, -1)) // short for match_parent
        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLoadingView = view.findViewById(R.id.progress_bar)

        mContentView = view.findViewById(R.id.base_content)

    }


    override fun onLoadingStateChanged(loadingState: LoadingState?) = when (loadingState) {
        LoadingState.NORMAL -> mLoadingView.show()
        else -> mLoadingView.hide()
    }


    override fun onError(throwable: Throwable?) {
        throwable?.let {
            uiResolution.resolve(it)
        }
    }

    override fun onSuccess(message: String?) {}


    abstract fun getLayoutId(): Int

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            parseArguments(it)
        }
    }

    /**
     * Parse arguments, normally
     */
    protected open fun parseArguments(arguments: Bundle) {}

    /**
     *
     */
    abstract fun getActionBarTitle(): String

    override fun onResume() {
        super.onResume()
        if (activity is CanSetTitle) {
            (activity as CanSetTitle).setActionBarTitle(getActionBarTitle())
        }
    }
}