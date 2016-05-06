'use strict';

var React = require('react-native');
var {
    NativeModules
} = React;

var CardIO = {};

var ReactCardIOModule = NativeModules.ReactCardIOModule;

CardIO.scan = function (options) {
  return ReactCardIOModule.scan(options);
};

CardIO.canScan = function () {
  return ReactCardIOModule.canScan();
}

module.exports = CardIO;
