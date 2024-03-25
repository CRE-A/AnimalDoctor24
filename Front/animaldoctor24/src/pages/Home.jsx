import React from 'react';
import Nav from "../components/Nav";
import {useNavigate} from "react-router-dom";


export default function Home() {
    const navigate = useNavigate();
    return (
        <>
            <section className="relative flex flex-col items-center w-[393px] h-[852px] bg-white ">
                <Nav/>
                <div className="flex flex-col items-center">
                    <div className="w-[316px] h-[316px] my-[40px]">
                        <img src="https://loosedrawing.com/assets/illustrations/png/1058.png" />
                    </div>
                    <button onClick={()=>{
                    navigate('/login')}
                    } className="w-[254px] h-[72px] bg-yellow-300 font-bold text-lg">로그인</button>
                    <button className="w-[254px] h-[72px] bg-gray-200 font-bold text-lg my-[20px]">그냥 둘러보기</button>
                </div>
            </section>
        </>
    );
}


