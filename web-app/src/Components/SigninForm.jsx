import React from 'react';
import $ from 'jquery';
import {browserHistory}   from 'react-router'




class SigninForm extends React.Component{
    constructor(props){
        super(props)
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleRememberMe = this.handleRememberMe.bind(this);
        this.state = {
            username : '',
            password : '',
            rememberMe : false
        }
    }
    
    handleInputChange(e){
        this.setState({
            [e.target.name] : e.target.value
        });
    }
    
    handleSubmit(e){
        var _this = this
        e.preventDefault();
        var data = 'Username='+_this.state.username+'&Password='+_this.state.password+'&RememberMe='+_this.state.rememberMe
        $.ajax({
            type : 'post',
            contentType :'application/x-www-form-urlencoded',
            url :'http://localhost:8000/cgi-bin/login.py',
            data : data,
            success : function(returnedData, status){
                console.log(returnedData);
                console.log(status)
               browserHistory.push('/profile');
                
            },
            crossDomain: true    
        });
    }
    
    handleRememberMe(e){
//        alert(this.state.rememberMe)
        this.setState({
             rememberMe : e.target.checked
            });
        }
        
    
    
    
    
    render(){
        return(
        <div className={'login-form-container form-container ' +  this.props.isVisible}>
            <form className='login-form form' onSubmit = {this.handleSubmit}>
               <h2>Вход</h2>
               
                <label>Имя пользователя<br></br>
                <input type="text" name="username" required value={this.state.username} onChange={this.handleInputChange}/>
                </label>
                
                <label>Пароль<br></br>
                <input type="password" name="password" required value={this.state.password} onChange={this.handleInputChange}/>
                </label>
                
                <label>Запомнить меня
                <input type='checkbox' name='rememberMe' defaultChecked={this.state.rememberMe} onChange={this.handleRememberMe}></input>
                </label>
                
                <input type="submit" value="Войти"/>
            </form>
        </div>
        
        ); 
        
    }
    
    
    
}

export default SigninForm;


