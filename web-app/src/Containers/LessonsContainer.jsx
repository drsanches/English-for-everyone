import React from 'react';
import $ from 'jquery';

import Lesson from '../Components/Lesson.jsx'

class LessonsContainer extends React.Component{
    constructor(props){
        super(props)
        
        this.state = {
            Lesson : []
        }
        
    }
    
    
    
    
    
    componentWillMount(){
        var _this = this
        $.ajax({
            url : 'http://localhost:8000/cgi-bin/getlesson.py',
            data : {'SessionId' : '1',
                     'Type' : 'qq',
                     'TestId': 12543},
            success : function(returnedData, status){
            _this.setState(returnedData);
                console.log(_this.state)
            
        }
        })
       
     }
    
    
    
    render(){
        return(
        <div>
            <Lesson data={this.state.Lesson}/>
        </div>
        )
        
    }
    
    
}

export default LessonsContainer