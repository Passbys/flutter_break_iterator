#import "FlutterbreakiteratorPlugin.h"
#if __has_include(<flutterbreakiterator/flutterbreakiterator-Swift.h>)
#import <flutterbreakiterator/flutterbreakiterator-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutterbreakiterator-Swift.h"
#endif

@implementation FlutterbreakiteratorPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterbreakiteratorPlugin registerWithRegistrar:registrar];
}
@end
