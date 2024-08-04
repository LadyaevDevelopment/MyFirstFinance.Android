package ladyaev.development.myFirstFinance.feature.setupUser.screens.completeRegistration

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.button.ActionButton
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.effects.FirstTimeSideEffect
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent
import ladyaev.development.myFirstFinance.core.ui.theme.AppTheme

@Composable
fun CompleteRegistrationScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: CompleteRegistrationViewModel.Base = viewModel(factory = viewModelFactory())
) {
    FirstTimeSideEffect { firstTime ->
        viewModel.initialize(firstTime)
    }

    SingleLiveEffect(transmission = viewModel.effect) {
        when (it) {
            is CompleteRegistrationViewModel.UiEffect.Navigation -> {
                handleNavigationEvent(it.navigationEvent)
            }
        }
    }

    val birdComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bird_animation))

    CustomScaffold(
        toolbar = null,
        contentScrollable = false,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.completeRegistration_title),
                style = MaterialTheme.typography.titleLarge,
                letterSpacing = -(3.sp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            LottieAnimation(
                birdComposition,
                restartOnPlay = false,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(
                onclick = {
                    viewModel.on(CompleteRegistrationViewModel.UserEvent.NextButtonClick)
                },
                text = stringResource(id = R.string.completeRegistration_next),
                buttonColors = AppTheme.buttonTheme.primary
            )
        }
    )
}