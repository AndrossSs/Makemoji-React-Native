import { PropTypes } from 'react';
import React, { Component } from 'react';
import { requireNativeComponent, View } from 'react-native';

class MakeMojiTextInput extends React.Component {
    constructor(props) {
        super(props);
        this.onCameraPressed = this._onCameraPressed.bind(this);
    }
   /* onSendPressed(event: Event) {
        if (!this.props.onSendPressed) {
            return;
        }
        console.log("send pressed recieved");
        this.props.onSendPressed(event.nativeEvent.message);
    }*/
    onSendPressed: Function = (e) => {
        console.log("send pressed recieved");
        if (this.props.onSendPressed) {
            this.props.onSendPressed(e);
        }
    };
    _onCameraPressed(event: Event) {
    if (!this.props.onCameraPressed) {
        return;
    }
    this.props.onCameraPressed(event.nativeEvent.message);
}
    render() {
        return <RCTMojiInputLayout {...this.props} onSendPressed={this.onSendPressed} onCameraPressed={this._onCameraPressed} />;
    }
    shouldComponentUpdate(nextProps,nextState){
        return true;
    }
}
MakeMojiTextInput.propTypes = {
    ...View.propTypes,
    onSendPressed: React.PropTypes.func,
    onCameraPressed: React.PropTypes.func
};

module.exports = requireNativeComponent(`RCTMojiInputLayout`, MakeMojiTextInput);