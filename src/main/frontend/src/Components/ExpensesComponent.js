import React, {Component} from 'react';
import axios from "axios";
import { Link, Redirect } from 'react-router-dom';

import Expense from './Expense';
import AddExpense from './AddExpense';
import EditExpense from "./EditExpense";

class ExpensesComponent extends Component
{
    constructor(props)
    {
        super(props);
        this.state =
            {
                data: [],
                displayData: [],
                add: false,
                month: "2020-06",
                sorted: true,
                amountSpent: 0,
            };

        this.sortByDate = this.sortByDate.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }


    componentDidMount() {
        axios.get("/expenses/getExpenses")
            .then(response => {
                this.setState({data: response.data});
                const sum = this.state.data.reduce((prev, curr) => prev + curr.amount,0);
                console.log(sum);
                this.setState({amountSpent: sum});
            });


        console.log("comonent did mount");
    }

    makeAddPossible()
    {
        this.setState({add: !this.state.add})
    }

    handleChange(event)
    {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    sortByDate()
    {
        let m = new Date();
        let current = new Date(this.state.month);
        m.setDate(1);
        m.setMonth(current.getMonth()+1);
        m.setFullYear(current.getFullYear());


        this.setState({sorted: true});
        axios.post(
            "/expenses/getSorted",
            {
                date: m, amount: 0, goal: ""
            }
        ).then(response => {
            this.setState({data: response.data});
            const sum = this.state.data.reduce((prev, curr) => prev + curr.amount,0);
            console.log(sum);
            this.setState({amountSpent: sum});

        }).catch(error => {
            console.log("Can add expense");
        });

    }


    render() {
        if(!sessionStorage.getItem("session"))
        {
            console.log("inValid session");
            return <Redirect to='/'/>;
        }


        console.log(this.state.month);
        console.log(this.state.sorted);

        return(
            <div>
                <div>
                    {
                        this.state.add && <AddExpense add={this.state.add}/>
                    }
                    <button type="button" onClick={this.makeAddPossible.bind(this)}> Dodaj </button>
                </div>

                <div>
                    <form>
                        <div className="form-group">
                            Miesiąc:
                            <input className="form-control center-block" type="month" name="month" onChange={this.handleChange}
                                   value={this.state.month}/>
                        </div>
                        <div className="form-group row justify-content-center align-items-center">
                            <button className="btn btn-primary btn-lg center-block" type="button" onClick={this.sortByDate}>Sortuj</button>
                        </div>
                    </form>
                </div>

                <div>
                    <p>W tym okresie wydałes {this.state.amountSpent}</p>
                </div>


                <div>
                    {this.state.data.map(element => <Expense expense={element}/> )}

                </div>
            </div>
        )
    }
}

export default ExpensesComponent;