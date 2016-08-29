import { PropTypes } from 'react';
import React, { Component } from 'react';
import { requireNativeComponent, View } from 'react-native';

const UIManager = require('UIManager');
class MakeMojiTextInput extends React.Component {
    constructor(props) {
        super(props);
    }
    onSendPress: Function = (e) => {
        if (this.props.onSendPress) {
            this.props.onSendPress(e);
        }
    };
    onCameraPress: Function = (e) => {
        if (this.props.onCameraPress) {
            this.props.onCameraPress(e);
        }
    };
    onHyperMojiPress: Function = (e) => {
        if (this.props.onHyperMojiPress) {
            this.props.onHyperMojiPress(e);
        }
    };


    render() {
        return <RCTMojiInputLayout {...this.props} onHyperMojiPress={this.onHyperMojiPress}
                                   onSendPress={this.onSendPress} onCameraPress={this.onCameraPress} />;
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

module.exports = requireNativeComponent(`RCTMojiInputLayout`, MakeMojiTextInput);