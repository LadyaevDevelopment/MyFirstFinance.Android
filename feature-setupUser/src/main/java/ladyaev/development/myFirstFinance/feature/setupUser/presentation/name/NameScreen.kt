package ladyaev.development.myFirstFinance.feature.setupUser.presentation.name

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.button.ActionButton
import ladyaev.development.myFirstFinance.core.ui.controls.container.AnimatedVisibilityContainer
import ladyaev.development.myFirstFinance.core.ui.controls.input.CustomTextField
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.controls.space.ExpandedSpacer
import ladyaev.development.myFirstFinance.core.ui.controls.toolbar.AnimatedTitleToolbar
import ladyaev.development.myFirstFinance.core.ui.effects.FirstTimeSideEffect
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.theme.AppTheme

@Composable
fun NameScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: NameViewModel.Base = viewModel(factory = viewModelFactory())
) {
    val focusManager = LocalFocusManager.current

    FirstTimeSideEffect { firstTime ->
        viewModel.initialize(firstTime, Unit)
    }

    SingleLiveEffect(transmission = viewModel.effect) {
        when (it) {
            is NameViewModel.UiEffect.Navigation -> {
                handleNavigationEvent(it.navigationEvent)
            }
            is NameViewModel.UiEffect.ShowErrorMessage -> {

            }
            NameViewModel.UiEffect.HideKeyboard -> {
                focusManager.clearFocus(true)
            }
        }
    }

    var titleCollapsed by remember { mutableStateOf(false) }

    val state by viewModel.state.observeAsState(NameViewModel.UiState())

    CustomScaffold(
        onClick = {
            titleCollapsed = false
            focusManager.clearFocus()
        },
        toolbar = {
            AnimatedTitleToolbar(
                title = stringResource(id = R.string.name_title),
                titleVisible = titleCollapsed,
                onNavigationButtonClick = {
                    viewModel.on(NameViewModel.UserEvent.ToolbarBackButtonClick)
                }
            )
        },
        content = {
            AnimatedVisibilityContainer(
                contentVisible = !titleCollapsed,
                content = {
                    Text(
                        text = stringResource(id = R.string.name_title),
                        style = MaterialTheme.typography.titleLarge,
                        letterSpacing = -(3.sp)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.birthDate_subtitle))
            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                text = state.lastName,
                placeholder = stringResource(id = R.string.name_placeholder_lastName),
                onTextChanged = {
                    viewModel.on(NameViewModel.UserEvent.LastNameChanged(it))
                },
                onFocusChanged = {
                    titleCollapsed = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                text = state.firstName,
                placeholder = stringResource(id = R.string.name_placeholder_firstName),
                onTextChanged = {
                    viewModel.on(NameViewModel.UserEvent.FirstNameChanged(it))
                },
                onFocusChanged = {
                    titleCollapsed = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                text = state.middleName,
                placeholder = stringResource(id = R.string.name_placeholder_middleName),
                onTextChanged = {
                    viewModel.on(NameViewModel.UserEvent.MiddleNameChanged(it))
                },
                onFocusChanged = {
                    titleCollapsed = it
                }
            )

            ExpandedSpacer(minHeight = 16.dp)
            ActionButton(
                onclick = {
                    viewModel.on(NameViewModel.UserEvent.NextButtonClick)
                },
                text = stringResource(id = R.string.next),
                buttonColors = AppTheme.buttonTheme.primary,
                enabled = state.nextButtonEnabled
            )
        }
    )
}
