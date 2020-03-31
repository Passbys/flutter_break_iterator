package com.purchase.flutterbreakiterator

import android.graphics.Point
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.text.BreakIterator

/** FlutterbreakiteratorPlugin */
public class FlutterbreakiteratorPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutterbreakiterator")
    channel.setMethodCallHandler(this);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "flutterbreakiterator")
      channel.setMethodCallHandler(FlutterbreakiteratorPlugin())
    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "getBreakIterator") {
      var list = ArrayList<String>()
      val boundary = BreakIterator.getWordInstance()
      var sentence = call.arguments.toString()
      boundary.setText(sentence)
      var start = boundary.first()
      var end = boundary.next()
      while (end != BreakIterator.DONE) {
        val res: String = sentence!!.substring(start, end)
//        if (!(res.matches(Regex("\\[[a-z0-9]*\\]")) && isEmoji(res)) && !isPunctuation(res)) {
//          list.add(res)
//        }
        list.add(res)
        start = end
        end = boundary.next()
      }
      result.success(list)
    } else {
      result.notImplemented()
    }
  }

  /**
   * 是否emoji16，待实现
   *
   * @param text
   * @return
   */
  fun isEmoji(text: String): Boolean {
    return checkEmoji(text)
  }

  /**
   * 检查是否emoji表情
   *
   * @param source
   * @return
   */
  fun checkEmoji(source: String): Boolean {
    val len = source.length
    val isEmoji = false
    for (i in 0 until len) {
      val hs = source[i]
      if (hs.toInt() in 0xd800..0xdbff) {
        if (source.length > 1 && i + 1 < source.length) {
          val ls = source[i + 1]
          val uc = (hs.toInt() - 0xd800) * 0x400 + (ls.toInt() - 0xdc00) + 0x10000
          if (uc in 0x1d000..0x1f77f) {
            return true
          }
        }
      } else { // non surrogate
        if (hs.toInt() in 0x2100..0x27ff && hs.toInt() != 0x263b) {
          return true
        } else if (hs.toInt() in 0x2B05..0x2b07) {
          return true
        } else if (hs.toInt() in 0x2934..0x2935) {
          return true
        } else if (hs.toInt() in 0x3297..0x3299) {
          return true
        } else if (hs.toInt() == 0xa9 || hs.toInt() == 0xae || hs.toInt() == 0x303d || hs.toInt() == 0x3030 || hs.toInt() == 0x2b55 || hs.toInt() == 0x2b1c || hs.toInt() == 0x2b1b || hs.toInt() == 0x2b50 || hs.toInt() == 0x231a) {
          return true
        }
        if (!isEmoji && source.length > 1 && i < source.length - 1) {
          val ls = source[i + 1]
          if (ls.toInt() == 0x20e3) {
            return true
          }
        }
      }
    }
    return isEmoji
  }

  fun isPunctuation(txt: String): Boolean {
    //val regex = "[\\u3000-\\u301e\\ufe10-\\ufe19\\ufe30-\\ufe44\\ufe50-\\ufe6b\\uff01-\\uffee]|[\\s]|[,\\.;\\:\"'!\\&\\$\\#\\@\\*\\(\\)\\%]"
    val regex = "[\\u3000-\\u301e\\ufe10-\\ufe19\\ufe30-\\ufe44\\ufe50-\\ufe6b\\uff01-\\uffee]|[\\s]"
    return txt.matches(Regex(regex))
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
