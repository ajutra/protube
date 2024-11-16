import type {Config} from 'jest';

const config: Config = {
  testEnvironment: "jsdom",
  setupFiles: ["<rootDir>/jest.polyfills.js"],
  testEnvironmentOptions: {
    customExportConditions: [''],
  },
  transform: {
    "^.+\\.tsx?$": "ts-jest",
  },
  moduleNameMapper: {
    "\\.(css|less|scss|sass)$": "identity-obj-proxy",
    "\\.(gif|ttf|eot|svg)$": "jest-transform-stub"
  }
};

export default config;