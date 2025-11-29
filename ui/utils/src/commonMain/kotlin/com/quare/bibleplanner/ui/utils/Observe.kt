package com.quare.bibleplanner.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Observes a [Flow] returned by a flow inside a [ViewModel], collecting its emissions
 * in the [viewModelScope] and executing the provided [collector] for each value.
 *
 * This extension is particularly useful when integrating flows that expose
 * reactive streams with the ViewModel layer. By launching the collection
 * in [viewModelScope], the observation is automatically cancelled when the ViewModel
 * is cleared, preventing memory leaks and redundant work.
 *
 * Example usage with a flow:
 * ```
 * class UserViewModel(
 *     private val getUserProfileUseCase: GetUserProfileUseCase
 * ) : ViewModel() {
 *
 *     init {
 *         observe(getUserProfileUseCase()) { profile ->
 *             // Update state with the latest user profile
 *         }
 *     }
 * }
 * ```
 *
 * @param flow The [Flow] returned to be observed.
 * @param collector A suspend function to handle each value emitted by the flow.
 */
fun <T> ViewModel.observe(
    flow: Flow<T>,
    collector: suspend (T) -> Unit,
) {
    flow.onEach(collector).launchIn(viewModelScope)
}
