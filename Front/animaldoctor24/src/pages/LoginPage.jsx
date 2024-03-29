import React, {useState} from 'react';
import Nav from "../components/Nav";

export default function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Logging in with:', { username, password });
        setUsername('');
        setPassword('');
    };
    return (
        <>
            <section className="relative flex flex-col items-center bg-white w-[393px] h-[782px] ">
                <button className="w-[126px] h-[49px] rounded-[27px] bg-yellow-300 font-bold text-white text-[15px] mt-[50px]">로그인</button>
                <h1 className="font-bold relative top-[15px] text-lg">Animal Doctor 24 로그인하기!</h1>
                <div className="relative bottom-[30px]">
                    <img src="https://loosedrawing.com/assets/illustrations/png/593.png" className="w-[316px] h-[316px]"/>
                </div>
                <form onSubmit={handleSubmit} className="relative bottom-[70px]">
                    <div>
                        <input
                            className="w-[342px] h-[54px] border-2 mb-[20px] focus:outline-none px-[10px] "
                            placeholder="이메일"
                            type="text"
                            id="username"
                            value={username}
                            onChange={handleUsernameChange}
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
                    <button type="submit" className="w-[342px] h-[76px] text-[24px] bg-yellow-300 font-bold text-white text-lg">로그인</button>
                </form>
                <div className="relative bottom-[30px]">
                    <span className="mr-[5px]">계정이 아직 없으신가요?</span>
                    <span className="font-bold">회원가입</span>
                </div>
            </section>
        </>

    ); ;
}
