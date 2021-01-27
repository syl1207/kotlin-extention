package com.song.common.extentions.okhttp

import android.os.Build
import okhttp3.OkHttpClient
import java.net.Socket
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*
import kotlin.jvm.Throws

fun OkHttpClient.Builder.supportHttps(): OkHttpClient.Builder {
    try {
        val trustManager =
            OkHttpHelper.getFitTrustManager()
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(trustManager), SecureRandom())
        sslSocketFactory(sslContext.socketFactory, trustManager)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    hostnameVerifier(HostnameVerifier { _, _ -> true })
    return this
}



object OkHttpHelper {
    @JvmStatic
    fun getFitTrustManager(): X509TrustManager {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            object : X509ExtendedTrustManager() {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?,
                    socket: Socket?
                ) {

                }

                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?,
                    engine: SSLEngine?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?,
                    socket: Socket?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?,
                    engine: SSLEngine?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOf()
                }
            }
        } else {
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            }
        }
    }

    @JvmStatic
    fun supportHttps(client: OkHttpClient.Builder) {
        client.supportHttps()
    }
}




