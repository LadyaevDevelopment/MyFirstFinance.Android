package ladyaev.development.myFirstFinance.feature.setupUser.presentation.startMenu

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.button.ActionButton
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.controls.space.ExpandedSpacer
import ladyaev.development.myFirstFinance.core.ui.dialogs.DefaultErrorDialog
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.effects.UiEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors
import ladyaev.development.myFirstFinance.core.ui.theme.AppTheme

@Composable
fun StartMenuScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: StartMenuViewModel.Base = viewModel(factory = viewModelFactory())
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(Unit)
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

    val state by viewModel.state.observeAsState(StartMenuViewModel.UiState())

    CustomScaffold(
        toolbar = null,
        contentScrollable = false,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Spacer(modifier = Modifier.height(32.dp))
            ExpandedSpacer(minHeight = 16.dp)
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = null
            )
            ExpandedSpacer(minHeight = 16.dp)

            if (state.loadingDocuments) {
                CircularProgressIndicator(color = AppColors.blue)
            } else {
                val annotatedText = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = AppColors.darkGray)) {
                        append(stringResource(R.string.startMenu_policyDocuments) + " ")
                    }
                    for ((index, document) in state.documents.withIndex()) {
                        pushStringAnnotation(tag = "URL", annotation = document.path)
                        withStyle(style = SpanStyle(color = AppColors.blue)) {
                            append(document.title)
                        }
                        pop()
                        withStyle(style = SpanStyle(color = AppColors.darkGray)) {
                            append(when {
                                index < state.documents.lastIndex - 1 -> ", "
                                index == state.documents.lastIndex - 1 -> " " + stringResource(R.string.and) + " "
                                else -> "."
                            })
                        }
                    }
                }
                val context = LocalContext.current
                ClickableText(
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                openCustomTab(context, annotation.item)
                            }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(
                onclick = {
                    viewModel.on(StartMenuViewModel.UserEvent.LoginBtnClick)
                },
                text = stringResource(id = R.string.startMenu_login),
                buttonColors = AppTheme.buttonTheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            ActionButton(
                onclick = {
                    viewModel.on(StartMenuViewModel.UserEvent.RegisterBtnClick)
                },
                text = stringResource(id = R.string.startMenu_register),
                buttonColors = AppTheme.buttonTheme.secondary
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        ExpandedSpacer(minHeight = 16.dp)
    }

    DefaultErrorDialog(state = state.errorState) {
        viewModel.on(StartMenuViewModel.UserEvent.ErrorDialogDismiss)
    }
}

private fun openCustomTab(context: Context, url: String) {
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}