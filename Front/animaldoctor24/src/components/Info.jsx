import React from 'react';
import { IoCall } from "react-icons/io5";
import {useNavigate} from "react-router-dom";


export default function Info({hospital : {description, email, hn, hospitalName,location, tag, businessDay,businessHour,lunchHour,hospitalPhoneNumber, imagePath }}) {
    const navigate = useNavigate();
    return <>
        <div className="mt-5">
            <img src={imagePath}/>
        </div>
        <div className="px-5 mt-5 flex flex-col h-[300px] overflow-auto w-full justify-between gap-5">
            <div>
                <h1 className="text-2xl font-bold">{hospitalName}</h1>
                <p>{location}</p>
            </div>
            <div>
                <h1 className="text-2xl font-bold mb-2">소개</h1>
                <p>{description}</p>
            </div>
            <div>
                <h1 className="text-2xl font-bold mb-5">진료시간안내</h1>
                <div className="flex bg-opacity-30 bg-yellow-300 rounded-2xl mx-5 justify-around text-xl py-2">
                    <div className="flex flex-col font-bold gap-2 ">
                        <p className="">{businessDay}</p>
                        <p>점심시간</p>
                    </div>
                    <div className="flex flex-col gap-2">
                        <p>{businessHour}</p>
                        <p>{lunchHour}</p>
                    </div>
                </div>
            </div>
            <div>
                <h1 className="text-2xl font-bold mb-2">전화번호</h1>
                <div className="flex justify-start items-center h-6 mb-5">
                    <IoCall/>
                    <p className="ml-2">{hospitalPhoneNumber}</p>
                </div>

            </div>

        </div>
    </>

}

