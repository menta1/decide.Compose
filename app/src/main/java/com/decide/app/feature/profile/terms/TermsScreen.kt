package com.decide.app.feature.profile.terms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TermsScreen(
    onClickBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Text(
//            text = AnnotatedString.fromHtml(htmlString = terms) ,
//            style = DecideTheme.typography.bodyMedium,
//            color = DecideTheme.colors.inputBlack
//            )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTermsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TermsScreen {}
    }
}