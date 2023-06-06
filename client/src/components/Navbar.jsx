import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import { CustomButton } from "../components";
import { logo, menu, search, thirdweb } from "../assets";

import { navlinks } from "../constants";
import { useStateContext } from "../context";

const Navbar = () => {
  const navigate = useNavigate();
  const [active, setActive] = useState("dashboard");
  const [toggleDrawer, setToggleDrawer] = useState(false);
  const { connect, address, userProfile } = useStateContext();

  return (
    <div className="flex md:flex-row flex-col-reverse justify-between mb-[35px] gap-6">
      <div className="sm:flex hidden justify-end gap-4 text-white text-lg">
        Welcome, {userProfile.current ? userProfile.current.name : "Guest"}
      </div>

      {/* Small screens navigation */}

      <div className="sm:hidden flex justify-between items-center relative">
        <div className="w-[40px] h-[40px] rounded-[10px] bg-[#2c2f32] flex justify-center items-center cursor-pointer">
          <img
            src={logo}
            alt="user profile"
            className="w-[60%] h-[40%] object-contain"
          />
        </div>

        <img
          src={menu}
          alt="menu"
          className="w-[25px] h-[25px] object-contain cursor-pointer"
          onClick={() => {
            setToggleDrawer((prev) => !prev);
          }}
        />

        <div
          className={`absolute top-[60px] right-0 left-0 bg-[#1c1c24] z-10 shadow-secondary py-4 ${
            !toggleDrawer ? "-translate-y-[100vh]" : "translate-y-0"
          } transition-all duration-700`}
        >
          <ul className="mb-4">
            {navlinks.map((link) => (
              <li
                key={link.name}
                className={`flex p-4 ${
                  active === link.name && "bg-[#3a3a43]"
                } cursor-pointer`}
                onClick={() => {
                  setActive(link.name);
                  setToggleDrawer(false);
                  navigate(link.link);
                }}
              >
                <img
                  src={link.imgUrl}
                  alt={link.name}
                  className={`w-[24px] h-[24px] object-contain ${
                    active === link.name ? "grayscale-0" : "grayscale"
                  }`}
                />
                <p
                  className={`ml-[20px] font-epilogue font-semibold text-[14px] ${
                    active === link.name ? "text-[#1dc071]" : "text-[#808191]"
                  }`}
                >
                  {link.name}
                </p>
              </li>
            ))}
          </ul>

          <div className="flex mx-4"></div>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
