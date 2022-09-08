import 'plugin_codelab_platform_interface.dart';

//ini yang dipanggil ke dart
class PluginCodelab {
  static Future<String?> getPlatformVersion() {
    // return apasih;
    return PluginCodelabPlatform.instance.getPlatformVersion();
  }

  static Future<int?> onKeyDown(int key) {
    return PluginCodelabPlatform.instance.onKeyDown(key);
  }

  static Future<int?> onKeyUp(int key) {
    return PluginCodelabPlatform.instance.onKeyUp(key);
  }
}
