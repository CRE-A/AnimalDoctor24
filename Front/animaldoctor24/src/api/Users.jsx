import axios from 'axios';

const requestUrl = process.env.REACT_APP_API_URL
const endpoint = 'http://localhost:8080/api/v1/auth/authenticate';
export async function getUserById ({email, password}){
    return axios.post(`${endpoint}`, {email, password});
}
