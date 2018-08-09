package com.scorematics.android.api;


import android.content.Context;

import com.scorematics.android.utils.PreferenceHandler;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;


public class RetroUtils {




    private static boolean sslOverride;
    public static RestAdapter getHostAdapter(final Context context, String baseHost) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade arg0) {
                arg0.addHeader("content-type","application/json");
                arg0.addHeader("Authorization","bearer " + PreferenceHandler.readString(context,PreferenceHandler.TOKEN,""));



            }
        };
        if(sslOverride) {
            RestAdapter restAdapter = new RestAdapter.Builder().setClient(new OkClient(getUnsafeOkHttpClient())).setEndpoint(baseHost).setRequestInterceptor(requestInterceptor).build();
            return restAdapter;
        }
        else {
            RestAdapter restAdapter = new RestAdapter.Builder().setClient(new OkClient(new OkHttpClient())).setEndpoint(baseHost).setRequestInterceptor(requestInterceptor).setLogLevel(RestAdapter.LogLevel.FULL).build();
            return restAdapter;
        }

    }


    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
