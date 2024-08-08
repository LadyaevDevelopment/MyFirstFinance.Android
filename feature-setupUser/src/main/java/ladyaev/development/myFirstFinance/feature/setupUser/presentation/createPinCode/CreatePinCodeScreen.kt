package ladyaev.development.myFirstFinance.feature.setupUser.presentation.createPinCode

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.pinCodeKeyboard.PinCodeKeyboard
import ladyaev.development.myFirstFinance.core.ui.controls.progress.pinCodeProgress.PinCodeProgressView
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.controls.space.ExpandedSpacer
import ladyaev.development.myFirstFinance.core.ui.controls.toolbar.Toolbar
import ladyaev.development.myFirstFinance.core.ui.effects.FirstTimeSideEffect
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent

@Composable
fun CreatePinCodeScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: CreatePinCodeViewModel.Base = viewModel(factory = viewModelFactory())
) {
    FirstTimeSideEffect { firstTime ->
        viewModel.initialize(firstTime)
    }

    SingleLiveEffect(transmission = viewModel.effect) {
        when (it) {
            is CreatePinCodeViewModel.UiEffect.Navigation -> {
                handleNavigationEvent(it.navigationEvent)
            }
        }
    }

    val state by viewModel.state.observeAsState(CreatePinCodeViewModel.UiState())

    CustomScaffold(
        horizontalAlignment = Alignment.CenterHorizontally,
        toolbar = {
            Toolbar(
                title = "",
                onNavigationButtonClick = {
                    viewModel.on(CreatePinCodeViewModel.UserEvent.ToolbarBackButtonClick)
                }
            )
        },
        content = {
            Text(
                text = stringResource(id = R.string.createPinCode_title),
                style = MaterialTheme.typography.titleLarge,
                letterSpacing = -(3.sp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))

            PinCodeProgressView(
                markers = state.codeMarkers
            )

            ExpandedSpacer(minHeight = 16.dp)

            PinCodeKeyboard(
                onKeyTapped = {
                    viewModel.on(CreatePinCodeViewModel.UserEvent.DigitalKeyPressed(it))
                }
            )
        }
    )
}