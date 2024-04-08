import React, {useState} from 'react';
import { FaArrowLeft } from "react-icons/fa6";
import { useLocation} from "react-router-dom";
import Info from "../components/Info";
import Review from "../components/Review";


export default function HospitalDetails() {

    const [isInfoFocused, setInfoFocused] = useState(true);
    const [isReviewFocused, setReviewFocused] = useState(false);
    const handleSwitch = (type) => {
        if (type === 'info') {
            setInfoFocused(true);
            setReviewFocused(false);
        } else if (type === 'review') {
            setInfoFocused(false);
            setReviewFocused(true);
        }
    }
    const {
        state: {
            hospital, reviews
        },
    } = useLocation();
    return (
        <>
            <section className="relative flex flex-col items-center bg-white w-[393px] h-[782px] ">
                <div className="flex justify-around w-full my-5" >
                    <FaArrowLeft/>
                    <span className="font-bold">동물병원이름</span>
                    <span></span>
                </div>
                <div className="flex flex-center gap-6 text-2xl font-bold">
                    <nav  onClick={() => handleSwitch('info')}>소개</nav>
                    <nav  onClick={() => handleSwitch('review')}>리뷰</nav>
                </div>

                {isInfoFocused && <Info hospital={hospital}/>}
                <ul className="mt-10">
                    {isReviewFocused && reviews.map((review)=>(
                        <Review key={review.rn} review={review}/>
                    )) }
                </ul>


            </section>
        </>
    );
}


