package com.makemojireactnative;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.View;

import com.facebook.csslayout.CSSNode;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.text.ReactTextView;
import com.facebook.react.views.text.ReactTextViewManager;
import com.facebook.react.views.text.TextInlineImageSpan;
import com.makemoji.mojilib.HyperMojiListener;
import com.makemoji.mojilib.Moji;
import com.makemoji.mojilib.MojiInputLayout;
import com.makemoji.mojilib.MyMojiInputLayout;

import java.lang.reflect.Method;
import java.util.Map;

import csslayout.MyShadowNode;

/**
 * Created by s_baa on 8/6/2016.
 */
public class ReactMojiTextView extends ReactTextViewManager {
    MyShadowNode node;
    EventDispatcher eventDispatcher;
    @Override
    public String getName() {
        return "ReactMojiText";
    }

    @Override
    public ReactTextView createViewInstance(final ThemedReactContext reactContext) {
      ReactTextView rtv =super.createViewInstance(reactContext);
        return rtv;
    }
    @ReactProp(name = "html")
    public void setHtml(ReactTextView view, @Nullable String html) {
        Log.d(getName(),"setting html "+html);
        if (html!=null)
            Moji.setText(html,view,true);
    }
    @Override
    public void updateExtraData(ReactTextView view, Object extraData) {

    }

    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                HyperMojiEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onHyperMojiPress")
                );
    }

    public static class HyperMojiEvent extends Event<ReactMojiInputLayout.HyperMojiEvent>{
        String url;
        final static String EVENT_NAME = "onHyperMojiPress";
        public HyperMojiEvent(int viewTag,String url){
            super(viewTag, SystemClock.uptimeMillis());
            this.url = url;
        }
        @Override
        public String getEventName() {
            return EVENT_NAME;
        }

        @Override
        public void dispatch(RCTEventEmitter rctEventEmitter) {
            WritableMap eventData = Arguments.createMap();
            eventData.putString("url", url);
            rctEventEmitter.receiveEvent(getViewTag(),getEventName(),eventData);
        }
    }
}
