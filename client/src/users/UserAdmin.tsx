import React from 'react';

import './UserAdmin.css';
import {useDispatch, useSelector} from "react-redux";
import Checkbox from "./Checkbox";
import {Alert, Button, Table} from "reactstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {RootState} from "../rootReducer";

const UserAdmin = () => {
    const dispatch = useDispatch();
    const {users, error, loading} = useSelector((state: RootState) => {
        const {users, error, loading} = state.user;
        return {users, error, loading};
    });

    return (
        <div className="userAdmin">
            <h1>Users</h1>
            {error &&
            <Alert>{error}</Alert>
            }
            <Table color={loading ? 'dark' : 'light'}>
                <thead>
                <tr>
                    <th>Email</th>
                    <th>Admin?</th>
                    <th>Can Book?</th>
                    <th>Can See Money?</th>
                    <th>Clients</th>
                    <th className="text-right">
                        <Button><FontAwesomeIcon icon="plus"/></Button>
                    </th>
                </tr>
                </thead>
                <tbody>
                {users.map(user => (
                        <tr key={user.email}>
                            <td>{user.email}</td>
                            <td><Checkbox value={user.admin}/></td>
                            <td><Checkbox value={user.canBook}/></td>
                            <td><Checkbox value={user.canViewMoney}/></td>
                            <td>
                                {user.clients && (
                                    <ul>
                                        {user.clients.map(client => (
                                            <li key={client}>{client}</li>
                                        ))}
                                    </ul>
                                )
                                }
                            </td>
                            <td>
                                <div className="btn-group">
                                    <button>Update</button>
                                    <button>Delete</button>
                                </div>
                            </td>
                        </tr>
                    )
                )}
                </tbody>
            </Table>
        </div>
    );
};

export default UserAdmin;