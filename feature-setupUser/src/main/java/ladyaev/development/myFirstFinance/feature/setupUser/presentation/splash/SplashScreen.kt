package ladyaev.development.myFirstFinance.feature.setupUser.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.scaffold.CustomScaffold
import ladyaev.development.myFirstFinance.core.ui.effects.FirstTimeSideEffect
import ladyaev.development.myFirstFinance.core.ui.effects.SingleLiveEffect
import ladyaev.development.myFirstFinance.core.ui.navigation.NavigationEvent

@Composable
fun SplashScreen(
    viewModelFactory: () -> ViewModelProvider.Factory,
    handleNavigationEvent: (event: NavigationEvent) -> Unit,
    viewModel: SplashScreenViewModel.Base = viewModel(factory = viewModelFactory())
) {
    FirstTimeSideEffect { firstTime ->
        viewModel.initialize(firstTime)
    }

    SingleLiveEffect(transmission = viewModel.effect) {
        when (it) {
            is SplashScreenViewModel.UiEffect.Navigation -> {
                handleNavigationEvent(it.navigationEvent)
            }
        }
    }

    val birdComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bird_animation))
    val textComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.myffin_text_animation))

    CustomScaffold(
        toolbar = null,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(24.dp))
            LottieAnimation(
                birdComposition,
                restartOnPlay = false,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(40.dp))
            LottieAnimation(
                textComposition,
                contentScale = ContentScale.Crop,
                restartOnPlay = false,
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth()
            )
        }
    )
}