import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ethers } from "ethers";

import { money } from "../assets";
import { CustomButton, FormField } from "../components";
import { checkIfImage } from "../utils";

import { useStateContext } from "../context";

const CreateCampaign = () => {
  const navigate = useNavigate();
  const { createCampaign, userProfile, saveCampaignToDatabase } =
    useStateContext();

  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState({
    username: userProfile.current.name,
    title: "",
    description: "",
    goal: "",
    userId: userProfile.current.userId,
    deadline: "",
    image:
      "https://thegivingblock.com/wp-content/uploads/2021/07/Learn-Crypto-The-Giving-Block.png",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    checkIfImage(form.image, async (exists) => {
      if (exists) {
        setLoading(true);
        await saveCampaignToDatabase({
          ...form,
        });
        setLoading(false);
        navigate("/dashboard");
      } else {
        alert("Please provide a valid image url");
        setForm({ ...form, image: "" });
      }
    });
  };

  const handleFormFieldCchange = (fieldName, e) => {
    setForm({
      ...form,
      [fieldName]: e.target.value,
    });
  };

  return (
    <div className="bg-[#1c1c24] flex justify-center items-center flex-col rounded-[10px] p4 sm:p-10">
      {loading && "Loader..."}
      <div className="flex justify-center items-center p-[16px] sm:min-w-[380px] bg-[#3a3a43] rounded-[10px]">
        <h1 className="font-epilogue font-bold text-[18px] sm:text-[25px] leading-[38px] text-white">
          Start a campaign
        </h1>
      </div>

      <form
        className="w-full mt-[65px] flex flex-col gap-[30px]"
        onSubmit={handleSubmit}
      >
        <div className="flex items-start justify-start">
          <FormField
            labelName="Campaign title *"
            placeholder="My first startup"
            inputType="text"
            value={form.title}
            handleChange={(e) => handleFormFieldCchange("title", e)}
          />
        </div>

        <FormField
          labelName="Description *"
          placeholder="Describe in depth your campaign..."
          isTextArea
          value={form.description}
          handleChange={(e) => handleFormFieldCchange("description", e)}
        />

        <div className="w-full flex justify-start items-center p-4 bg-[#8c6dfd] h-[120px] rounded-[10px]">
          <img
            src={money}
            alt="money icon"
            className="w-[40px] h-[40px] object-contain"
          />
          <h4 className="font-epilogue font-bold text-[25px] text-white ml-[20px]">
            You will get 100% of the raised amount
          </h4>
        </div>

        <div className="flex flex-wrap gap-[40px]">
          <FormField
            labelName="Goal *"
            placeholder="ETH 0.50"
            inputType="text"
            value={form.goal}
            handleChange={(e) => handleFormFieldCchange("goal", e)}
          />
          <FormField
            labelName="End date *"
            placeholder="Input a date"
            inputType="text"
            value={form.deadline}
            handleChange={(e) => handleFormFieldCchange("deadline", e)}
          />

          <div className="flex justify-center items-center mt-[40px] w-full">
            <CustomButton
              type="submit"
              title="Submit new campaign"
              styles="bg-[#1dc071] p-5 mb-5"
            />
          </div>
        </div>
      </form>
    </div>
  );
};

export default CreateCampaign;
