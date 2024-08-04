package ladyaev.development.myFirstFinance.core.ui.controls.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ladyaev.development.myFirstFinance.core.resources.R

@Composable
fun Toolbar(title: String, onNavigationButtonClick: () -> Unit) {
    @OptIn(ExperimentalMaterial3Api::class)
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.padding(8.dp)
        ) {
            Row {
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    letterSpacing = -(1.sp)
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
            IconButton(onClick = onNavigationButtonClick) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}