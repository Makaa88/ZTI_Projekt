import React, {Component} from 'react';
import { Link, Redirect } from 'react-router-dom';
import axios from "axios";

class LogoutComponent extends Component
{
    constructor(props)
    {
        super(props);

        this.state =
            {
                redirect: false
            };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event)
    {

        axios.post(
            "/auth/logout",
            {
            }
        ).then(response => {
            console.log("logut response");
            if(response.data.loginStatus) {
                console.log("logut response accpeted");
                this.setState({redirect: true});
                sessionStorage.clear();
            }
        }).catch(error => {
            console.log("logout eroor", error);
        });

    }

    render() {
        const {redirect} = this.state;
        if(redirect)
        {
            console.log("redirect");
            return  <Redirect to=''/>;
        }

        return(
            <div>
                <form onSubmit={this.handleSubmit}>
                    <button type="submit">Wyloguj</button>
                </form>
            </div>
        );
    }
}

export default LogoutComponent;