package ladyaev.development.myFirstFinance.feature.setupUser.presentation.residenceAddress

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.button.ActionButton
import ladyaev.development.myFirstFinance.core.ui.controls.container.AnimatedVisibilityContainer
import ladyaev.development.myFirstFinance.core.ui.controls.input.CustomTextField
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.controls.space.ExpandedSpacer
import ladyaev.development.myFirstFinance.core.ui.controls.toolbar.AnimatedTitleToolbar
import ladyaev.development.myFirstFinance.core.ui.effects.FirstTimeSideEffect
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.dialogs.DefaultErrorDialog
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ResidenceAddressScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.models.toEntity
import ladyaev.development.myFirstFinance.core.ui.theme.AppTheme

@Composable
fun ResidenceAddressScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    arguments: ResidenceAddressScreenArguments,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: ResidenceAddressViewModel.Base = viewModel(factory = viewModelFactory())
) {
    FirstTimeSideEffect { firstTime ->
        viewModel.initialize(firstTime, arguments.chosenCountry?.toEntity())
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

    var titleCollapsed by remember { mutableStateOf(false) }

    val state by viewModel.state.observeAsState(ResidenceAddressViewModel.UiState())
    val contentScrollState = rememberScrollState()

    val scope = rememberCoroutineScope()

    CustomScaffold(
        onClick = {
            titleCollapsed = false
            focusManager.clearFocus()
        },
        toolbar = {
            AnimatedTitleToolbar(
                title = stringResource(id = R.string.residenceAddress_title),
                titleVisible = titleCollapsed,
                onNavigationButtonClick = {
                    viewModel.on(ResidenceAddressViewModel.UserEvent.ToolbarBackButtonClick)
                }
            )
        },
        contentScrollState = contentScrollState,
        content = {
            AnimatedVisibilityContainer(
                contentVisible = !titleCollapsed,
                content = {
                    Text(
                        text = stringResource(id = R.string.residenceAddress_title),
                        style = MaterialTheme.typography.titleLarge,
                        letterSpacing = -(3.sp)
                    )
                },
                animationListener = {
                    if (titleCollapsed) {
                        scope.launch {
                            contentScrollState.animateScrollBy(contentScrollState.maxValue.toFloat())
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.birthDate_subtitle))
            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                text = state.countryName,
                enabled = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                text = state.city,
                placeholder = stringResource(id = R.string.residenceAddress_city_hint),
                onTextChanged = {
                    viewModel.on(ResidenceAddressViewModel.UserEvent.CityChanged(it))
                },
                onFocusChanged = {
                    titleCollapsed = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                text = state.street,
                placeholder = stringResource(id = R.string.residenceAddress_street_hint),
                onTextChanged = {
                    viewModel.on(ResidenceAddressViewModel.UserEvent.StreetChanged(it))
                },
                onFocusChanged = {
                    titleCollapsed = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                text = state.buildingNumber,
                placeholder = stringResource(id = R.string.residenceAddress_buildingNumber_hint),
                onTextChanged = {
                    viewModel.on(ResidenceAddressViewModel.UserEvent.BuildingNumberChanged(it))
                },
                onFocusChanged = {
                    titleCollapsed = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                text = state.apartment,
                placeholder = stringResource(id = R.string.residenceAddress_apartment_hint),
                onTextChanged = {
                    viewModel.on(ResidenceAddressViewModel.UserEvent.ApartmentChanged(it))
                },
                onFocusChanged = {
                    titleCollapsed = it
                }
            )

            ExpandedSpacer(minHeight = 16.dp)
            ActionButton(
                onclick = {
                    viewModel.on(ResidenceAddressViewModel.UserEvent.NextButtonClick)
                },
                text = stringResource(id = R.string.next),
                buttonColors = AppTheme.buttonTheme.primary,
                enabled = state.nextButtonEnabled,
                progressbarVisible = state.progressbarVisible
            )
        }
    )

    DefaultErrorDialog(state = state.errorState) {
        viewModel.on(ResidenceAddressViewModel.UserEvent.ErrorDialogDismiss)
    }
}
