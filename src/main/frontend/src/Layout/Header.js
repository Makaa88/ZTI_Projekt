import React, {Component} from 'react';
import { Link } from 'react-router-dom'

import LogoutComponent from '../Components/LogoutComponent';
import ExpensesComponent from '../Components/ExpensesComponent';


class Header extends Component
{

    render() {
        return(
            <nav>
                <ul>
                    <li> <LogoutComponent/> </li>
                    <li className="nav-item"> <Link to="/expenses" className="nav-link"> Wydatki </Link> </li>
                </ul>
            </nav>
        )
    }
}

export default Header;