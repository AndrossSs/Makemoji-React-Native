import { PropTypes } from 'react';
import React, { Component } from 'react';
import { requireNativeComponent, View } from 'react-native';

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
    cameraDrawable:React.PropTypes.string,
    backspaceDrawable:React.PropTypes.string,
    cameraVisible:React.PropTypes.bool,


    buttonContainerDrawable:React.PropTypes.string,
    topBarDrawable:React.PropTypes.string,
    bottomPageDrawable:React.PropTypes.string,
    phraseBgColor:React.PropTypes.string,
    headerTextColor:React.PropTypes.string,

    alwaysShowEmojiBar:React.PropTypes.bool,
    minSendLength:React.PropTypes.number
};

module.exports = requireNativeComponent(`RCTMojiInputLayout`, MakeMojiTextInput);