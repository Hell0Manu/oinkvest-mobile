package com.example.oinkvest_mobile.presentation.onboarding

import androidx.annotation.DrawableRes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oinkvest_mobile.presentation.onboarding.components.OnboardingPage
import kotlinx.coroutines.launch
import com.example.oinkvest_mobile.R
import com.example.oinkvest_mobile.data.local.AppPreferences
import com.example.oinkvest_mobile.presentation.components.AnimatedBlurredBackground


data class OnboardingPageData(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
    val imagesize: Float
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {

    val context = LocalContext.current

    val pages = listOf(
        OnboardingPageData(
            image = R.drawable.pig1,
            title = "Bem-vindo ao OinkInvest!",
            description = "Uma plataforma feita para facilitar suas operações de compra, venda e monitoramento de criptomoedas de um jeito simples e fácil.",
            imagesize = 0.7f
        ),
        OnboardingPageData(
            image = R.drawable.pig2,
            title = "Personalize suas estratégias",
            description = "Adapte operações de compra e venda conforme suas metas e perfil de investimento e obtenha insights detalhados para tomadas de decisão por meio de dashboards.",
            imagesize = 1.2f
        ),
        OnboardingPageData(
            image = R.drawable.pig3,
            title = "Acesse seu histórico e carteira",
            description = "Acesse seu histórico de trades, revise e analise todas as suas transações passadas e companhe a evolução dos seus investimentos",
            imagesize = 0.7f
        ),
        OnboardingPageData(
            image = R.drawable.pig4,
            title = "Notificações em tempo real",
            description = "Fique informado sobre ações, compras e vendas relevantes. ",
            imagesize = 1.2f
        )
    )
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    AnimatedBlurredBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                OnboardingPage(
                    modifier = Modifier.fillMaxSize(),
                    pageData = pages[pageIndex]
                )
            }

            TopSection(
                pagerState = pagerState,
                onSkip = {
                    scope.launch {
                        AppPreferences.setOnboardingCompleted(context)

                        pagerState.animateScrollToPage(pages.size - 1)
                    }
                }
            )

            BottomSection(
                pagerState = pagerState,
                onAnterior = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                onProximo = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                onLogin = {
                    AppPreferences.setOnboardingCompleted(context)

                    navController.navigate("login") {
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                },
                onCriarConta = {
                    AppPreferences.setOnboardingCompleted(context)

                    navController.navigate("register") {
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onSkip: () -> Unit
) {
    if (pagerState.currentPage > 0 && pagerState.currentPage < pagerState.pageCount - 1) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextButton(onClick = onSkip, modifier = Modifier.align(Alignment.CenterEnd)) {
                Text(text = "PULAR", color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.BottomSection(
    pagerState: PagerState,
    onAnterior: () -> Unit,
    onProximo: () -> Unit,
    onLogin: () -> Unit,
    onCriarConta: () -> Unit
) {
    Column(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 40.dp, start = 24.dp, end = 24.dp)
    ) {

        when (pagerState.currentPage) {
            0 -> {
                Button(
                    onClick = onProximo, modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "PRÓXIMO",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            1, 2 -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onAnterior) {
                        Text(text = "ANTERIOR", color = MaterialTheme.colorScheme.onBackground)
                    }

                    PagerIndicator(
                        pageCount = 3,
                        selectedIndex = pagerState.currentPage - 1
                    )

                    TextButton(onClick = onProximo) {
                        Text(text = "PRÓXIMO", color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }

            3 -> {
                Column {
                    Button(
                        onClick = onLogin, modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "ENTRAR",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onCriarConta,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                    ) {
                        Text(
                            text = "CRIAR CONTA",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onAnterior) {
                            Text(text = "ANTERIOR", color = MaterialTheme.colorScheme.onBackground)
                        }

                        PagerIndicator(
                            pageCount = 3,
                            selectedIndex = pagerState.currentPage - 1
                        )

                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                                .width(88.dp)
                        )

                    }
                }
            }
        }
    }
}


@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    selectedIndex: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { i ->
            val color =
                if (i == selectedIndex) MaterialTheme.colorScheme.primary else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(12.dp)
            )
        }
    }
}
