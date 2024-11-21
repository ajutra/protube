import type { Config } from 'jest'

const config: Config = {
  testEnvironment: 'jsdom',
  setupFiles: ['<rootDir>/jest.polyfills.js'],
  setupFilesAfterEnv: ['<rootDir>/jest.setup.js'],
  testEnvironmentOptions: {
    customExportConditions: [''],
  },
  transform: {
    '^.+\\.tsx?$': 'ts-jest', // TypeScript transform
  },
  extensionsToTreatAsEsm: ['.ts', '.tsx'],
  coverageThreshold: {
    global: {
      branches: 80,
      functions: 80,
      lines: 80,
      statements: 80,
    },
  },
  coveragePathIgnorePatterns: ['<rootDir>/src/hooks/use-toast.ts'],
  moduleNameMapper: {
    '\\.(css|less|scss|sass)$': 'identity-obj-proxy',
    '\\.(gif|ttf|eot|svg)$': 'jest-transform-stub',
    '^@/(.*)$': '<rootDir>/src/$1',
  },
}

export default config
