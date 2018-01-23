import React from 'react';
import SigninForm from './Components/SigninForm.jsx';
import SignupForm from './Components/SignupForm.jsx';
import './style/index.css';
import {Link} from 'react-router';


class App extends React.Component {
    constructor(props){
        super(props)
        
        this.state = {
          logVisibility : '',
          regVisibility : ''
        }
    this.handleLogOpened  = this.handleLogOpened.bind(this);    
    this.handleRegOpened  = this.handleRegOpened.bind(this);    
    }
    
    
    handleLogOpened(){
        this.setState({
            logVisibility :   (this.state.logVisibility === '') ? 'visible' : ''
        });
        
    }
    
     handleRegOpened(){
        this.setState({
            regVisibility :   (this.state.regVisibility === '') ? 'visible' : ''
        });
        
    }
    
    
    
    
  render() {
    return (
        <div>
        <div className = {'fade ' + this.state.logVisibility} onClick = {this.handleLogOpened}></div>
        <div className = {'fade ' + this.state.regVisibility} onClick = {this.handleRegOpened}></div>
        
    <header className='header'>
            <span className="logo">SimpleLearn</span>
            <nav className='startpage-navbar navbar'>
                <ul>
                    <li><button className='login-btn' onClick={this.handleLogOpened}>Вход</button></li>
                    
                    <li><button className='reg-btn' onClick={this.handleRegOpened}>Регистрация</button></li>
                </ul>
            </nav>
        </header>

       
        
        <SigninForm isVisible = {this.state.logVisibility}/>
        <SignupForm isVisible = {this.state.regVisibility}/>

    

        </div>
    )
  }
}

export default App;
