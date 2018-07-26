package com.igorvd.baseproject.features

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.igorvd.baseproject.utils.SingleLiveEvent
import com.igorvd.baseproject.utils.extensions.throwOrLog

/**
 * @author Igor Vilela
 * @since 28/12/17
 */
abstract class BaseViewModel() : ViewModel() {

    val showProgressEvent = SingleLiveEvent<Void>()
    val hideProgressEvent = SingleLiveEvent<Void>()

    /**
     * This method should be used when we the view model is asked to do some long running task.
     * Because we're running a long task, the user should see a progress indicator, and when the
     * job completes we need to remove the progress. This method is useful to avoid duplication of
     * this process
     *
     * param[work] a function to be executed between the showProgress and hideProgress events
     */
    protected suspend fun doWorkWithProgress(work: suspend () -> Unit) {

        showProgressEvent.call()
        try {
            work()
            /*} catch(e: DomainIOException) {

                val genesisMessage = genesisMessageFactory.buildGenesisMessage(GeneralMessages.IO_ERROR)
                showMessageEvent.value = genesisMessage

            } catch (e: DomainServerUnreachedException) {

                val genesisMessage = genesisMessageFactory.buildGenesisMessage(GeneralMessages.SERVER_UNREACHED)
                showMessageEvent.value = genesisMessage*/

        } catch (e: Exception) {
            e.throwOrLog()
        } finally {
            hideProgressEvent.call()
        }
    }

    fun observe(
        owner: LifecycleOwner, showProgress: () -> Unit, hideProgress: () -> Unit
    ) {

        showProgressEvent.observe(owner, showProgress)
        hideProgressEvent.observe(owner, hideProgress)
    }
}

