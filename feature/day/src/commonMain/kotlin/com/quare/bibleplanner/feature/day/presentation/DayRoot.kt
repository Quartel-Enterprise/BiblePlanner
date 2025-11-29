package com.quare.bibleplanner.feature.day.presentation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.quare.bibleplanner.core.model.route.DayNavRoute
import com.quare.bibleplanner.feature.day.presentation.viewmodel.DayViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.day(navController: NavController) {
    composable<DayNavRoute> {
        val viewModel = koinViewModel<DayViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        LaunchedEffect(Unit) {
            viewModel.backUiAction.collectLatest {
                navController.navigateUp()
            }
        }
        DayScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
        )
    }
}
