import React from 'react';
import ReactDOM from 'react-dom';
import './style/index.css';
import App from './App.jsx';
import Home from './Home.jsx';
import LessonsContainer from './Containers/LessonsContainer.jsx';
import TestsContainer from './Containers/TestsContainer.jsx';
import ProfileContainer from './Containers/ProfileContainer.jsx';

import registerServiceWorker from './registerServiceWorker';
import { Router, Route, IndexRoute, browserHistory } from 'react-router'


ReactDOM.render((
    <Router history = {browserHistory}>
    <Route path = '/' component = {App}/>
    <Route  component = {Home}>
        <Route path = '/lessons' component = {LessonsContainer}/>
        <Route path = '/tests' component = {TestsContainer}/>
        <Route path = '/profile' component = {ProfileContainer}/>
    </Route>
    </Router>

), document.getElementById('root'));
registerServiceWorker();
