package com.example.oinkvest_mobile.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.oinkvest_mobile.presentation.onboarding.OnboardingPageData

@Composable
fun OnboardingPage(
    modifier: Modifier = Modifier,
    pageData: OnboardingPageData,
) {
    Column(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 85.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if(pageData.imagesize == 1.2f) Arrangement.Top else Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = pageData.image),
            contentDescription = pageData.title,
            modifier = Modifier
                .fillMaxWidth(pageData.imagesize)
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = pageData.title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = pageData.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}