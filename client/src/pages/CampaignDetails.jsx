import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { ethers } from "ethers";

import { useStateContext } from "../context";
import { CustomButton, InfoBox } from "../components";
import { calculateBarPercentage, daysLeft } from "../utils";
import { thirdweb } from "../assets";

const CampaignDetails = () => {
  const { state } = useLocation();
  const {
    getDonations,
    contract,
    address,
    userProfile,
    donateToCampaignInDatabase,
    retrieveCampaignDonators,
  } = useStateContext();

  const [loading, setLoading] = useState(false);
  const [amount, setAmount] = useState("");
  const [donators, setDonators] = useState([]);

  const remainingDays = daysLeft(state.deadline);

  const handleDonation = async () => {
    const donationData = {
      userId: userProfile.current.userId,
      campaignId: state.campaignId,
      amount: amount,
    };
    const donation = await donateToCampaignInDatabase(donationData);
    console.log(donation);
  };

  useEffect(async () => {
    const fetchDonators = async () => {
      const donatorsData = await retrieveCampaignDonators(state.campaignId);
      console.log("Donators data printed from use effect", donatorsData);
      setDonators(donatorsData);
    };
    fetchDonators();
  }, []);

  return (
    <div>
      {loading && "Loading..."}
      <div className="w-full flex md:flex-row flex-col mt-10 gap-[30px]">
        <div className="flex-1 flex-col">
          <img
            src="https://incyber.org/wp-content/uploads/2021/08/ARTICLE-CRYPTO-2-1.png"
            alt="campaign-image"
            className="w-full h-[410px] object-cover rounded-xl"
          />
          <div className="relative w-full h-[5px] bg-[3a3a43] mt-2 ">
            <div
              className="absolute h-full bg-[#4acd8d]"
              style={{
                width: `${calculateBarPercentage(0.5, 0.1)}%`,
                maxWidth: "100%",
              }}
            ></div>
          </div>
        </div>
      </div>
      <div className="mt-[60px] flex lg:flex-row flex-col gap-5">
        <div className="flex-1 flex flex-col gap-[40px]">
          <div className="">
            <h4 className="font-epilogue font-semibold text-[18px] text-white uppercase">
              Campaign information
            </h4>
            <div className="mt-[20px] flex flex-row items-center flex-wrap gap-[14px]">
              <div className="w-[52px] h-[52px] flex items-center justify-center rounded-full bg-[#2c2f32] cursor-pointer">
                <img
                  src={thirdweb}
                  alt="thirdweb image"
                  className="w-[60%] h-[60%] object-contain"
                />
              </div>
              <div>
                <h4 className="font-epilogue font-semibold text-[14px] text-white break-all">
                  Campaign id: {state.campaignId}
                </h4>
                <p className="mt-[4px] font-epilogue font-normal text-[12px] text-[#808191]">
                  Creator: {state.userId}
                </p>
              </div>
            </div>
          </div>

          <div className="">
            <h4 className="font-epilogue font-semibold text-[18px] text-white uppercase">
              Donators
            </h4>

            <div className="mt-[20px] flex flex-col gap-4">
              {donators.length > 0 ? (
                donators.map((item, index) => {
                  <div
                    key={index}
                    className="flex flex-col items-start justify-start gap-2 border-b-[1px] border-white"
                  >
                    <p>Donated by: {item.userId}</p>
                    <p>Amount Donated: {item.amount}</p>
                  </div>;
                })
              ) : (
                <p className="font-epilogue font-normal text-[16px] text-[#808191] leading-[26px] text-justify">
                  No donators yet
                </p>
              )}
            </div>
          </div>
        </div>

        <div className="flex-1 mt-4 md:mt-0 md:w-full">
          <h4 className="font-epilogue font-semibold text-[18px] text-white uppercase">
            Fund
          </h4>

          <div className="mt-[20px] flex flex-col p-4 bg-[#1c1c24] rounded-[10px]">
            <p className="font-epilogue font-medium text-[20px] leading-[30px] text-center text-[#808191]">
              Fund campaign
            </p>

            <div className="mt-[30px] ">
              <input
                type="number"
                placeholder="ETH 0.1"
                step="0.01"
                className="w-full py-[10px] sm:px-[20px] px-[50px] outline-none border-[1px] border-[#3a3a43] bg-transparent font-epilogue text-white text-[18px] leading-[30px] placeholder:text-[#4b5264] rounded-[10px]"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
              />
              <div className="mt-[20px] p-4 bg-[#13131a] rounded-[10px]">
                <h4 className="font-epilogue font-semibold text-[14px] leading-[22px] text-white">
                  Invest in it because you believe in it!
                </h4>
                <p className="mt-[20px] font-epilogue font-normal leading-[22px] text-[#808191]">
                  Support the project willingly to your heart's content, as much
                  as you want and for as long as you want.{" "}
                </p>
              </div>

              <CustomButton
                type="button"
                title="Fund campaign"
                styles="w-full bg-[#8c6dfd] py-4 mt-[20px]"
                handleClick={handleDonation}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CampaignDetails;
