import axios from 'axios';

const requestUrl = process.env.REACT_APP_API_URL
const endpoint = 'http://localhost:8080/api/v1/hospital/list';

export async function getHospitals() {
    return axios.get(`${endpoint}`);
}