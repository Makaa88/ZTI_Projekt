import React, {Component} from 'react';
import { Link } from 'react-router-dom'

import LogoutComponent from '../Components/LogoutComponent';


class Header extends Component
{

    render() {
        return(
            <nav>
                <ul>
                    <li> <LogoutComponent/> </li>
                </ul>
            </nav>
        )
    }
}

export default Header;