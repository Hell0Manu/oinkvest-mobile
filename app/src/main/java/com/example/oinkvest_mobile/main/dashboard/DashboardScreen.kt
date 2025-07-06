package com.example.oinkvest_mobile.main.dashboard

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

import com.example.oinkvest_mobile.R
import com.example.oinkvest_mobile.data.local.LoginDataHolder


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DashboardScreen() {

    var isLoading by remember { mutableStateOf(true) }

    val webViewAlpha by animateFloatAsState(targetValue = if (isLoading) 0f else 1f, label = "webview alpha")

    var webView: WebView? = null
    var backButton by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize().alpha(webViewAlpha),
            factory = { context ->
            WebView(context).apply {
                val url = context.getString(R.string.base_url) + "/dashboard"

                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.javaScriptEnabled = true
                settings.builtInZoomControls = true
                settings.displayZoomControls = false

                settings.loadWithOverviewMode = true

                webViewClient = object : WebViewClient(){
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        backButton = view?.canGoBack() == true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        Log.d("WebViewAuth", "Página finalizada: $url")

                        if (url?.endsWith("/login") == true) {
                            Log.d("WebViewAuth", "Página de login detectada. Injetando script...")

                            val jsScript = """
                                    (function() {
                                        var emailInput = document.querySelector('input[name="username"]');
                                        var passwordInput = document.querySelector('input[name="password"]');
                                        var form = document.querySelector('form[action="/login"]');
                                        
                                        if (emailInput && passwordInput && form) {
                                            emailInput.value = '${LoginDataHolder.email ?: ""}';
                                            passwordInput.value = '${LoginDataHolder.password ?: ""}';
                                            form.submit();
                                        } else {
                                            console.error('Elementos do formulário de login não encontrados!');
                                        }
                                    })();
                                """.trimIndent()

                            view?.evaluateJavascript(jsScript, null)
                            LoginDataHolder.clear()
                        }else{
                            isLoading = false
                        }
                    }
                }
                loadUrl(url)
            }
        }, update = {
            webView = it
        } )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        BackHandler (enabled = backButton) {
            webView?.goBack()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen()
}