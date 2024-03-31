import React from 'react';
import SearchBar from "../components/SearchBar";
import Hospitals from "../components/Hospitals";
export default function MainPage() {
    return (
        <>
            <section className="relative flex flex-col items-center bg-white w-[393px] h-[782px] ">
                <div className="relative left-[-40px] top-[40px] mt-[10px]">
                    <h1 className="text-[20px] font-bold">
                        <span className="text-yellow-300">신설동 </span>
                        주변의</h1>
                    <h1 className="text-[20px] font-bold">동물병원을 찾고 계신가요?</h1>
                </div>
                <div className="flex items-center">
                    <button className="relative left-[35px] bottom-[30px] w-[128px] h-[50px] text-[16px] bg-yellow-300 bg-opacity-30 font-bold text-yellow-300 text-lg rounded-[8px]" >
                        위치설정변경
                    </button>
                    <div className="relative top-[14px] left-[5px]">
                        <img src="https://loosedrawing.com/assets/illustrations/png/1056.png" className="w-[287px] h-[287x]"/>
                    </div>
                </div>
                <SearchBar/>
                <h1 className="relative left-[-58px] top-[21px] text-[20px] font-bold"> 신설동 주변의 동물병원</h1>
                <Hospitals/>
            </section>
        </>
    );
}


