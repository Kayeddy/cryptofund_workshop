export const authHandler = () => {
  const addUser = async (userData) => {
    try {
      const response = await fetch("http://localhost:8083/api/users/post", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
      });
      const data = await response.json();
      console.log(data);
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  const logIn = async (userData) => {
    try {
      const response = await fetch(
        "http://localhost:8083/api/users/get/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(userData),
        }
      );
      const data = await response.json();
      console.log("The data is: ", data);
      return data;
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  const addCampaignToDatabase = async (campaign) => {
    try {
      const response = await fetch("http://localhost:8081/api/campaigns/post", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(campaign),
      });
      const data = await response.json();
      console.log("The data is: ", data);
      return data;
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  const retrieveCampaigns = async () => {
    try {
      const response = await fetch("http://localhost:8081/api/campaigns/get", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });
      const data = await response.json();
      console.log("Retrieved campaigns from database: ", data);
      return data;
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  const donateToCampaign = async (donationData) => {
    try {
      const response = await fetch("http://localhost:8082/api/donations/post", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(donationData),
      });
      const data = await response.json();
      return data;
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  const getCampaignDonators = async (campaignId) => {
    try {
      const response = await fetch(
        `http://localhost:8082/api/donations/get/campaign/${campaignId}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const data = await response.json();
      console.log("Donation information by project", data);
      return data;
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  return {
    addUser,
    logIn,
    addCampaignToDatabase,
    retrieveCampaigns,
    donateToCampaign,
    getCampaignDonators,
  };
};
