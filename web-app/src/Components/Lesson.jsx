import React from 'react';
 import '../style/lessons.css';

class Lesson extends React.Component{
    render(){
        return(
        <div className='lessons'>
             {this.props.data.map(function(item,index){
                    return (
    <div key={index} className = 'lesson'>
     <p className="native-word">Слово : {item.NativeWord}</p>
      <p className="foreign-word">Перевод : {item.ForeignWord}</p>
      
    </div>
  )
                })}
        </div>
        
        )
    }
    
    
}

export default Lesson