package com.song.common.extentions.okhttp

import android.os.Build
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.Socket
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

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

fun ByteArray.toMultipart(key: String, fileName: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        key,
        fileName,
        toRequestBody(MultipartBody.FORM, 0, size)
    )
}

fun String.toJsonRequestBody(): RequestBody {
    return toRequestBody("application/json".toMediaTypeOrNull())
}

fun MediaType.Companion.formUrlEncoded(): MediaType? {
    return "application/x-www-form-urlencoded".toMediaTypeOrNull()
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




