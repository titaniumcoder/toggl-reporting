import axios, {AxiosResponse} from 'axios';

export interface IReportingApi {
    auth(idToken: string): Promise<AxiosResponse<ICurrentUser>>;

    logout(): void;

    /*
    fetchClients():    Promise < AxiosResponse < IClient[] >>;

    fetchCash(): Promise<AxiosResponse<IHeaderInfo>>;

    fetchClient(id: number, from: Moment, to: Moment): Promise<AxiosResponse<ITogglClientResponse>>;

    fetchExcel(id: number, from: Moment, to: Moment): Promise<AxiosResponse<any>>;

    tagEntry(id: number): Promise<AxiosResponse<void>>;

    untagEntry(id: number): Promise<AxiosResponse<void>>;

    tagClient(id: number, from: Moment, to: Moment): Promise<AxiosResponse<void>>;

    untagClient(id: number, from: Moment, to: Moment): Promise<AxiosResponse<void>>;
*/
}

export interface ICurrentUser {
    admin: boolean;
    canBook: boolean;
    canViewMoney: boolean;
}

export class ReportingApi implements IReportingApi {
    constructor() {
        axios.defaults.baseURL = '/api';
        delete axios.defaults.headers['Authorization'];
    }

    async auth(idToken: string) {
        axios.defaults.headers['Authorization'] = `Bearer ${idToken}`;

        return  await axios.get<ICurrentUser>('current-user');
    }

    logout(): void {
        delete axios.defaults.headers['Authorization'];
    }
}

export default new ReportingApi();

/*
    async fetchExcel(id: number, fromM: Moment, toM: Moment) {
        const from = fromM.format('YYYY-MM-DD');
        const to = toM.format('YYYY-MM-DD');
        return axios.get<any>(`timesheet/${id}`, {
            params: {from, to},
            maxContentLength: 10000000,
            responseType: 'blob',
        });
    };
 */

/*
import axios, { AxiosResponse } from 'axios';
import { ICashout, IClient, IProject, ITimeEntry } from './model';
import { Moment } from 'moment';

export interface ITogglClientResponse {
    projects: IProject[];
    timeEntries: ITimeEntry[][];
}

export interface ITogglReportingApi {
    fetchClients(): Promise<AxiosResponse<IClient[]>>;

    fetchCash(): Promise<AxiosResponse<ICashout[]>>;

    fetchClient(id: number, from: Moment, to: Moment): Promise<AxiosResponse<ITogglClientResponse>>;

    fetchExcel(id: number, from: Moment, to: Moment): Promise<AxiosResponse<any>>;

    tagEntry(id: number): Promise<AxiosResponse<void>>;

    untagEntry(id: number): Promise<AxiosResponse<void>>;

    tagClient(id: number, from: Moment, to: Moment): Promise<AxiosResponse<void>>;

    untagClient(id: number, from: Moment, to: Moment): Promise<AxiosResponse<void>>;
}

export class TogglReportingApi implements ITogglReportingApi {
    constructor() {
        axios.defaults.baseURL = '/api';
    }

    async login(username: string, password: string) {
        delete axios.defaults.headers['Authorization'];

        /*
        access_token: "eyJhbGciOiJub25lIn0.eyJzdWIiOiJ0ZXN0IiwibmJmIjoxNTczNTUxNzA0LCJyb2xlcyI6W10sImlzcyI6InRvZ2dsLXJlcG9ydGluZyIsImV4cCI6MTU3MzU1NTMwNCwiaWF0IjoxNTczNTUxNzA0fQ."
        expires_in: 3600
        refresh_token: "eyJhbGciOiJub25lIn0.eyJzdWIiOiJ0ZXN0IiwibmJmIjoxNTczNTUxNzA0LCJyb2xlcyI6W10sImlzcyI6InRvZ2dsLXJlcG9ydGluZyIsImlhdCI6MTU3MzU1MTcwNH0."
        token_type: "Bearer"
        username: "test"
        * /
const login = await axios.post('login', { username: username, password: password });
if (login.status === 401) {
    // do nothing
    return false;
} else {
    axios.defaults.headers['Authorization'] = 'Bearer ' + login.data.access_token;
    // TODO store refresh token + expires_in for better auth handling
    return true;
}
}

logout() {
    delete axios.defaults.headers['Authorization'];
}

async fetchClients() {
    return await axios.get<IClient[]>('clients');
}

async fetchCash() {
    return await axios.get<ICashout[]>('cash');
}

async tagEntry(id: number) {
    return await axios.put<void>(`tag/${id}`);
};

async untagEntry(id: number) {
    return await axios.delete<void>(`tag/${id}`);
};

async untagClient(id: number, fromM: Moment, toM: Moment) {
    const from = fromM.format('YYYY-MM-DD');
    const to = toM.format('YYYY-MM-DD');
    return await axios.delete<void>(`client/${id}/billed`, {
        params: {
            from, to
        }
    });
};

async tagClient(id: number, fromM: Moment, toM: Moment) {
    const from = fromM.format('YYYY-MM-DD');
    const to = toM.format('YYYY-MM-DD');
    return await axios.put<void>(`client/${id}/billed`, {}, {
        params: {
            from, to
        }
    });
};

async fetchClient(id: number, fromM: Moment, toM: Moment) {
    const from = fromM.format('YYYY-MM-DD');
    const to = toM.format('YYYY-MM-DD');
    return axios.get<ITogglClientResponse>(`client/${id}`, { params: { from, to } });
};

async fetchExcel(id: number, fromM: Moment, toM: Moment) {
    const from = fromM.format('YYYY-MM-DD');
    const to = toM.format('YYYY-MM-DD');
    return axios.get<any>(`timesheet/${id}`, {
        params: { from, to },
        maxContentLength: 10000000,
        responseType: 'blob',
    });
};
}
 */