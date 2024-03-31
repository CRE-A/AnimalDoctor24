import axios from 'axios';

const requestUrl = process.env.REACT_APP_API_URL
const endpoint = 'http://localhost:8080/home/authenticate';

const getUserById = ({email, password}) => {
    return axios.post(`${endpoint}`, {email, password});
};

export const userService = {
    getUserById,
};