import { FaWifi } from "react-icons/fa";
import { IoBatteryFull } from "react-icons/io5";
import { FaSignal } from "react-icons/fa";
export default function Nav() {
    return (
        <section className="flex justify-between w-[393px] pl-[60px] pr-[40px] py-5 border-b-2 border-gray-100">
            <div className="font-bold text-lg">9:41</div>
            <div className="flex items-center justify-between w-[70px] text-lg">
                <FaSignal />
                <FaWifi />
                <IoBatteryFull />
            </div>
        </section>
    );
}

