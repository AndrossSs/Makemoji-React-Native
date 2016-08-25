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
import com.makemoji.mojilib.Moji;
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

        mojiInputLayout.setSendLayoutClickListener(new MojiInputLayout.SendClickListener() {
            @Override
            public boolean onClick(final String html, Spanned spanned) {
                eventDispatcher.dispatchEvent(new SendEvent(mojiInputLayout.getId(),html));
                return true;
            }
        });
        mojiInputLayout.setHyperMojiClickListener(new HyperMojiListener() {
            @Override
            public void onClick(String url) {
                eventDispatcher.dispatchEvent(new HyperMojiEvent (mojiInputLayout.getId(),url));
            }
            });
        mojiInputLayout.setCameraButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDispatcher.dispatchEvent(new CameraEvent(mojiInputLayout.getId()));
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
                SendEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onSendPress"),
                HyperMojiEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onHyperMojiPress"),
                CameraEvent.EVENT_NAME, (Object) MapBuilder.of("registrationName", "onCameraPress")

                );
    }
    public static class SendEvent extends Event<SendEvent>{
        String html;
        final static String EVENT_NAME = "onSendPress";
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
            eventData.putString("plainText", Moji.htmlToPlainText(html));
            rctEventEmitter.receiveEvent(getViewTag(),getEventName(),eventData);
        }
    }
    public static class HyperMojiEvent extends Event<HyperMojiEvent>{
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
    public static class CameraEvent extends Event<CameraEvent>{
        final static String EVENT_NAME = "onCameraPress";
        public CameraEvent(int viewTag){
            super(viewTag, SystemClock.uptimeMillis());
        }
        @Override
        public String getEventName() {
            return EVENT_NAME;
        }

        @Override
        public void dispatch(RCTEventEmitter rctEventEmitter) {
            WritableMap eventData = Arguments.createMap();
            rctEventEmitter.receiveEvent(getViewTag(),getEventName(),eventData);
        }
    }
}
