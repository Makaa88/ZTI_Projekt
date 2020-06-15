import React, {Component} from 'react';
import axios from "axios";
import { Link, Redirect } from 'react-router-dom';

class Expense extends Component
{
    state =
        {
            deleteRender: false,
        };

    deleteExpense(id)
    {
        console.log(id);
        axios.delete(
            "/expenses/delete/" +id,
        ).then(response => {
            if(response.data.successResponse)
            {
                console.log("deleted");
                this.setState({deleteRender: true});
            }
            else console.log("Delete errror");
        }).catch(error => {
            console.log("Delete error v2", error);
        });

    }

    render() {

        if(!sessionStorage.getItem("session"))
        {
            console.log("inValid session");
            return <Redirect to='/'/>;
        }

        if(this.state.deleteRender)
        {
            return <Redirect to='/expenses'/>;
        }


        return(
            <div className="bg-light px-4 py-3 d-flex">
                <p className="m-0">{this.props.expense.goal}</p>
                <p className="m-0">{this.props.expense.date}</p>
                <p className="m-0">{Number(this.props.expense.amount).toFixed(2)}</p>
                <button type='button' className="btn btn-sm btn-danger ml-2" onClick={this.deleteExpense.bind(this, this.props.expense.id)}> Usuń </button>
            </div>
        );
    }
}


export default Expense;