'use strict';
import React, {
  Alert,
  AppRegistry,
  Component,
  StyleSheet,
  View
} from 'react-native';

var CardIO = require('react-native-card-io');
var Button = require('@remobile/react-native-simple-button');


class CardIOReactSampleApp extends Component {

  constructor(props) {
    super(props);
    this.onBtnPress = this.onBtnPress.bind(this);
  }

  render() {
      return (
          <View style={styles.container}>
              <Button onPress={this.onBtnPress}>
                  showCamera
              </Button>
          </View>
      );
  }

  onBtnPress() {
    CardIO.scan({
      "requireExpiry": true,
      "scanInstructions": "Using Card.IO React Plugin",
    }).then(function(data) {
      var card = JSON.parse(data);
      console.log(card);
    }, function(err) {
      console.log(err);
    });
  }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'space-around',
        alignItems: 'center',
        backgroundColor: 'transparent',
        paddingVertical:150,
    }
});

AppRegistry.registerComponent('CardIOReactSampleApp', () => CardIOReactSampleApp);
