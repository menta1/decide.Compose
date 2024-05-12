package com.mental.uikit.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.decide.uikit.R
import com.mental.uikit.theme.DecideTheme

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box() {
            Icon(
                painter = painterResource(id = R.drawable.ic_bottom_bar_assay),
                contentDescription = "List assay"
            )
            Text(text = stringResource(id = R.string.bottom_bar_nav_assays))
        }
        Box() {
            Icon(
                painter = painterResource(id = R.drawable.ic_bottom_bar_category),
                contentDescription = "List assay"
            )
            Text(text = stringResource(id = R.string.bottom_bar_nav_categories))
        }
        Box() {
            Icon(
                painter = painterResource(id = R.drawable.ic_bottom_bar_profile),
                contentDescription = "List assay"
            )
            Text(text = stringResource(id = R.string.bottom_bar_nav_profile))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBottomNavBar() {
    DecideTheme {
        Text(text = "adasdadsads")
    }
//    DecideTheme {
//        BottomNavBar()
//    }
}