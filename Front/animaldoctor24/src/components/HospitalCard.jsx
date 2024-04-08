import React, {useEffect, useState} from 'react';
import { FaHeart } from "react-icons/fa";
import { IoCall } from "react-icons/io5";
import {useNavigate} from "react-router-dom";
import {getReviews} from "../api/Reviews";


export default function HospitalCard({hospital ,hospital : {description, email, hn, hospitalName,location, role, tag, businessDay,businessHour,lunchHour,hospitalPhoneNumber, imagePath }}) {
    const [reviews, setReviews] = useState([]);
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        getReviews(hn)
            .then((res)=> {
                setReviews(res.data)
            })
            .catch((error)=> setErrorMsg(error.response.data));
    }, []);

    const navigate = useNavigate();
    return <>
            <li className="flex justify-normal w-[324px] mb-[5px]"
                onClick={()=>{ navigate(`/hospitals/${hn}`, { state: { hospital, reviews } } )}}>
                <div className="w-[100px] h-[100px] ">
                    <img src={imagePath} className="object-center object-cover w-full h-full"/>
                </div>

                <div className="flex flex-col flex-grow justify-between pl-[10px]">
                    <div>
                        <div className="flex justify-between items-center">
                            <h1 className="text-[16px] font-bold">{hospitalName}</h1>
                            <FaHeart/>
                        </div>
                        <h1 className="text-[13px]">서울특별시 성북구 2.22km</h1>
                    </div>
                    <div className="flex h-[30px] items-center">
                        <IoCall/>
                        <h1 className="ml-[5px]">02-2677-2720</h1>
                    </div>
                </div>
            </li>

    </>

}
