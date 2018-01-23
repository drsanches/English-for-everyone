import React from 'react';
//import Answer from './Answer.jsx'


class Test extends React.Component{
    render(){
        return(
        <div className ='tests'>
           <p>ID теста: {this.props.testId}</p>
           
           
            {this.props.data.map(function(item,index){
                    return (
    <div key={index}>
      <p className="question">{item.Question}</p>
    </div>
  )
                })}
        </div>
        
        
        )
        
    }
    
    
    
}

export default Test