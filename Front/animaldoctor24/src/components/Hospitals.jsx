import React, {useEffect, useState} from 'react';
import HospitalCard from "./HospitalCard";
import {getHospitals} from "../api/Hospitals";
import {getReviews} from "../api/Reviews";

export default function Hospitals() {
    const [hospitals, setHospitals] = useState([]);
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        getHospitals()
            .then((res)=> setHospitals(res.data))
            .catch((error)=> setErrorMsg(error.response.data));
    }, []);

    return (
        <>
            <ul className="mt-[30px] w-full flex flex-col justify-center items-center">
                <div>
                    {errorMsg && <p>등록된 동물병원이 없습니다.</p>}
                </div>
                {hospitals &&
                    hospitals.slice(0, 2).map((hospital) => (
                        <HospitalCard key={hospital.hn} hospital={hospital} />
                    ))}
            </ul>

        </>
    );
}
