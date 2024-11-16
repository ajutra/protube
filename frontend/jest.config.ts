import type { Config } from "jest";

const config: Config = {
  testEnvironment: "jsdom",
  setupFiles: ["<rootDir>/jest.polyfills.js", "<rootDir>/jest.setup.js"],
  setupFilesAfterEnv: ["./setupTests.js"],
  testEnvironmentOptions: {
    customExportConditions: [""],
  },
  coverageThreshold: {
    global: {
      branches: 75,
      functions: 75,
      lines: 75,
      statements: 75,
    },
  },
  moduleNameMapper: {
    "\\.(svg)$": "<rootDir>/__mocks__/fileMock.js",
  },
};

export default config;
