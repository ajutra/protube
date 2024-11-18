import { getEnv } from "@/utils/Env";


export const fetchUserComments = async (username: string) => {
  const response = await fetch(getEnv().API_BASE_URL + `/users/${username}/comments`);
  const data = await response.json();
  return data;
};