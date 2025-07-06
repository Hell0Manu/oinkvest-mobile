package com.example.oinkvest_mobile.presentation.components

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.oinkvest_mobile.data.local.LoginDataHolder

/**
 * Um WebViewClient reutilizável que lida com a automação de login e monitoramento de sessão.
 *
 * @param onSessionExpired Ação a ser chamada quando a sessão expira (redirect para /login).
 * @param onPageLoadingStateChanged Ação para notificar a UI sobre o estado de carregamento (mostra/esconde o loading).
 * @param onPageFinishedHook Um 'gancho' para executar lógica customizada quando a página termina de carregar (ex: atualizar o botão de voltar).
 */
class AppWebViewClient(
    private val onSessionExpired: () -> Unit,
    private val onPageLoadingStateChanged: (isLoading: Boolean) -> Unit,
    private val onPageFinishedHook: (url: String?) -> Unit // <-- O PARÂMETRO QUE FALTAVA
) : WebViewClient() {

    private var loginAutomated = false

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        if (url != null && (url.endsWith("/login") || url.contains("/login?error"))) {
            // A automação de login já foi tentada. Se caímos no /login de novo, a sessão expirou.
            if (loginAutomated) {
                Log.w("AppWebViewClient", "Sessão expirada ou falha no login! Chamando onSessionExpired.")
                onSessionExpired()
                return true // Impede a WebView de carregar a página de login.
            }
        }
        // Deixa a WebView carregar qualquer outra URL.
        return false
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        onPageLoadingStateChanged(true)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        if (url?.endsWith("/login") == true && !loginAutomated) {
            val email = LoginDataHolder.email
            val password = LoginDataHolder.password

            if (!email.isNullOrBlank() && !password.isNullOrBlank()) {
                Log.d("AppWebViewClient", "Página de login detectada. Injetando script.")

                val jsScript = """
                    (function() {
                        document.querySelector('input[name="username"]').value = '$email';
                        document.querySelector('input[name="password"]').value = '$password';
                        document.querySelector('form[action="/login"]').submit();
                    })();
                """.trimIndent()

                view?.evaluateJavascript(jsScript, null)
                loginAutomated = true
                LoginDataHolder.clear()
            }
        }

        // Chama o hook para que a UI possa fazer outras coisas
        onPageFinishedHook(url)
        onPageLoadingStateChanged(false) // Avisa que o carregamento terminou (esconde o loading)
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        onPageLoadingStateChanged(false)
        Log.e("AppWebViewClient", "Erro na WebView: ${error?.description} na URL: ${request?.url}")
    }
}