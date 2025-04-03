package com.example.myapp

import android.os.Bundle
import android.view.ViewGroup
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class MainActivity : FlutterActivity() {
    private val CHANNEL = "com.example.infinite_list/ds"
    private lateinit var nativeAdHelper: NativeAdHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the native ad helper
        nativeAdHelper = NativeAdHelper(this)

        MethodChannel(flutterEngine?.dartExecutor, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "initializeSdk" -> {
                    nativeAdHelper.initializeSdk()
                    result.success("SDK Initialized")
                }
                "showBannerAd" -> {
                    val adUnitId = call.argument<String>("adUnitId")
                    val adContainerId = call.argument<Int>("adContainerId")
                    val adContainer = findViewById<ViewGroup>(adContainerId ?: 0)
                    if (adUnitId != null) {
                        nativeAdHelper.showBannerAd(adUnitId, adContainer)
                        result.success("Banner ad displayed")
                    } else {
                        result.error("ERROR", "Invalid adUnitId", null)
                    }
                }
                else -> result.notImplemented()
            }
        }
    }
}
