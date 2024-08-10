package ladyaev.development.myFirstFinance.feature.setupUser.presentation.chooseCountry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.input.SearchTextField
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.controls.toolbar.Toolbar
import ladyaev.development.myFirstFinance.core.ui.effects.FirstTimeSideEffect
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.dialogs.DefaultErrorDialog
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun ChooseCountryScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: ChooseCountryViewModel.Base = viewModel(factory = viewModelFactory())
) {
    FirstTimeSideEffect { firstTime ->
        viewModel.initialize(firstTime, Unit)
    }

    SingleLiveEffect(transmission = viewModel.effect) {
        when (it) {
            is ChooseCountryViewModel.UiEffect.Navigation -> {
                handleNavigationEvent(it.navigationEvent)
            }
            is ChooseCountryViewModel.UiEffect.ShowErrorMessage -> {

            }
        }
    }

    val state by viewModel.state.observeAsState(ChooseCountryViewModel.UiState())

    CustomScaffold(
        toolbar = {
            Toolbar(
                title = stringResource(id = R.string.chooseCountry_title),
                onNavigationButtonClick = {
                    viewModel.on(ChooseCountryViewModel.UserEvent.ToolbarBackButtonClick)
                }
            )
        },
        contentScrollable = false,
        useContentHorizontalPadding = false,
        content = {
            Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                SearchTextField(
                    text = state.query,
                    onTextChanged = {
                        viewModel.on(ChooseCountryViewModel.UserEvent.QueryChanged(it))
                    },
                    onSearchButtonClick = {}
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (state.loadingData) {
                CircularProgressIndicator(color = AppColors.black)
            }
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.countries, key = { it.country.name }) { item: ChooseCountryViewModel.CountryItem ->
                    CountryItem(
                        flagImageUrl = item.country.flagPath,
                        title = item.country.name,
                        code = item.country.phoneNumberCode,
                        isSelected = item.chosen,
                        onClick = {
                            viewModel.on(ChooseCountryViewModel.UserEvent.CountryChosen(item.country))
                        }
                    )
                }
            }
        }
    )

    DefaultErrorDialog(state = state.errorState) {
        viewModel.on(ChooseCountryViewModel.UserEvent.ErrorDialogDismiss)
    }
}