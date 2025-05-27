package com.example.oinkvest_mobile.main.history

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.oinkvest_mobile.R

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HistoryScreen() {
    Column (
        modifier = Modifier.fillMaxSize().background(Color(0xFFF3F4F6)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var webView: WebView? = null
        var backButton by remember { mutableStateOf(false) }
        AndroidView(factory = { context ->
            WebView(context).apply {
                val url = context.getString(R.string.base_url) + "/history.html"

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
private fun HistoryScreenPreview() {
    HistoryScreen()
}