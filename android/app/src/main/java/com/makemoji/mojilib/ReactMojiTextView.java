package com.makemoji.mojilib;

import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.text.ReactTextShadowNode;
import com.facebook.react.views.text.ReactTextView;
import com.facebook.react.views.text.ReactTextViewManager;

import java.util.Map;

import csslayout.MyReactTextShadowNode;
import csslayout.MyShadowNode;

/**
 * Created by s_baa on 8/6/2016.
 */
public class ReactMojiTextView extends ReactTextViewManager {
    MyReactTextShadowNode node;
    EventDispatcher eventDispatcher;
    @Override
    public String getName() {
        return "ReactMojiText";
    }
    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, ReactTextView view) {
        eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
    }

    @Override
    public ReactTextView createViewInstance(final ThemedReactContext reactContext) {
      final ReactTextView rtv =super.createViewInstance(reactContext);
        rtv.setTag(com.makemojireactnative.R.id._makemoji_hypermoji_listener_tag_id, new HyperMojiListener() {
            @Override
            public void onClick(String url) {
                eventDispatcher.dispatchEvent(new HyperMojiEvent (rtv.getId(),url));
            }
        });
        return rtv;
    }
    @ReactProp(name = "html")
    public void setHtml(ReactTextView view, @Nullable String html) {
        if (html!=null) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX,node.getFontSize());
            Moji.setText(html, view, true);
        }
    }
    @ReactProp(name = "plaintext")
    public void setPlainText(ReactTextView view, @Nullable String plaintext) {
        if (plaintext!=null)
            Moji.setText(Moji.plainTextToSpanned(plaintext),view);
    }
    @ReactProp(name = "textSize", defaultFloat=0f)
    public void setTextSize(ReactTextView view, float textSize) {
        Log.d(getName(),"text size " + textSize);
        if (textSize!=0f)
            view.setTextSize(textSize);
    }

    @Override
    public ReactTextShadowNode createShadowNodeInstance() {
        node = new MyReactTextShadowNode(false);
        return node;

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
