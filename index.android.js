import React,{Component} from 'react';
import {
  AppRegistry,View,Text,
} from 'react-native';


class ExampleActivity extends Component{
    componentDidMount(){
        console.log('ExampleActivity componentDidMount')
    }

    render(){
        return(
            <View>
                <Text>this is React Native Activity Page</Text>
            </View>
        );
    }
}

class ExampleFragment extends Component{
    componentDidMount(){
        console.log('ExampleFragment componentDidMount')
    }
    render(){
        return(
            <View>
                <Text>this is React Native Fragment Page</Text>
            </View>
        );
    }
}
AppRegistry.registerComponent('PreLoadRNActivity', () => ExampleActivity);
AppRegistry.registerComponent('PreLoadRNFragment', () => ExampleFragment);
