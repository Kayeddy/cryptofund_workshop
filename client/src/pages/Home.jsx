import React, { useState, useEffect } from "react";

import { useStateContext } from "../context";
import { DisplayCampaigns } from "../components";
import { CustomButton } from "../components";

const Home = () => {
  const [loading, setLoading] = useState(false);
  const [campaigns, setCampaigns] = useState([]);

  const { address, contract, getCampaigns, getCampaignsFromDatabase } =
    useStateContext();

  const fetchCampaigns = async () => {
    setLoading(true);
    const campaigns = await getCampaignsFromDatabase();
    setCampaigns(campaigns);
    setLoading(false);
  };

  useEffect(() => {
    if (contract) fetchCampaigns();
  }, [address, contract]);

  return (
    <DisplayCampaigns
      title="All campaigns"
      loading={loading}
      campaigns={campaigns}
    />
  );
};

export default Home;
