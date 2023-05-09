import React from "react";
import { useNavigate } from "react-router-dom";

import { loader } from "../assets";
import { FundCard } from "../components";

const DisplayCampaigns = ({ title, loading, campaigns }) => {
  const navigate = useNavigate();

  const handleNavigate = (campaign) => {
    navigate(`/campaign-details/${campaign.title}`, { state: campaign });
  };

  return (
    <div>
      <h1 className="font-epilogue font-semibold text-[18px] text-white text-left">
        {title} ({campaigns.length})
      </h1>

      <div className="flex flex-wrap mt-[20px] gap-[26px]">
        {loading && (
          <img
            src={loader}
            alt="loader image"
            className="w-[100px] h-[100px] object-contain"
          />
        )}

        {!loading && campaigns.length == 0 && (
          <p className="font-epilogue font-semibold text-[14px] leaading-[30px] text-[#8181a3]">
            No campaigns available to show
          </p>
        )}

        {!loading &&
          campaigns.length > 0 &&
          campaigns.map((campaign) => (
            <FundCard
              key={campaign.id}
              {...campaign}
              handleClick={() => handleNavigate(campaign)}
            />
          ))}
      </div>
    </div>
  );
};

export default DisplayCampaigns;
