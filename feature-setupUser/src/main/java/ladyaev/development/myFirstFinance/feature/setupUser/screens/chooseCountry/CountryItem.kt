package ladyaev.development.myFirstFinance.feature.setupUser.screens.chooseCountry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ladyaev.development.myFirstFinance.core.ui.extensions.noRippleClickable
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun CountryItem(
    flagImageUrl: String,
    title: String,
    code: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .noRippleClickable(onClick)
        .background(if (isSelected) AppColors.lightGray else AppColors.white)
        .padding(vertical = 16.dp, horizontal = 24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(2.dp))
            ) {
                AsyncImage(
                    model = flagImageUrl,
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = code,
                style = MaterialTheme.typography.titleSmall
                    .copy(fontWeight = FontWeight.Black)
            )
        }
    }
}