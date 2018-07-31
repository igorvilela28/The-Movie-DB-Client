package com.igorvd.baseproject

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.igorvd.baseproject.features.BaseViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

/**
 *
 * @author Igor Vilela
 * @since 24/01/2018
 */
abstract class BaseViewModelTest<T: BaseViewModel> {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    protected val observerShowProgress = mockk<Observer<Void?>>(relaxed = true)
    protected val observerHideProgress = mockk<Observer<Void?>>(relaxed = true)
    protected val observerShowError = mockk<Observer<Int>>(relaxed = true)

    /**
     * You must overide the [setUp] method and initialize the view model BEFORE the super call
     */
    protected lateinit var mViewModel: T

    @Before
    open fun setUp() {

        every { observerShowProgress.onChanged(null) } just Runs
        every { observerHideProgress.onChanged(null) } just Runs
        every { observerShowError.onChanged(any()) } just Runs

        mViewModel.showProgressEvent.observeForever(observerShowProgress)
        mViewModel.hideProgressEvent.observeForever(observerHideProgress)
        mViewModel.showErrorEvent.observeForever(observerShowError)

    }

    @After
    open fun tearDown() {
        mViewModel.showProgressEvent.removeObserver(observerShowProgress)
        mViewModel.hideProgressEvent.removeObserver(observerHideProgress)
        mViewModel.showErrorEvent.removeObserver(observerShowError)

    }

    /**
     * Gera uma lista contendo itens identicos ao original. O tamanho é definido por [size]
     */
    protected fun <T> T.generateList(size: Int = 5) : List<T> {
        val list = arrayListOf<T>()
        for (i in 0 until size) {
            list.add(this)
        }
        return list
    }

}