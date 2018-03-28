package com.kay.core.ui

import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.view.postDelayed
import com.kay.core.R
import com.kay.core.resolver.Resolution
import com.kay.core.utils.CanSetTitle
import com.kay.core.utils.LoadingState
import com.kay.core.utils.LoadingState.Companion.NORMAL
import com.kay.core.viewmodel.LifecycleOwnerExt
import com.kay.core.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
abstract class AbsBaseFragment : DaggerFragment(), LifecycleOwnerExt {

    /**
     * provide a resolution lazily.
     * fallback to default
     */
    val uiResolution by lazy {
        getResolution()
    }


    /**
     * Override this method to provide proper resolution
     */
    abstract fun getResolution(): Resolution

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


    override fun onLoadingStateChanged(@LoadingState.Value loadingState: Int) {
        when (loadingState) {
            // have to use postDelayed to show the loading,
            // this will cause temporary leak.
            NORMAL -> {
                view?.postDelayed(100, { mLoadingView.show() })
            }
            else -> {
                view?.postDelayed(100, { mLoadingView.hide() })
            }
        }
    }


    override fun onError(throwable: Throwable?) {
        throwable?.let {
            uiResolution.resolve(it)
        }
    }

    override fun onSuccess(message: String?) {
        uiResolution.success(message)
    }


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
            (activity as CanSetTitle).setTitle(getActionBarTitle())
        }
    }

}