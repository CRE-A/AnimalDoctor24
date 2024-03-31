import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {getUserById} from "../api/Users";

export default function RegisterPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };


    const handleSubmit = (event) => {
        event.preventDefault();
        try{
            const res = getUserById({email, password});
            console.log('응답오는지 확인 :' , res);
            setEmail('email');
            setPassword('password');
        }catch (e) {
            console.log(e);
        }

    };
    return (
        <>
            <section className="relative flex flex-col items-center bg-white w-[393px] h-[782px] ">
                <button className="w-[126px] h-[49px] rounded-[27px] bg-yellow-300 font-bold text-white text-[15px] mt-[50px]">회원가입</button>
                <h1 className="font-bold relative top-[15px] text-lg">Animal Doctor 24 가입하기!</h1>
                <div className="relative bottom-[30px]">
                    <img src="https://loosedrawing.com/assets/illustrations/png/506.png" className="w-[316px] h-[316px]"/>
                </div>
                <form onSubmit={handleSubmit} className="relative bottom-[60px]">
                    <div>
                        <input
                            className="w-[342px] h-[54px] border-2 mb-[20px] focus:outline-none px-[10px] "
                            placeholder="이메일"
                            type="text"
                            id="email"
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                    </div>
                    <div>
                        <input
                            className="w-[342px] h-[54px] border-2 mb-[20px] focus:outline-none px-[10px] "
                            placeholder="비밀번호"
                            type="password"
                            id="password"
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                    </div>
                    <button type="submit" className="w-[342px] h-[76px] text-[24px] bg-yellow-300 font-bold text-white text-lg">가입하기</button>
                </form>
                <div className="relative bottom-[20px]">
                    <span className="mr-[5px]">계정이 있으신가요?</span>
                    <button onClick={()=>{navigate('/login')}} className="font-bold">로그인</button>
                </div>
            </section>
        </>

    ); ;
}
