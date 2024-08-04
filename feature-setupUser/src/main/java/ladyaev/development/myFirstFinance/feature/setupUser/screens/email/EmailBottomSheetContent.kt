package ladyaev.development.myFirstFinance.feature.setupUser.screens.email

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.controls.button.ActionButton
import ladyaev.development.myFirstFinance.core.ui.theme.AppTheme

@Composable
fun EmailBottomSheetContent(
    email: String,
    nextButtonClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.email_bs_title),
            style = MaterialTheme.typography.titleMedium,
            letterSpacing = -(1.sp)
        )
        Image(
            painter = painterResource(id = R.drawable.img_envelope),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.email_bs_subtitle, email),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        ActionButton(
            onclick = {
                nextButtonClick()
            },
            text = stringResource(id = R.string.next),
            buttonColors = AppTheme.buttonTheme.primary
        )
    }
}
