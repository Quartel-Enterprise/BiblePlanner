package com.quare.bibleplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.quare.bibleplanner.core.model.route.MaterialYouBottomSheetNavRoute
import com.quare.bibleplanner.feature.materialyou.presentation.component.MaterialYouBottomSheet
import com.quare.bibleplanner.feature.materialyou.presentation.model.AndroidColorSchemeUiAction
import com.quare.bibleplanner.feature.materialyou.presentation.viewmodel.AndroidColorSchemeViewModel
import com.quare.bibleplanner.ui.utils.ActionCollector
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val androidColorSchemeViewModel: AndroidColorSchemeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDynamicColorsOn by androidColorSchemeViewModel.uiState.collectAsState()
            val onEvent = androidColorSchemeViewModel::onEvent
            val navHostController = rememberNavController()
            val materialYouBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            MaterialYouActionCollector(navHostController, materialYouBottomSheetState)
            App(
                getSpecificColors = { isAppInDarkTheme ->
                    enableEdgeToEdge(statusBarStyle = getStatusBarStyle(isAppInDarkTheme))
                    getAndroidSpecificColorScheme(
                        isDynamicColorsOn = isDynamicColorsOn,
                        isAppInDarkTheme = isAppInDarkTheme,
                    )
                },
                extraRoute = { navGraphBuilder ->
                    navGraphBuilder.dialog<MaterialYouBottomSheetNavRoute> {
                        MaterialYouBottomSheet(
                            sheetState = materialYouBottomSheetState,
                            isMaterialYouActivated = isDynamicColorsOn,
                            onEvent = onEvent,
                        )
                    }
                },
            )
        }
    }

    @Composable
    private fun getStatusBarStyle(isAppInDarkTheme: Boolean): SystemBarStyle = SystemBarStyle.run {
        val color = Color.Transparent.toArgb()
        if (isAppInDarkTheme) {
            dark(color)
        } else {
            light(color, color)
        }
    }

    @Composable
    private fun MaterialYouActionCollector(
        navHostController: NavHostController,
        materialYouBottomSheetState: SheetState,
    ) {
        ActionCollector(androidColorSchemeViewModel.uiAction) { uiAction ->
            when (uiAction) {
                AndroidColorSchemeUiAction.CloseBottomSheet -> {
                    if (materialYouBottomSheetState.isVisible) {
                        materialYouBottomSheetState.hide()
                    }
                    navHostController.navigateUp()
                }
            }
        }
    }
}
