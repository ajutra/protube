import { getEnv } from "@/utils/Env";
import { useEffect, useState } from 'react';

export const fetchUserComments = async (username: string) => {
  const response = await fetch(getEnv().API_BASE_URL + `/users/${username}/comments`);
  const data = await response.json();
  return data;
};

const useFetchAllUserComments = (username: string) => {
  const [comments, setComments] = useState<{ text: string }[]>([]);

  useEffect(() => {
    const getComments = async () => {
      if (username) {
        const userComments = await fetchUserComments(username);
        setComments(userComments);
      }
    };

    getComments();
  }, [username]);

  return comments;
};

export default useFetchAllUserComments;