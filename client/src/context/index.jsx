import React, { useContext, createContext, useState } from "react";
import {
  useAddress,
  useContract,
  useMetamask,
  useContractWrite,
} from "@thirdweb-dev/react";
import { ethers } from "ethers";
import detectEthereumProvider from "@metamask/detect-provider";

import { authHandler } from "../api";

const StateContext = createContext();

let user = {};

export const StateContextProvider = ({ children }) => {
  // States
  const [provider, setProvider] = useState(null);
  const [metamaskAddress, setMetamaskAddress] = useState(null);

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

  const connectMetamask = async () => {
    const provider = await detectEthereumProvider();

    if (provider) {
      // Check if this is Metamask
      if (provider.isMetaMask) {
        console.log("MetaMask is installed!");

        // Now we try to get the user account.
        try {
          const accounts = await provider.request({
            method: "eth_requestAccounts",
          });
          console.log(accounts[0]); // This will log the user's account address.
          setProvider(provider);
          setMetamaskAddress(accounts[0]);

          // Set up the listener for disconnects
          provider.on("accountsChanged", (accounts) => {
            if (!accounts.length) {
              // User has disconnected their wallet
              setProvider(null);
              console.log("User has disconnected their wallet");
              // Add your logic here
            }
          });
        } catch (error) {
          console.error(error);
        }
      }
    } else {
      console.log("Please install MetaMask!");
      window.open("https://metamask.io/download/", "_blank");
    }
  };

  // User interaction methods

  const authenticateUser = async (type, data) => {
    try {
      await connectMetamask();
      const modifiedData = data;
      modifiedData.walletAdress = metamaskAddress;
      console.log(
        "Here is the modified data (with the wallet address) that will be stored in the database",
        modifiedData
      );

      if (type === "login") {
        user = await logIn(modifiedData);
        console.log("login successful");
      } else {
        user = await addUser(modifiedData);
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
