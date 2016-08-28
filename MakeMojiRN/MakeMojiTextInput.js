import { PropTypes } from 'react';
import React, { Component } from 'react';
import { requireNativeComponent, View } from 'react-native';

const UIManager = require('UIManager');
class MakeMojiTextInput extends React.Component {
    constructor(props) {
        super(props);
        this.attatchEditText = this.attatchEditText.bind(this);
        this.detatchEditText = this.detatchEditText.bind(this);
    }
    onSendPress: Function = (e) => {
        if (this.props.onSendPress) {
            this.props.onSendPress(e.nativeEvent);
        }
    };
    onCameraPress: Function = (e) => {
        if (this.props.onCameraPress) {
            this.props.onCameraPress(e.nativeEvent);
        }
    };
    onHyperMojiPress: Function = (e) => {
        if (this.props.onHyperMojiPress) {
            this.props.onHyperMojiPress(e.nativeEvent);
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
    cameraDrawable:React.PropTypes.string,
    backspaceDrawable:React.PropTypes.string,
    cameraVisible:React.PropTypes.bool,


    buttonContainerDrawable:React.PropTypes.string,
    topBarDrawable:React.PropTypes.string,
    bottomPageDrawable:React.PropTypes.string,
    phraseBgColor:React.PropTypes.string,
    headerTextColor:React.PropTypes.string,

    alwaysShowEmojiBar:React.PropTypes.bool,
    minSendLength:React.PropTypes.number,
    outsideEditText:React.PropTypes.string
};

module.exports = requireNativeComponent(`RCTMojiInputLayout`, MakeMojiTextInput);