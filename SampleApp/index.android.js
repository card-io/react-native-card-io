'use strict';
import React, {
  Alert,
  AppRegistry,
  Component,
  StyleSheet,
  View
} from 'react-native';

var CardIO = require('react-native-card-io');
var Button = require('react-native-button');


class CardIOReactSampleApp extends Component {

  constructor(props) {
    super(props);
    this.onBtnPress = this.onBtnPress.bind(this);
  }

  render() {
      return (
          <View style={styles.container}>
          <Button containerStyle={styles.button}
               style={{fontSize: 20, color: 'white'}}
               onPress={this.onBtnPress} >
                    Scan Credit Card
              </Button>
          </View>
      );
  }

  onBtnPress() {
    CardIO.canScan().then(function(data) {
      CardIO.scan({
        "requireExpiry": true,
        "scanInstructions": "Using Card.IO React Plugin",
      }).then(function(data) {
        var card = JSON.parse(data);
        console.log(card);
      }, function(err) {
        console.log(err);
      });
    }, function(err) {
      console.log(err);
    }
    )
  }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'space-around',
        alignItems: 'center',
        backgroundColor: 'transparent',
        paddingVertical:150,
    },
    button: {
        padding:10,
        height:45,
        overflow:'hidden',
        borderRadius:4,
        backgroundColor: 'blue',
    }
});

AppRegistry.registerComponent('CardIOReactSampleApp', () => CardIOReactSampleApp);
