package com.coolhabit.mocktv.baseUI.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.coolhabit.mocktv.baseUI.model.NavCommand
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment(resource: Int) : Fragment(resource) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.viewModels() =
        createViewModelLazy(VM::class, { this.viewModelStore }, { viewModelFactory })

    protected val mainActivity: AppCompatActivity?
        get() = activity as? AppCompatActivity

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        withViewModel().apply {
            onNavigationCommands()
        }
    }

    abstract fun withViewModel(): BaseViewModel

    private fun BaseViewModel.onNavigationCommands() {

        this.navigationCommand.collectWithState { action ->
            when (action) {
                is NavCommand.Navigate -> findNavController().navigate(
                    action.directions,
                )
                is NavCommand.Deeplink -> {
                    if (action.backTo > 0) {
                        findNavController().popBackStack(action.backTo, true)
                    }
                    findNavController().navigate(action.deeplinkRequest)
                }
                is NavCommand.GoBack -> {
                    action.backTo.takeIf { it > 0 }?.let {
                        val result = !findNavController().popBackStack(it, false)
                        if (result) {
                            forceApplicationFinish()
                        }
                    } ?: kotlin.run {
                        if (!findNavController().popBackStack()) {
                            forceApplicationFinish()
                        }
                    }
                }
            }
        }
    }

    protected fun <T> Flow<T>.collectWithState(getData: (T) -> Unit) {
        val currentFlow = this
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                currentFlow.collect {
                    getData(it)
                }
            }
        }
    }

    private fun forceApplicationFinish() {
        requireActivity().finish()
    }
}
