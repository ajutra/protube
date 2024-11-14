/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/**/*.{html,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        'custom-gray': '#a0a0a0',
        'custom-gray-dark': '#666666',
      },
    },
  },
  plugins: [],
}