import React from 'react';
import $ from 'jquery'

import Test from '../Components/Test.jsx'


class TestsContainer extends React.Component{
    constructor(props){
        super(props);
        
    this.state = {
        Questions : [],
        TestId : 0,
       answers : { 'Answer-1' : '',
                    'Answer-2' : '',
                    'Answer-3' : ''}
    }
    
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);    
    
    }

    
     componentWillMount(){
        var _this = this
        $.ajax({
            url : 'http://localhost:8000/cgi-bin/gettest.py',
            data : {'SessionId' : '1',
                     'Type' : '1'},
            success : function(returnedData, status){
            _this.setState(returnedData);
                 console.log(_this.state)
                 
            }    
        })
        }
       
    
    handleInputChange(e){
        this.setState({
           answers : {[e.target.name] : e.target.value}
        });
           
    }
        
        
        
        
        
        
    
    
    handleSubmit(e){
        var _this = this;
        $.ajax({
            contentType :'application/x-www-form-urlencoded',
            type : 'POST',
            url :'http://localhost:8000/cgi-bin/gradetestresults.py',
            data : {
                'TestId' : _this.state.TestId,
                'SessionId' : '1',
                'Answer-1' : _this.state["Answer-1"],
                'Answer-2' : _this.state["Answer-2"]
            },
                    
            success : function(returnedData, status){
                _this.setState(returnedData);
            }
        });
    }
    
     
    render(){
    var inputs = [];
        for (var i = 0; i < this.state.Questions.length; i++) {            
    inputs.push(<input type='text' value={this.state.answer} key={i} name = {'Answers-'+i} onChange={this.handleInputChange}/>)
}
return (
    <div>
      <Test data={this.state.Questions} testId={this.state.TestId}/> 
       {inputs}
       
       <button onClick={this.handleSubmit}>Проверить</button>
       <div>{this.state.RightAnswers}</div>
       </div>
       )
       
       
       
       
         
    }
    
    
}

export default TestsContainer