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
      const data = response.json();
      console.log(data);
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  return {
    addUser,
    logIn,
  };
};
