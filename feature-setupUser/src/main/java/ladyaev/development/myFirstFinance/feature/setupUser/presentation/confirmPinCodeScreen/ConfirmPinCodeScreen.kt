package ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmPinCodeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.pinCodeKeyboard.PinCodeKeyboard
import ladyaev.development.myFirstFinance.core.ui.controls.progress.pinCodeProgress.PinCodeProgressView
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.controls.toolbar.Toolbar
import ladyaev.development.myFirstFinance.core.ui.dialogs.DefaultErrorDialog
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmPinCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun ConfirmPinCodeScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    arguments: ConfirmPinCodeScreenArguments,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: ConfirmPinCodeViewModel.Base = viewModel(factory = viewModelFactory())
) {
    LaunchedEffect(arguments.pinCode) {
        viewModel.initialize(Code(arguments.pinCode))
    }

    val focusManager = LocalFocusManager.current
    SingleLiveEffect(transmission = viewModel.effect) {
        when (it) {
            is UiEffect.Navigation -> {
                handleNavigationEvent(it.navigationEvent)
            }
            UiEffect.HideKeyboard -> {
                focusManager.clearFocus(true)
            }
        }
    }

    val state by viewModel.state.observeAsState(ConfirmPinCodeViewModel.UiState())

    CustomScaffold(
        horizontalAlignment = Alignment.CenterHorizontally,
        toolbar = {
            Toolbar(
                title = "",
                onNavigationButtonClick = {
                    viewModel.on(ConfirmPinCodeViewModel.UserEvent.ToolbarBackButtonClick)
                }
            )
        },
        content = {
            Text(
                text = stringResource(id = R.string.confirmPinCode_title),
                style = MaterialTheme.typography.titleLarge,
                letterSpacing = -(3.sp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))

            PinCodeProgressView(
                markers = state.codeMarkers
            )

            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (state.progressbarVisible) {
                    CircularProgressIndicator(color = AppColors.black)
                }
            }

            PinCodeKeyboard(
                onKeyTapped = {
                    viewModel.on(ConfirmPinCodeViewModel.UserEvent.DigitalKeyPressed(it))
                }
            )
        }
    )

    DefaultErrorDialog(state = state.errorState) {
        viewModel.on(ConfirmPinCodeViewModel.UserEvent.ErrorDialogDismiss)
    }
}