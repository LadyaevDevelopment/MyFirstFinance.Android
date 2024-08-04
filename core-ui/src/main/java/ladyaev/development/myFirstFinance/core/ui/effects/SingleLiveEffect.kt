package ladyaev.development.myFirstFinance.core.ui.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Composable
fun <T : Any> SingleLiveEffect(transmission: LiveData<T>, callback: (effect: T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = Observer<T> { effect ->
            callback(effect)
        }
        transmission.observe(lifecycleOwner, observer)
        onDispose {
            transmission.removeObserver(observer)
        }
    }
}
