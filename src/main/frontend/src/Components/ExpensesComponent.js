import React, {Component} from 'react';
import { Link, Redirect } from 'react-router-dom';

import Expense from './Expense';
import AddExpense from './AddExpense';

class ExpensesComponent extends Component
{
    state =
        {
            data: []
        };

    componentDidMount() {
        fetch("/expenses/getExpenses")
            .then(response => response.json())
            .then(data => {
                this.setState({data});
            });
    }


    render() {
        if(!sessionStorage.getItem("session"))
        {
            console.log("inValid session");
            return <Redirect to='/'/>;
        }

        return(
            <div>
                <div>
                    <AddExpense/>
                </div>

                <div>
                    {this.state.data.map(element => <Expense expense={element}/> )}
                </div>
            </div>
        )
    }
}

export default ExpensesComponent;