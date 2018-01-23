import React from 'react';
import SigninForm from './Components/SigninForm.jsx';
import SignupForm from './Components/SignupForm.jsx';
import './style/home.css';
import {Link} from 'react-router'

class Home extends React.Component {
    constructor(props){
        super(props)
        
        this.state = {
            
        }
    }
    


    
    
  render() {
    return (
        <div>
        <header className = 'home-header'>
        <span className='logo'>SimpleLearn</span>
        <nav className = 'home-navbar'> 
        <Link to = '/lessons'>Уроки</Link> 
        <Link to = '/tests'>Тесты</Link>
        <Link to = '/profile'>Профиль</Link>
        
        </nav>
        
        </header>
        
        <main>
            {this.props.children}
            
        </main>
        
        </div>
        
    )
  }
}

export default Home;
