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
import com.kay.core.utils.LoadingState
import com.kay.core.viewmodel.AbsBaseViewModel
import com.kay.core.viewmodel.HasViewModel
import com.kay.core.viewmodel.LifecycleOwnerExt
import com.kay.core.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * Created by KhaTran on 31/1/18.
 */
abstract class AbsVMBaseFragment<T, VM : AbsBaseViewModel<T>> : AbsBaseFragment(), LifecycleOwnerExt<T>, HasViewModel<T, VM> {


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

    /**
     * Each fragment should have its own ViewModel
     */
    protected lateinit var mViewModel: VM


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

        mViewModel = getViewModel().apply {
            setup(this@AbsVMBaseFragment)
        }

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
}