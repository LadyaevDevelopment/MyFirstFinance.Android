package ladyaev.development.myFirstFinance.feature.setupUser.presentation.email

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.bottomSheet.BottomSheetDialog
import ladyaev.development.myFirstFinance.core.ui.controls.button.ActionButton
import ladyaev.development.myFirstFinance.core.ui.controls.input.CustomTextField
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.controls.space.ExpandedSpacer
import ladyaev.development.myFirstFinance.core.ui.controls.toolbar.Toolbar
import ladyaev.development.myFirstFinance.core.ui.effects.FirstTimeSideEffect
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.dialogs.DefaultErrorDialog
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.theme.AppTheme

@Composable
fun EmailScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: EmailViewModel.Base = viewModel(factory = viewModelFactory())
) {
    val focusManager = LocalFocusManager.current

    FirstTimeSideEffect { firstTime ->
        viewModel.initialize(firstTime)
    }

    SingleLiveEffect(transmission = viewModel.effect) {
        when (it) {
            is EmailViewModel.UiEffect.Navigation -> {
                handleNavigationEvent(it.navigationEvent)
            }
            is EmailViewModel.UiEffect.ShowErrorMessage -> {

            }
            EmailViewModel.UiEffect.HideKeyboard -> {
                focusManager.clearFocus(true)
            }
        }
    }

    val state by viewModel.state.observeAsState(EmailViewModel.UiState())

    CustomScaffold(
        toolbar = {
            Toolbar(
                title = "",
                onNavigationButtonClick = {
                    viewModel.on(EmailViewModel.UserEvent.ToolbarBackButtonClick)
                }
            )
        },
        content = {
            Text(
                text = stringResource(id = R.string.email_title),
                style = MaterialTheme.typography.titleLarge,
                letterSpacing = -(3.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.birthDate_subtitle))
            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                text = state.email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onTextChanged = {
                    viewModel.on(EmailViewModel.UserEvent.EmailChanged(it))
                },
            )
            ExpandedSpacer(minHeight = 16.dp)
            ActionButton(
                onclick = {
                    viewModel.on(EmailViewModel.UserEvent.NextButtonClick)
                },
                text = stringResource(id = R.string.next),
                buttonColors = AppTheme.buttonTheme.primary,
                enabled = state.nextButtonEnabled
            )
        }
    )

    BottomSheetDialog(
        showBottomSheet = state.bottomSheetVisible,
        onCompletelyClosed = {
            viewModel.on(EmailViewModel.UserEvent.EmailBottomSheetDismiss)
        },
        content = {
            EmailBottomSheetContent(state.email) {
                viewModel.on(EmailViewModel.UserEvent.EmailBottomSheetNextButtonClick)
            }
        }
    )

    DefaultErrorDialog(state = state.errorState) {
        viewModel.on(EmailViewModel.UserEvent.ErrorDialogDismiss)
    }
}