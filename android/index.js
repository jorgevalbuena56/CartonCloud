import React from 'react';
import {AppRegistry, StyleSheet, TouchableOpacity, Text, View} from 'react-native';
import { NativeModules } from 'react-native';
const viewEvents = NativeModules.ViewModule;

$today = new Date();
$yesterday = new Date($today);
$yesterday.setDate($today.getDate() - 1);

class WeatherInformation extends React.Component {
 constructor(){
    super();
    this.state ={
      status: false,
      weatherData: Object
    }
  }

   _renderWeatherInfo = () => {
               return (
                       this.state.status && (
                             <View style={{
                               flex: 1,
                               flexDirection: 'column',
                               justifyContent: 'center',
                               alignItems: 'center',
                               minHeight: 300,
                               borderWidth: 1,
                               borderStyle: 'solid',
                               padding: 5,
                               margin: 5
                             }}>
                                 <View style={{flex:1}}>
                                   <Text style={styles.weatherInfo}>Date Result: {this.state.weatherData.applicable_date}</Text>
                                </View>
                                <View style={{flex:1}}>
                                   <Text style={styles.weatherInfo}>Max Temp: {this.state.weatherData.max_temp} degrees</Text>
                                </View>
                            </View>
                       )
               )
  }

  render() {
    return (
      <View style={styles.container}>
               {this._renderWeatherInfo()}
               <TouchableOpacity
                        style={styles.weatherButton}
                         onPress={()=> viewEvents.onPressGetWeather((msg) =>
                         {this.setState({
                                weatherData: msg,
                                status: true
                         })
                          console.log("From console: " + Object.getOwnPropertyNames(msg))
                         })}>
                        <Text style={styles.weatherTextButton}> Get Weather </Text>
               </TouchableOpacity>

      </View>
    );
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  weatherInfo: {
    fontSize: 20,
    textAlign: 'center',
    margin: 100,
    color: "#000000"
  },
  weatherButton : {
    alignItems: 'center',
    backgroundColor: '#176aef',
    margin: 200,
    padding: 15
  },
  weatherTextButton : {
      fontSize: 20,
      textAlign: 'center',
      color: '#ffffff',
    }
});

AppRegistry.registerComponent('CartonCloudReactApp', () => WeatherInformation);