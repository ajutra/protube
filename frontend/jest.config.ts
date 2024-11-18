import type { Config } from 'jest'

const config: Config = {
  testEnvironment: 'jsdom',
  setupFiles: ['<rootDir>/jest.polyfills.js', '<rootDir>/jest.setup.js'],
  setupFilesAfterEnv: ['./setupTests.js'],
  testEnvironmentOptions: {
    customExportConditions: [''],
  },
  transform: {
    '^.+\\.tsx?$': 'ts-jest',
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
    '\\.(css|less|scss|sass)$': 'identity-obj-proxy',
    '\\.(gif|ttf|eot|svg)$': 'jest-transform-stub',
    '^@/(.*)$': '<rootDir>/src/$1',
  },
}

export default config
