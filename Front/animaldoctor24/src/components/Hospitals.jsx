import React from 'react';
import HospitalCard from "./HospitalCard";

export default function Hospitals() {
    // const hospitals = getHospitals();
    return (
        <>

            <ul className="mt-[30px] w-full flex justify-center">
                {/*{hospitals &&*/}
                {/*    hospitals.map((hospital) => (*/}
                {/*        <HospitalCard key={hospital.id} hospital={hospital} />*/}
                {/*    ))}*/}
                <HospitalCard/>
            </ul>

        </>
    );
}
