import type { Config } from 'jest'

const config: Config = {
  testEnvironment: 'jsdom',
  setupFiles: ['<rootDir>/jest.polyfills.js'], // Mantén solo archivos compatibles aquí
  setupFilesAfterEnv: ['<rootDir>/jest.setup.js'], // Este archivo puede usar imports
  testEnvironmentOptions: {
    customExportConditions: [''],
  },
  transform: {
    '^.+\\.tsx?$': 'ts-jest', // TypeScript transform
  },
  extensionsToTreatAsEsm: ['.ts', '.tsx'], // Añade estas extensiones si usas ES Modules
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
};

export default config;
