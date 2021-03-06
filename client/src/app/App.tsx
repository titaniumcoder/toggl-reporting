import React, {useEffect} from 'react';
import {Redirect, Route, Switch, useHistory} from 'react-router-dom'
import './App.css';
import Navigation from "../Navigation";
import {Container} from "reactstrap";
import Clients from "../clients/Clients";
import ClientInfo from "../clients/ClientInfo";
import Login from "../auth/Login";
import {useDispatch, useSelector} from "react-redux";
import UserAdmin from "../users/UserAdmin";
import {RootState} from "../rootReducer";
import ClientAdmin from "../clients/ClientAdmin";
import ProjectAdmin from "../projects/ProjectAdmin";
import CurrentTimeEntry from "../timeentry/CurrentTimeEntry";
import TimeEntries from "../timeentry/TimeEntries";
import ClientSelector from "../clients/ClientSelector";
import {logout} from '../auth/authSlice';

const App = () => {
    const {loggedIn, admin, canBook, authExpiration} = useSelector((state: RootState) => state.auth);

    const dispatch = useDispatch();
    const history = useHistory();

    useEffect(() => {
        const timeout = setTimeout(() => {
            dispatch(logout());
        }, authExpiration - Date.now());

        return () => {
            if (timeout) {
                clearTimeout(timeout);
            }
        }
    });

    // when loggedin go back to root on the rouet
    useEffect(() => {
        if (!loggedIn) {
            // TODO redirect
            history.push("/");
        }
    }, [loggedIn, history]);

    if (!loggedIn) {
        return <Login/>
    } else {
        return (
            <div>
                <Container fluid={true}>
                    <Navigation/>
                    <Switch>
                        {admin &&
                        <Route path="/admin">
                            {
                                admin ? (
                                    <div>
                                        <UserAdmin/>
                                        <ClientAdmin/>
                                        <ProjectAdmin/>
                                    </div>) : <Redirect to="/"/>
                            }
                        </Route>
                        }
                        <Route path="/">
                            {canBook &&
                            <CurrentTimeEntry/>
                            }

                            <Clients/>
                            <ClientSelector/>
                            <div className="mt-3">
                                <ClientInfo/>
                            </div>
                            <TimeEntries/>
                        </Route>
                    </Switch>
                </Container>
            </div>
        );
    }
};

export default App;
