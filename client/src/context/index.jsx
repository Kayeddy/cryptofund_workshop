import React, { useContext, createContext } from "react";
import {
  useAddress,
  useContract,
  useMetamask,
  useContractWrite,
} from "@thirdweb-dev/react";
import { ethers } from "ethers";

import { authHandler } from "../api";

const StateContext = createContext();

export const StateContextProvider = ({ children }) => {
  // Hooks and other required data
  const { contract } = useContract(
    "0x2DA65c65065bEf5Cd21dc532501cE0473A6dEaa5"
  );

  const { mutateAsync: createCampaign } = useContractWrite(
    contract,
    "createCampaign"
  );

  const { addUser, logIn } = authHandler();

  const address = useAddress();
  const connect = useMetamask();

  let user = null;

  // User interaction methods

  const authenticateUser = async (type, data) => {
    try {
      await connect();
      data.walletAddress = address;

      if (type === "login") {
        user = await logIn(data);
        console.log("login successful");
      } else {
        user = await addUser(data);
        console.log("signup successful");
      }
      console.log(user);
      return Promise.resolve(user);
    } catch (error) {
      console.log(`${type} failed: ${error}`);
      return Promise.reject(error);
    }
  };

  // Campaign interaction methods
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
        user,
        connect,
        getCampaigns,
        createCampaign: publishCampaign,
        authenticateUser,
      }}
    >
      {children}
    </StateContext.Provider>
  );
};

export const useStateContext = () => useContext(StateContext);
