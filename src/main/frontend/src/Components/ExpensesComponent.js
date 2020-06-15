import React, {Component} from 'react';
import { Link, Redirect } from 'react-router-dom';
import axios from 'axios'

class ExpensesComponent extends Component
{
    render() {
        if(!sessionStorage.getItem("session"))
        {
            console.log("inValid session");
            return <Redirect to='/'/>;
        }
        return(
            <div>Hello from expenses</div>
        )
    }
}

export default ExpensesComponent;