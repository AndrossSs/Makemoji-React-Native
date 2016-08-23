package com.makemojireactnative;

import android.os.SystemClock;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.csslayout.CSSNode;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.makemoji.mojilib.HyperMojiListener;
import com.makemoji.mojilib.MojiInputLayout;
import com.makemoji.mojilib.MyMojiInputLayout;

import java.lang.reflect.Method;
import java.util.Map;

import csslayout.MyShadowNode;

/**
 * Created by s_baa on 8/6/2016.
 */
public class ReactMojiInputLayout extends ViewGroupManager<MyMojiInputLayout> {
    MyShadowNode node;
    EventDispatcher eventDispatcher;
    @Override
    public boolean needsCustomLayoutForChildren() {
        return true;
    }
    @Override
    public String getName() {
        return "RCTMojiInputLayout";
    }

    @Override
    protected MyMojiInputLayout createViewInstance(final ThemedReactContext reactContext) {
        final MyMojiInputLayout mojiInputLayout = new MyMojiInputLayout(reactContext);
        Log.d(getName(),"createviewinstance");
        mojiInputLayout.setSendLayoutClickListener(new MojiInputLayout.SendClickListener() {
            @Override
            public boolean onClick(final String html, Spanned spanned) {
                WritableMap event = Arguments.createMap();
                event.putString("html", html);
                eventDispatcher.dispatchEvent(new SendEvent(mojiInputLayout.getId(),html));
                return true;
            }
        });
        mojiInputLayout.setHyperMojiClickListener(new HyperMojiListener() {
            @Override
            public void onClick(String url) {
                WritableMap event = Arguments.createMap();
                event.putString("url", url);
                Log.d(getName(),"hypermoji click");
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                        mojiInputLayout.getId(),
                        "onHyperMojiClick",
                        event);
            }
            });
        mojiInputLayout.setCameraButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WritableMap event = Arguments.createMap();
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                        mojiInputLayout.getId(),
                        "onCameraPressed",
                        event);
                mojiInputLayout.invalidate();
                mojiInputLayout.requestLayout();
                mojiInputLayout.layout(mojiInputLayout.getLeft(),mojiInputLayout.getTop(),mojiInputLayout.getRight(),mojiInputLayout.getBottom());
            }
            });

        mojiInputLayout.setRnUpdateListener(new MojiInputLayout.RNUpdateListener() {

            Method m;
            @Override
            public void needsUpdate() {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {

                        if (m == null) {
                            try {
                                m = CSSNode.class.getDeclaredMethod("markHasNewLayout");
                                m.setAccessible(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                            if (node != null) {
                                if (node.hasNewLayout()) node.markLayoutSeen();
                                ReactShadowNode parent = node.getParent();
                                while (parent != null) {
                                    if (parent.hasNewLayout()) {
                                        try {
                                            m.invoke(parent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        parent.markLayoutSeen();
                                    }
                                    parent = parent.getParent();
                                }
                                node.markUpdated();
                            }
                            Log.d(getName(), "markUpdated");
                    }

                };
                reactContext.runOnNativeModulesQueueThread(r);
            }
        });

        return mojiInputLayout;
    }
    public LayoutShadowNode createShadowNodeInstance() {
        node = new MyShadowNode();
        return node;
    }
    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, MyMojiInputLayout view) {
        eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
    }

    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                SendEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onSendPressed")
        );
    }
    public static class SendEvent extends Event<SendEvent>{
        String html;
        final static String EVENT_NAME = "onSendPressed";
        public SendEvent(int viewTag,String html){
            super(viewTag, SystemClock.uptimeMillis());
            this.html = html;
        }
        @Override
        public String getEventName() {
            return EVENT_NAME;
        }

        @Override
        public void dispatch(RCTEventEmitter rctEventEmitter) {
            WritableMap eventData = Arguments.createMap();
            eventData.putString("html", html);
            rctEventEmitter.receiveEvent(getViewTag(),getEventName(),eventData);
        }
    }
}
