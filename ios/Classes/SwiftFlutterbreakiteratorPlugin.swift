import Flutter
import UIKit

public class SwiftFlutterbreakiteratorPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutterbreakiterator", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterbreakiteratorPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
     if call.method == "getBreakIterator" {
        var tokensArray: [AnyHashable] = []
        let text = call.arguments as! String
        var range = CFRangeMake(CFIndex(0), CFIndex((text.count)))

        let tokenizer = CFStringTokenizerCreate(kCFAllocatorDefault, text as CFString?, range, kCFStringTokenizerUnitWord, nil)

        var tokenType = CFStringTokenizerAdvanceToNextToken(tokenizer)

        while tokenType != [] {
            range = CFStringTokenizerGetCurrentTokenRange(tokenizer)
            let token = (text as NSString?)?.substring(with: NSRange(location: range.location, length: range.length))
            tokensArray.append(token ?? "")
            tokenType = CFStringTokenizerAdvanceToNextToken(tokenizer)
        }
        result(tokensArray)
      } else {
        result(nil)
      }
  }
}
