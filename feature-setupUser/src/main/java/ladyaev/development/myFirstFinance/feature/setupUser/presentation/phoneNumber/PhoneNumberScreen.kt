package ladyaev.development.myFirstFinance.feature.setupUser.presentation.phoneNumber

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.button.ActionButton
import ladyaev.development.myFirstFinance.core.ui.controls.input.CustomTextField
import ladyaev.development.myFirstFinance.core.ui.controls.input.TextFieldButton
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.digitalKeyboard.DigitalKeyBoard
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.controls.space.ExpandedSpacer
import ladyaev.development.myFirstFinance.core.ui.controls.toolbar.Toolbar
import ladyaev.development.myFirstFinance.core.ui.effects.FirstTimeSideEffect
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.dialogs.DefaultErrorDialog
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.PhoneNumberScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.models.toEntity
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors
import ladyaev.development.myFirstFinance.core.ui.theme.AppTheme

@Composable
fun PhoneNumberScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    arguments: PhoneNumberScreenArguments,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: PhoneNumberViewModel.Base = viewModel(factory = viewModelFactory())
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

    val state by viewModel.state.observeAsState(PhoneNumberViewModel.UiState())

    CustomScaffold(
        toolbar = {
            Toolbar(
                title = "",
                onNavigationButtonClick = {
                    viewModel.on(PhoneNumberViewModel.UserEvent.ToolbarBackButtonClick)
                }
            )
        },
        content = {
            Text(
                text = stringResource(id = R.string.phoneNumber_title),
                style = MaterialTheme.typography.titleLarge,
                letterSpacing = -(3.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.phoneNumber_subtitle))
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(78.dp)
                        .height(64.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(AppColors.lightGray)
                        .clickable {
                            viewModel.on(PhoneNumberViewModel.UserEvent.CountryFlagClick)
                        }
                ) {
                    if (!state.loadingData)  {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(2.dp))
                            ) {
                                AsyncImage(
                                    model = state.flagPath,
                                    contentDescription = null,
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_arrow_down_small),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(6.dp)
                            )
                        }
                    }

                    if (state.loadingData) {
                        CircularProgressIndicator(color = AppColors.darkGray)
                    }
                }

                CustomTextField(
                    text = state.phoneNumber.toString(),
                    enabled = false,
                    trailingButton = TextFieldButton(R.drawable.ic_clear) {
                        viewModel.on(PhoneNumberViewModel.UserEvent.ClearPhoneNumberBtnClick)
                    }
                )
            }
            ExpandedSpacer(minHeight = 16.dp)
            ActionButton(
                onclick = {
                    viewModel.on(PhoneNumberViewModel.UserEvent.NextButtonClick)
                },
                text = stringResource(id = R.string.next),
                buttonColors = AppTheme.buttonTheme.primary,
                enabled = state.nextButtonEnabled
            )
            Spacer(modifier = Modifier.height(16.dp))
            DigitalKeyBoard(
                onKeyTapped = {
                    viewModel.on(PhoneNumberViewModel.UserEvent.DigitalKeyPressed(it))
                }
            )
        }
    )

    DefaultErrorDialog(state = state.errorState) {
        viewModel.on(PhoneNumberViewModel.UserEvent.ErrorDialogDismiss)
    }
}