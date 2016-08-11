#import <MapKit/MapKit.h>

#import "RCTViewManager.h"
#import <UIKit/UIKit.h>
#import "METextInputView.h"


@interface RCTMojiInputLayout : RCTViewManager
@end

@implementation RCTMojiInputLayout

RCT_EXPORT_MODULE()

- (UIView *)view
{
  return [[METextInputView alloc] init];
}

@end