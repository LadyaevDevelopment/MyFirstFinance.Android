package ladyaev.development.myFirstFinance.feature.setupUser.presentation.birthDate

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.button.ActionButton
import ladyaev.development.myFirstFinance.core.ui.controls.datePicker.datePickerDialog
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
fun BirthDateScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: BirthDateViewModel.Base = viewModel(factory = viewModelFactory())
) {
    val state by viewModel.state.observeAsState(BirthDateViewModel.UiState())

    val context = LocalContext.current
    val datePickerDialog = remember {
        datePickerDialog(context, state.date) {
            viewModel.on(BirthDateViewModel.UserEvent.DateChanged(it))
        }
    }

    FirstTimeSideEffect { firstTime ->
        viewModel.initialize(firstTime, Unit)
    }

    SingleLiveEffect(transmission = viewModel.effect) {
        when (it) {
            is BirthDateViewModel.UiEffect.Navigation -> {
                handleNavigationEvent(it.navigationEvent)
            }
            BirthDateViewModel.UiEffect.ShowDatePickerDialog -> {
                datePickerDialog.show()
            }
        }
    }

    CustomScaffold(
        toolbar = {
            Toolbar(
                title = "",
                onNavigationButtonClick = {
                    viewModel.on(BirthDateViewModel.UserEvent.ToolbarBackButtonClick)
                }
            )
        },
        content = {
            Text(
                text = stringResource(id = R.string.birthDate_title),
                style = MaterialTheme.typography.titleLarge,
                letterSpacing = -(3.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.birthDate_subtitle))
            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                text = state.displayedDate,
                enabled = false,
                onClick = {
                    viewModel.on(BirthDateViewModel.UserEvent.DateInputClick)
                },
            )
            ExpandedSpacer(minHeight = 16.dp)
            ActionButton(
                onclick = {
                    viewModel.on(BirthDateViewModel.UserEvent.NextButtonClick)
                },
                text = stringResource(id = R.string.next),
                buttonColors = AppTheme.buttonTheme.primary,
                enabled = state.nextButtonEnabled
            )
        }
    )

    DefaultErrorDialog(state = state.errorState) {
        viewModel.on(BirthDateViewModel.UserEvent.ErrorDialogDismiss)
    }
}