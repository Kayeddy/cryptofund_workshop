import React, { useState, useRef } from "react";
import { CustomButton } from "../components";
import { useStateContext } from "../context";
import { authHandler } from "../api";
import { useNavigate } from "react-router-dom";

const Authentication = () => {
  const [active, setActive] = useState("login");
  const { connect, address } = useStateContext();
  const { addUser, logIn } = authHandler();
  const { authenticateUser, user } = useStateContext();
  const navigate = useNavigate();

  const loginEmailRef = useRef(null);
  const loginPasswordRef = useRef(null);
  const registrationNameRef = useRef(null);
  const registrationEmailRef = useRef(null);
  const registrationBirthdateRef = useRef(null);
  const registrationAboutRef = useRef(null);
  const registrationPasswordRef = useRef(null);

  const authenticate = () => {
    if (active === "login") {
      const email = loginEmailRef.current;
      const password = loginPasswordRef.current;

      const userData = {
        email: email,
        password: password,
        walletAddress: "",
      };
      authenticateUser(active, userData)
        .then((user) => {
          user ? navigate("/home") : alert("Invalid credentials");
        })
        .catch((error) => {
          // handle error
          console.log(error);
        });
    } else {
      const name = registrationNameRef.current.value;
      const email = registrationEmailRef.current.value;
      const birthdate = registrationBirthdateRef.current.value;
      const about = registrationAboutRef.current.value;
      const password = registrationPasswordRef.current.value;

      const userData = {
        name: name,
        email: email,
        birthdate: birthdate,
        about: about,
        password: password,
        walletAddress: "",
      };

      authenticateUser(active, userData)
        .then((user) => {
          user
            ? navigate("/home")
            : alert("There was a problem creating your account");
        })
        .catch((error) => {
          // handle error
          console.log(error);
        });
    }
  };

  const handleTabChange = () => {
    setActive((prev) => (prev === "login" ? "signup" : "login"));
  };

  return (
    <div className="min-w-screen min-h-screen w-screen h-screen fixed top-0 left-0">
      <div className="w-full h-full flex items-center justify-center bg-white bg-opacity-10 backdrop-blur-md">
        <div className="w-[450px] h-[550px] p-4 bg-[#13131a] shadow-md rounded-md">
          <div className="w-full h-full flex flex-col items-center justify-between">
            <section className="w-full h-fit flex flex-row items-center justify-around bg-slate-800 rounded-md">
              <div
                className={`${
                  active === "login" ? "bg-[#4acd8d]" : "bg-transparent"
                } rounded-md w-full text-white text-center cursor-pointer transition-all duration-100 ease-in-out`}
                onClick={handleTabChange}
              >
                <p className="p-2">Log-in</p>
              </div>
              <div
                className={`${
                  active === "signup" ? "bg-[#4acd8d]" : "bg-transparent"
                } rounded-md w-full text-white text-center cursor-pointer transition-all duration-100 ease-in-out`}
                onClick={handleTabChange}
              >
                <p className="p-2">Sign-up</p>
              </div>
            </section>

            {active === "login" ? (
              <section className="w-full h-full flex flex-col items-center justify-start gap-4 mt-10 overflow-y-auto">
                <input
                  type="text"
                  placeholder="Email"
                  ref={loginEmailRef}
                  className="w-[80%] rounded-md h-12 bg-transparent placeholder:text-white border-[1px] border-[#4acd8d] p-2 text-white"
                />
                <input
                  type="password"
                  placeholder="Password"
                  ref={loginPasswordRef}
                  className="w-[80%] rounded-md h-12 bg-transparent placeholder:text-white border-[1px] border-[#4acd8d] p-2 text-white"
                />
                <CustomButton
                  type="button"
                  title="Connect wallet and continue"
                  styles="min-h-[52px] bg-[#1dc071]"
                  handleClick={authenticate}
                />
              </section>
            ) : (
              <section className="w-full h-full flex flex-col items-center justify-around">
                <input
                  type="text"
                  placeholder="Name"
                  ref={registrationNameRef}
                  className="w-[80%] rounded-md h-12 bg-transparent placeholder:text-white border-[1px] border-[#4acd8d] p-2 text-white"
                />
                <input
                  type="text"
                  placeholder="Email"
                  ref={registrationEmailRef}
                  className="w-[80%] rounded-md h-12 bg-transparent placeholder:text-white border-[1px] border-[#4acd8d] p-2 text-white"
                />
                <input
                  type="text"
                  placeholder="Birthdate"
                  ref={registrationBirthdateRef}
                  className="w-[80%] rounded-md h-12 bg-transparent placeholder:text-white border-[1px] border-[#4acd8d] p-2 text-white"
                />
                <input
                  type="text"
                  placeholder="About"
                  ref={registrationAboutRef}
                  className="w-[80%] rounded-md h-12 bg-transparent placeholder:text-white border-[1px] border-[#4acd8d] p-2 text-white"
                />

                <input
                  type="password"
                  placeholder="Password"
                  ref={registrationPasswordRef}
                  className="w-[80%] rounded-md h-12 bg-transparent placeholder:text-white border-[1px] border-[#4acd8d] p-2 text-white"
                />

                <CustomButton
                  type="button"
                  title="Connect wallet and continue"
                  styles="min-h-[52px] bg-[#1dc071]"
                  handleClick={authenticate}
                />
              </section>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Authentication;
