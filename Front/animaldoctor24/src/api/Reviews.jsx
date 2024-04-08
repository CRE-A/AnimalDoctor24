import axios from 'axios';

const requestUrl = process.env.REACT_APP_API_URL
const endpoint = 'http://localhost:8080/api/v1/review/list';

export async function getReviews(hn) {
    return axios.get(`${endpoint}/${hn}`);
}