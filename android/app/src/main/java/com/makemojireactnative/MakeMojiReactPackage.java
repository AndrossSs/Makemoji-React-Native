package com.makemojireactnative;

import android.app.Application;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.makemoji.mojilib.Moji;
import com.makemoji.mojilib.ReactMakeMojiTextInputManager;
import com.makemoji.mojilib.ReactMojiInputLayout;
import com.makemoji.mojilib.ReactMojiTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by s_baa on 8/6/2016.
 */
class MakeMojiReactPackage implements ReactPackage {

    MakeMojiReactPackage(Application application){
        super();
        Moji.initialize(application,"bfd3eea60abad87d378f87939ef3a116e8b23a35");
    }
    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new ReactMojiInputLayout(), new ReactMojiTextView(), new ReactMakeMojiTextInputManager()
        );
    }

    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();


        return modules;
    }
}