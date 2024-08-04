package ladyaev.development.myFirstFinance.core.ui.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun FirstTimeSideEffect(callback: (firstTime: Boolean) -> Unit) {
    var firstTime by rememberSaveable { mutableStateOf(true) }
    SideEffect {
        callback(firstTime)
        firstTime = false
    }
}