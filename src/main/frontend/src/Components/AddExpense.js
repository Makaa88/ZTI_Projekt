import React, {Component} from 'react';
import axios from "axios";
import { Link, Redirect } from 'react-router-dom';

class AddExpense extends Component
{
    constructor(props)
    {
        super(props);

        this.state=
            {
                redirect: false,
                date: "",
                ammount: 0.0,
                goal: "",
            };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event)
    {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleSubmit(event)
    {
        const {date, ammount, goal} = this.state;
        console.log("GHandle submit");
        let id = parseInt(sessionStorage.getItem("session"));

        axios.post(
            "/expenses/addExpense",
            {
                date: date, amount: ammount, goal: goal, personId: id
            }
        ).then(response => {
            if(response.data.successResponse)
            {
                console.log("Added new expense");
                this.setState({redirect: true});
                this.props.add = false;
            }
            else console.log("Can add expense");
        }).catch(error => {
            console.log("Can add expense");
        });

    }

    render() {
        const {redirect} = this.state;
        if(redirect)
        {
            return  <Redirect to='/expenses'/>;
        }

        return (
            <div className="container login-container center">
                <div className="login-form-1">
                    <form onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            Data:
                            <input className="form-control center-block" type="date" name="date" onChange={this.handleChange}
                                   value={this.state.date} required/>
                        </div>
                        <div className="form-group">
                            Kwota:
                            <input className="form-control center-block" type="number" name="ammount" onChange={this.handleChange}
                                   value={this.state.ammount} required/>
                        </div>
                        <div className="form-group">
                            Na co wydatek:
                            <input className="form-control center-block" type="text" name="goal" onChange={this.handleChange}
                                   value={this.state.goal} required/>
                        </div>
                        <div className="form-group row justify-content-center align-items-center">
                            <button className="btn btn-primary btn-lg center-block" type="submit">Dodaj</button>
                        </div>
                    </form>

                    <p>{this.state.loginErrors}</p>
                </div>
            </div>
        );
    }
}

export default AddExpense;