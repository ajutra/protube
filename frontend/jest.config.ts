import type {Config} from 'jest';

const config: Config = {
  testEnvironment: "jsdom",
  setupFiles: ["<rootDir>/jest.polyfills.js", "<rootDir>/jest.setup.js"],
  setupFilesAfterEnv: [
    './setupTests.js',
  ],
  testEnvironmentOptions: {
    customExportConditions: [''],
  }
};


export default config;
