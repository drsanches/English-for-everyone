import React from 'react';
import $ from 'jquery';

class ProfileContainer extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            Success : '',
                
            
            Information : {
                Username : '',
                NativeLanguage : '',
                XP : 0,
                Languages : []
            }
        }
        
    }
    
    componentWillMount(){
        var _this = this
        $.ajax({
            url : 'http://localhost:8000/cgi-bin/getinfo.py',
            data : {'SessionId' : '1'},
            success : function(returnedData, status){
            _this.setState(returnedData);
//                console.log(_this.state)
            
        }
        })
        
    }
    
    
    render(){
        return(
        <div>
        <h1>Профиль</h1>
      <span>Имя пользователя :  {this.state.Information.Username}</span>
      <span>XP: {this.state.Information.XP}</span>
          <span>Родной язык: {this.state.Information.NativeLanguage}</span>
           
        <button>Выйти</button>   
           
            </div>    
        )
        
    }
    
    
}

export default ProfileContainer