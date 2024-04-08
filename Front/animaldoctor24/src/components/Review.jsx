
export default function Review({review : {rn, hn, email, role, title, contents, imagePath, creationDate, updateDate }}) {

    return <>
        <li className="list-none mx-5 px-5 py-4 shadow-md rounded-xl mb-3">
            <div className="flex justify-between w-full mt-5 mb-5 ">
                <div className="flex">
                    <div className="w-[50px] h-[50px] ">
                        <img className="rounded-full object-center object-cover w-full h-full" src={imagePath}/>
                    </div>
                    <div className="pl-4">
                        <p className="font-bold">{email.substring(0, email.indexOf('@'))}</p>
                        <p className="text-gray-300">2024.04.08</p>
                    </div>
                </div>
                <div className="bg-gray-300 leading-10 h-full text-xs px-1.5 text-white font-bold rounded-md">
                        찐리뷰
                </div>
            </div>
            <div className="w-full mt-3 mb-5">
                <p>{contents}</p>
            </div>
        </li>

    </>

}

