import React from 'react';
import {AppRegistry, StyleSheet, TouchableOpacity, Text, View} from 'react-native';
import { NativeModules } from 'react-native';
const viewEvents = NativeModules.ViewModule;

class WeatherInformation extends React.Component {
 constructor(){
    super();
    this.state ={
      status: false,
      error: false,
      weatherData: Object
    }
  }

   _renderWeatherInfo = () => {
               return (
                       this.state.status && (
                             <View style={styles.container}>
                                 <View>
                                   <Text style={styles.weatherInfo}>Weather Date: {this.state.weatherData.applicable_date}</Text>
                                   <Text style={styles.weatherInfo}>Weather State Name: {this.state.weatherData.weather_state_name}</Text>
                                   <Text style={styles.weatherInfo}>Min Temp: {this.state.weatherData.min_temp}</Text>
                                   <Text style={styles.weatherInfo}>Max Temp: {this.state.weatherData.max_temp}</Text>
                                   <Text style={styles.weatherInfo}>Wind Speed: {this.state.weatherData.wind_speed}</Text>
                                </View>
                            </View>
                       )
               )
  }

  _renderErrorView = () => {
               return (
                       this.state.error && (
                             <View style={styles.container}>
                                 <View>
                                   <Text style={{height:50, margin: 20, color: "red"}}>Error getting Weather Information. Please try again later</Text>
                                 </View>
                            </View>
                       )
               )
  }
  render() {
    return (
      <View style={styles.container}>
               {this._renderWeatherInfo()}
               {this._renderErrorView()}
               <TouchableOpacity
                        style={styles.weatherButton}
                         onPress={()=> viewEvents.onPressGetWeather((msg) =>
                                                    {msg.statusCode == 200 ?
                                                       this.setState({weatherData: msg, status: true, error: false})
                                                       : this.setState({error: true})
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
    height:40,
    textAlign: 'center',
    color: "#000000"
  },
  weatherButton : {
    alignItems: 'center',
    backgroundColor: '#176aef',
    height: 60,
    margin: 10,
    padding: 15
  },
  weatherTextButton : {
      fontSize: 20,
      textAlign: 'center',
      color: '#ffffff',
    }
});

AppRegistry.registerComponent('CartonCloudReactApp', () => WeatherInformation);