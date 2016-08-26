/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  ViewPagerAndroid,
    TouchableNativeFeedback,
ListView

} from 'react-native';
import MakeMojiTextInput from './MakeMojiRN/MakeMojiTextInput'
import MakeMojiText from './MakeMojiRN/MakeMojiText'
import TimerMixin from 'react-timer-mixin';

class MakeMojiReactNative extends Component {

    constructor(props){
        super(props);

        var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.state = {htmlMessages:[],
    dataSource:ds.cloneWithRows([])};

    }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} selectable={true}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Double tap R on your keyboard to reload,{'\n'}
          Shake or press menu button for dev menu
        </Text>
          <ListView style={{flex:1,alignSelf:'stretch'}}
                    dataSource={this.state.dataSource}
                    renderRow={(rowData) => <MakeMojiText textSize={14.0} onHyperMojiPress={this.log} style={styles.instructions} html={rowData}/>}
          />
        <MakeMojiTextInput style={styles.moji} headerTextColor={'red'} minSendLength={0} alwaysShowEmojiBar={true} onSendPress={this.sendPressed.bind(this)} onHyperMojiPress={this.log} onCameraPress={this.log}/>
          <Text style={styles.instructions}>
              below3
          </Text>
      </View>
    );
  }
  sendPressed(sendObject){
      console.log('send pressed', sendObject.nativeEvent);
      var htmlMessages = [...this.state.htmlMessages,sendObject.nativeEvent.html];
      var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
      this.setState({htmlMessages:htmlMessages,dataSource:ds.cloneWithRows(htmlMessages)});
  }
  log(s){
      var event = s.nativeEvent;
      console.log('',event);
  }
  componentDidMount(){
    //this.animationTimeout();
  }
  animationTimeout(){
    TimerMixin.setTimeout(
        () => {
         this.forceUpdate();
          this.animationTimeout();
           },
        50
    );

  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection:'column',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
      height:25,
    marginBottom: 5,
      fontSize:20
  },
  moji:{
    flex:1,
    height:100,
    alignSelf: 'stretch',
  }
});

AppRegistry.registerComponent('MakeMojiReactNative', () => MakeMojiReactNative);
