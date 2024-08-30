package com.decide.app.feature.assay.assayProcess.ui.assayTimer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.ButtonVariant
import com.decide.uikit.ui.card.CardQuestion

@Composable
fun AssayWithTimer() {

    val progress by remember {
        mutableFloatStateOf(0.4f)
    }

    var asd: List<String> = listOf("Paypal", "Google Pay", "Apple Pay", "Apple Pay")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Назад",
                style = DecideTheme.typography.titleLarge,
                color = DecideTheme.colors.inputBlack,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LinearProgressIndicator(
                modifier = Modifier.clip(RoundedCornerShape(40.dp)),
                progress = { progress },
                color = DecideTheme.colors.accentYellow
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "03:23",
                style = DecideTheme.typography.titleLarge,
                color = DecideTheme.colors.inputBlack,
            )

            Spacer(modifier = Modifier.height(12.dp))

        }

        Box(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CardQuestion(text = "Nicholas Savage: You know what’s weird? That quote has been attributed to Bataille, to McLuhan, to Andy Warhol: it's one of those.")

                Spacer(modifier = Modifier.height(32.dp))

                Image(
                    modifier = Modifier
                        .heightIn(max = 256.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    painter = painterResource(id = R.drawable.category_capabilities),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column {
                    asd.forEach {
                        Spacer(modifier = Modifier.height(12.dp))
                        ButtonVariant(
                            text = it
                        ) {

                        }
                    }
                }
                Spacer(modifier = Modifier.height(84.dp))
            }
            ButtonMain(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                text = "Выбрать"
            ) {

            }
        }
    }
}