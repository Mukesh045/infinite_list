import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static const platform = MethodChannel('com.example.infinite_list/ads');

  // Create a GlobalKey to reference the ad container widget
  final GlobalKey _adContainerKey = GlobalKey();

  Future<void> _initializeSdk() async {
    try {
      final result = await platform.invokeMethod('initializeSdk');
      print(result); // Should print "SDK Initialized"
    } on PlatformException catch (e) {
      Fluttertoast.showToast(
        msg: "Failed to show banner ad: '${e.message}'.",
        toastLength: Toast.LENGTH_SHORT,
        gravity: ToastGravity.BOTTOM,
        backgroundColor: Colors.black,
        textColor: Colors.white,
        fontSize: 16.0,
      );
    }
  }

  Future<void> _showBannerAd() async {
    try {
      // Pass the container's key (or a unique ID) to the native code
      await platform.invokeMethod('showBannerAd', {
        'adUnitId': 'adster_banner_320x50 ',  // Replace with your ad unit ID
        'adContainerId': _adContainerKey.hashCode,  // Use hashCode as a unique ID
      });
    } on PlatformException catch (e) {
        Fluttertoast.showToast(
          msg: "Failed to show banner ad: '${e.message}'.",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.BOTTOM,
          backgroundColor: Colors.black,
          textColor: Colors.white,
          fontSize: 16.0,
        );
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text('AdSter Integration')),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              ElevatedButton(
                onPressed: _initializeSdk,
                child: Text('Initialize SDK'),
              ),
              ElevatedButton(
                onPressed: _showBannerAd,
                child: Text('Show Banner Ad'),
              ),
              // The container where the banner ad will be placed
              Container(
                key: _adContainerKey,
                width: double.infinity,
                height: 50,  // Adjust height to fit the banner ad
                color: Colors.grey[200],
                child: Center(child: Text('Banner Ad Area')),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
