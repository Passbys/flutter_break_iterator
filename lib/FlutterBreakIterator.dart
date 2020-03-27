import 'dart:async';

import 'package:flutter/services.dart';

class FlutterBreakIterator {
  static const MethodChannel _channel =
      const MethodChannel('flutter_break_iterator');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  ///句子分词
  static Future<List> getWorkIterator(sentence) async {
    final List<String> locales = List<String>.from(await _channel
        .invokeMethod('getBreakIterator', {'sentence': sentence}));
    return locales;
  }
}
