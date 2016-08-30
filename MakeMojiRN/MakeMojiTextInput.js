import { PropTypes } from 'react';
import React, { Component } from 'react';
import { requireNativeComponent, View, BackAndroid } from 'react-native';
const ReactNative = require('react/lib/ReactNative');

const UIManager = require('UIManager');
var NativeComponent = requireNativeComponent('RCTMojiInputLayout', null);
export  default class MakeMojiTextInput extends React.Component {
    constructor(props) {
        super(props);
        this.state={canGoBack:false};
        this.canGoBack = this.canGoBack.bind(this);
        this.onCanGoBackChanged = this.onCanGoBackChanged.bind(this);
        this.onBackPressed = this.onBackPressed.bind(this);
        this.onSendPress = this.onSendPress.bind(this);
        this.onCameraPress = this.onCameraPress.bind(this);
        this.onHyperMojiPress = this.onHyperMojiPress.bind(this);


    }
    onSendPress(e) {
        if (this.props.onSendPress) {
            this.props.onSendPress(e.nativeEvent);
        }
    }
    onCameraPress(e){
        if (this.props.onCameraPress) {
            this.props.onCameraPress(e.nativeEvent);
        }
    };
    onHyperMojiPress(e) {
        if (this.props.onHyperMojiPress) {
            this.props.onHyperMojiPress(e.nativeEvent);
        }
    };

    onCanGoBackChanged(e){
        this.setState({canGoBack:e.nativeEvent.canGoBack});
    }
    canGoBack(){
        return this.state.canGoBack;
    }
    onBackPressed(){
        UIManager.dispatchViewManagerCommand(
            ReactNative.findNodeHandle(this),
            85,[]
        );
    }

    render() {
        return <NativeComponent {...this.props} onHyperMojiPress={this.onHyperMojiPress}
                                   onSendPress={this.onSendPress} onCameraPress={this.onCameraPress} onCanGoBackChanged={this.onCanGoBackChanged} />;
    }
}
MakeMojiTextInput.propTypes = {
    ...View.propTypes,
    onSendPress: React.PropTypes.func,
    onCameraPress: React.PropTypes.func,
    onHyperMojiPress: React.PropTypes.func,
    cameraDrawable:React.PropTypes.string,//name of asset in drawables folder
    backspaceDrawable:React.PropTypes.string,//name of asset in drawables folder
    cameraVisible:React.PropTypes.bool,//show the camera button


    buttonContainerDrawable:React.PropTypes.string,//the background drawable name behind the left buttons
    topBarDrawable:React.PropTypes.string,
    bottomPageDrawable:React.PropTypes.string,
    phraseBgColor:React.PropTypes.string,//color of phrase category backing
    headerTextColor:React.PropTypes.string,//color of page header text

    alwaysShowEmojiBar:React.PropTypes.bool,//always shows bar even when kb isn't up
    minSendLength:React.PropTypes.number,//number of characters needed to enable send button
    outsideEditText:React.PropTypes.string,//the tagFinder prop of a MakeMojiEditTextAndroid to use as input target, null if none
    channel:React.PropTypes.string//The Moji.setChannel to filter available categories
};

module.exports = MakeMojiTextInput