package com.example.oinkvest_mobile.main.wallet

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.oinkvest_mobile.R
import com.example.oinkvest_mobile.ui.theme.HeaderTextStyle

@Composable
fun WalletScreen() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var webView: WebView? = null
        var backButton by remember { mutableStateOf(false) }
        AndroidView(factory = { context ->
            WebView(context).apply {
                val url = context.getString(R.string.base_url) + "wallet.html"
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.setSupportZoom(true)
                webViewClient = object : WebViewClient(){
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        backButton = view?.canGoBack() == true
                    }
                }
                loadUrl(url)
            }
        }, update = {
            webView = it
        } )

        BackHandler (enabled = backButton) {
            webView?.goBack()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WalletScreenPreview() {
    WalletScreen()
}