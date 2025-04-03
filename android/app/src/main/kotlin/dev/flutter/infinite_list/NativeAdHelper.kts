package com.example.myapp

import android.content.Context
import android.view.ViewGroup
import com.adster.orchestrationsdk.AdSter
import com.adster.orchestrationsdk.ads.MediationBannerAd
import com.adster.orchestrationsdk.ads.MediationAdListener
import com.adster.orchestrationsdk.ads.AdRequestConfiguration
import com.adster.orchestrationsdk.ads.AdsListener
import com.adster.orchestrationsdk.ads.AdError
import com.adster.orchestrationsdk.ads.AdEventsListener

class NativeAdHelper(private val context: Context) {

    fun initializeSdk() {
        AdSter.initializeSdk(context.applicationContext, object : AdSter.InitializationListener {
            override fun onInitializationComplete(adapterStatus: List<AdSter.AdapterStatus>) {
                // Initialization complete callback
                // You can handle your logic here after SDK is initialized
            }
        })
    }

    fun showBannerAd(adUnitId: String, container: ViewGroup) {
        val configuration = AdRequestConfiguration.builder(context, adUnitId)

        AdSterAdLoader.builder()
            .withAdsListener(object : MediationAdListener() {
                override fun onBannerAdLoaded(ad: MediationBannerAd) {
                    super.onBannerAdLoaded(ad)
                    // Show the banner ad when it is successfully loaded
                    container.removeAllViews()
                    container.addView(ad.getView())
                }

                override fun onFailure(adError: AdError) {
                    // Handle failure case
                }
            })
            .withAdsEventsListener(object : AdEventsListener() {
                override fun onAdClicked() {
                    // Handle ad click event here
                }

                override fun onAdImpression() {
                    // Handle ad impression event here
                }
            })
            .build()
            .loadAd(configuration.build())
    }
}
