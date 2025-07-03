package com.example.oinkvest_mobile.util

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Um WebViewClient customizado que monitora a navegação.
 * @param onSessionExpired Uma função a ser chamada quando um redirect para /login é detectado.
 * @param onPageLoadingStateChanged Uma função para notificar a UI quando uma página começa ou termina de carregar.
 */
class AppWebViewClient(
    private val onSessionExpired: () -> Unit,
    private val onPageLoadingStateChanged: (isLoading: Boolean) -> Unit
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        Log.d("AppWebViewClient", "Interceptando navegação para: $url")

        if (url != null && url.endsWith("/login")) {
            Log.w("AppWebViewClient", "Sessão expirada ou inválida! Redirecionando para o login nativo.")
            // Chama a função de callback para que a UI possa fazer o logout.
            onSessionExpired()
            // Retornar 'true' impede a WebView de carregar a página de login. É essencial!
            return true
        }

        // Para qualquer outra URL, deixa a WebView continuar normalmente.
        return false
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        onPageLoadingStateChanged(true) // Avisa a UI que o carregamento começou
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onPageLoadingStateChanged(false) // Avisa a UI que o carregamento terminou
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        onPageLoadingStateChanged(false) // Para o loading em caso de erro
        Log.e("AppWebViewClient", "Erro na WebView: ${error?.description} na URL: ${request?.url}")
    }
}