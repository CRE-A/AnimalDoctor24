import React from 'react';
import { FaHeart } from "react-icons/fa";
import { IoCall } from "react-icons/io5";
import {useNavigate} from "react-router-dom";


export default function HospitalCard() {
    const navigate = useNavigate();
    return <>
            <li className="flex justify-normal w-[324px]">
                <div className="w-[100px] h-[100px] ">
                    <img src="https://loosedrawing.com/assets/illustrations/png/99.png" />
                </div>
                <div className="flex flex-col flex-grow justify-between pl-[10px]">
                    <div>
                        <div className="flex justify-between items-center">
                            <h1 className="text-[16px] font-bold">동물병원이름</h1>
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
