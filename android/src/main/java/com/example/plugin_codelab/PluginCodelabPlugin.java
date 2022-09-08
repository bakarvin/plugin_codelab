package com.example.plugin_codelab;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


import java.util.ArrayList;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.BinaryMessenger;

/** PluginCodelabPlugin */
public class PluginCodelabPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private AES256 aesEncrypt;
  private Synth Synth;
  private static final String channelName = "plugin_codelab";

  private static void setup(PluginCodelabPlugin plugin, BinaryMessenger binaryMessenger){
    plugin.channel = new MethodChannel(binaryMessenger, channelName);
    plugin.channel.setMethodCallHandler(plugin);
    plugin.Synth = new Synth();
    plugin.aesEncrypt = new AES256();
    plugin.Synth.start();
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    // channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "plugin_codelab");
    // channel.setMethodCallHandler(this);
    setup(this, flutterPluginBinding.getBinaryMessenger());
  }

  //handles message from dart method channel
  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("onKeyDown")) {
      try {
        ArrayList args = (ArrayList) call.arguments;
        int numKeysDown = Synth.keyDown((Integer) args.get(0));
        result.success(numKeysDown);
      } catch (Exception ex){
        result.error("1", ex.getMessage(), ex.getStackTrace());
      }
    } else if (call.method.equals("onKeyUp")) {
        try {
        ArrayList args = (ArrayList) call.arguments;
        int numKeysDown = Synth.keyUp((Integer) args.get(0));
        result.success(numKeysDown);
      } catch (Exception ex){
        result.error("1", ex.getMessage(), ex.getStackTrace());
      }
    } else if (call.method.equals("encrypt")) {
      try {
        String args = (String) call.arguments;
        String encrypt = aesEncrypt.encrypt((String) args);
        result.success(encrypt);
      } catch (Exception ex) {
        result.error("1", ex.getMessage(), ex.getStackTrace());
      }
    } else if (call.method.equals("decrypt")) {
      try {
        String args = (String) call.arguments;
        String decrypt = aesEncrypt.decrypt((String) args);
        result.success(decrypt);
      } catch (Exception ex){
        result.error("1", ex.getMessage(), ex.getStackTrace());
      }
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
