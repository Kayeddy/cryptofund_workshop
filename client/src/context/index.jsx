import React, { useContext, createContext } from "react";
import {
  useAddress,
  useContract,
  useMetamask,
  useContractWrite,
} from "@thirdweb-dev/react";
import { ethers } from "ethers";

const StateContext = createContext();

export const StateContextProvider = ({ children }) => {
  const { contract } = useContract(
    "0x2DA65c65065bEf5Cd21dc532501cE0473A6dEaa5"
  );

  const { mutateAsync: createCampaign } = useContractWrite(
    contract,
    "createCampaign"
  );

  const address = useAddress();
  const connect = useMetamask();

  const publishCampaign = async (form) => {
    try {
      const data = await createCampaign([
        address, //address of the person who is creating the campaign
        form.title, //title of the campaign
        form.description, //description of the campaign
        form.target, //target fund raise amount of the campaign
        new Date(form.deadline).getTime(), //deadline for raising funds for the campaign
        form.image, //image representative of the campaign
      ]);
      console.log("Contract call success", data);
    } catch (error) {
      console.log("Contract call error", error);
    }
  };

  const getCampaigns = async () => {
    const campaigns = await contract.call("getCampaigns");
    const parsedCampaigns = campaigns.map((campaign, i) => ({
      owner: campaign.owner,
      title: campaign.title,
      description: campaign.description,
      target: ethers.utils.formatEther(campaign.target.toString()),
      deadline: campaign.deadline.toNumber(),
      amountCollected: ethers.utils.formatEther(
        campaign.amountCollected.toString()
      ),
      image: campaign.image,
      pid: i,
    }));
    return parsedCampaigns;
  };

  const getUserCampaigns = async () => {
    const allCampaigns = await getCampaigns();

    const filteredCampaigns = allCampaigns.filter(
      (campaign) => campaign.owner === address
    );

    return filteredCampaigns;
  };

  const donateToCampaign = async (pid, amount) => {
    const data = await contract.call("donateToCampaign", pid, address, amount);

    return data;
  };

  /*
  const donateToCampaign = async (id, value) => {
    const data = await contract.call("donateToCampaign", id, contractAddress, value);

    return data;
  };
  */

  const getDonations = async (pid) => {
    const donations = await contract.call("getDonators", pid);
    const numberOfDonations = donations[0].length;

    const parsedDonations = [];

    for (let index = 0; index < numberOfDonations; index++) {
      parsedDonations.push({
        donator: donations[0][index],
        donation: ethers.utils.formatEther(donations[1][index]).toString(),
      });
    }

    return parsedDonations;
  };

  return (
    <StateContext.Provider
      value={{
        address,
        contract,
        connect,
        getCampaigns,
        createCampaign: publishCampaign,
      }}
    >
      {children}
    </StateContext.Provider>
  );
};

export const useStateContext = () => useContext(StateContext);
