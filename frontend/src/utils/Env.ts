export const getEnv = () => {
  const { VITE_API_DOMAIN, VITE_MEDIA_DOMAIN, ...otherViteConfig } = import.meta
    .env

  return {
    API_BASE_URL: `${VITE_API_DOMAIN}/api`,
    API_VIDEOS_URL: `${VITE_API_DOMAIN}/api/videos`,
    MEDIA_BASE_URL: `${VITE_MEDIA_DOMAIN}/media`,
    __vite__: otherViteConfig,
  }
}
