package ladyaev.development.myFirstFinance.feature.setupUser.presentation.confirmationCode

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.input.CodeInput
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.digitalKeyboard.DigitalKeyBoard
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.controls.space.ExpandedSpacer
import ladyaev.development.myFirstFinance.core.ui.controls.toolbar.Toolbar
import ladyaev.development.myFirstFinance.core.ui.effects.FirstTimeSideEffect
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.dialogs.DefaultErrorDialog
import ladyaev.development.myFirstFinance.core.ui.dialogs.InDevelopmentDialog
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.navigation.arguments.ConfirmationCodeScreenArguments
import ladyaev.development.myFirstFinance.core.ui.navigation.models.toEntity
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun ConfirmationCodeScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    arguments: ConfirmationCodeScreenArguments,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: ConfirmationCodeViewModel.Base = viewModel(factory = viewModelFactory())
) {
    FirstTimeSideEffect { firstTime ->
        viewModel.initialize(firstTime, arguments.phoneNumber.toEntity())
    }

    SingleLiveEffect(transmission = viewModel.effect) {
        when (it) {
            is ConfirmationCodeViewModel.UiEffect.Navigation -> {
                handleNavigationEvent(it.navigationEvent)
            }
            is ConfirmationCodeViewModel.UiEffect.ShowErrorMessage -> {

            }
        }
    }

    val state by viewModel.state.observeAsState(ConfirmationCodeViewModel.UiState())

    CustomScaffold(
        toolbar = {
            Toolbar(
                title = "",
                onNavigationButtonClick = {
                    viewModel.on(ConfirmationCodeViewModel.UserEvent.ToolbarBackButtonClick)
                }
            )
        },
        content = {
            Text(
                text = stringResource(id = R.string.confirmationCode_title),
                style = MaterialTheme.typography.titleLarge,
                letterSpacing = -(3.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                    append(stringResource(R.string.confirmationCode_sentTo))
                }
                append("   ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(state.phoneNumber)
                }
            }
            Text(text = annotatedText)

            Spacer(modifier = Modifier.height(24.dp))

            CodeInput(cells = state.codeCells)

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                if (state.requireCodeBtnEnabled) {
                    Text(
                        text = state.resendButtonText,
                        color = AppColors.blue,
                        modifier = Modifier.clickable {
                            viewModel.on(ConfirmationCodeViewModel.UserEvent.ResendButtonClick)
                        }
                    )
                } else {
                    if (state.requireCodeProgressbarVisible) {
                        Box(modifier = Modifier.size(24.dp)) {
                            CircularProgressIndicator(color = AppColors.blue)
                        }
                    } else {
                        Text(text = state.resendButtonText)
                    }
                }
                Spacer(modifier = Modifier
                    .width(16.dp)
                    .weight(1f)
                )
                Text(
                    text = stringResource(id = R.string.confirmationCode_support),
                    color = AppColors.blue,
                    modifier = Modifier.clickable {
                        viewModel.on(ConfirmationCodeViewModel.UserEvent.ContactSupportButtonClick)
                    }
                )
            }
            ExpandedSpacer(minHeight = 16.dp)
            DigitalKeyBoard(
                onKeyTapped = {
                    viewModel.on(ConfirmationCodeViewModel.UserEvent.DigitalKeyPressed(it))
                }
            )
        }
    )

    DefaultErrorDialog(state = state.errorState) {
        viewModel.on(ConfirmationCodeViewModel.UserEvent.ErrorDialogDismiss)
    }
    InDevelopmentDialog(visible = state.inDevelopmentDialogVisible) {
        viewModel.on(ConfirmationCodeViewModel.UserEvent.InDevelopmentDialogDismiss)
    }
}