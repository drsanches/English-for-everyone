import React from 'react';
import $ from 'jquery';
import {browserHistory}   from 'react-router'



class SignupForm extends React.Component{
    constructor(props){
        super(props)
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            username : '',
            password : '',
            email : ''
        }
    }
    
    handleInputChange(e){
        this.setState({
            [e.target.name] : e.target.value
        });
    }
    
    handleSubmit(e){
//        e.preventDefault();
        var _this = this;
                $.ajax({
                    type : "POST",
            contentType :'application/x-www-form-urlencoded',
            url :'http://localhost:8000/cgi-bin/reg.py',
            data : _this.state,     
            success : function(returnedData, status){
                console.log(returnedData)
                console.log(status)
               browserHistory.push('/profile');
            }
            
        });
    }
    
        

    
    render(){
        return(
          <div className={'reg-form-container form-container ' +  this.props.isVisible}>
       <form className='reg-form form' action="#">
               <h2>Регистрация</h2>
               
                <label>Имя пользователя<br></br>
                <input type="text" name="username" required value={this.state.username} onChange={this.handleInputChange}/>
                </label>
                
                <label>Пароль<br></br>
                <input type="password" name="password" required value={this.state.password} onChange={this.handleInputChange}/>
                </label>
                
                <label>Email <br></br>
                <input type='email' name='email' required value={this.state.email} onChange={this.handleInputChange}></input>
                </label>
                
                <input type="button" value="Регистрация" onClick={this.handleSubmit}/>
            </form>
        </div>
        
        ); 
        
    }
    
    
    
}

export default SignupForm;
