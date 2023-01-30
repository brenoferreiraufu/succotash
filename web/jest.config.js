// eslint-disable-next-line @typescript-eslint/no-var-requires
const nextJest = require('next/jest')

const createJestConfig = nextJest({
  dir: './'
})

const customJestConfig = {
  setupFilesAfterEnv: ['<rootDir>/jest.setup.js'],
  moduleDirectories: ['node_modules', '<rootDir>/src/'],
  testEnvironment: 'jest-environment-jsdom',
  collectCoverage: true,
  collectCoverageFrom: [
    'src/**/*.ts(x)?',
    '!src/services/*',
    '!src/test/**/*',
    '!src/contexts/*',
    '!src/styles/*',
    '!src/pages/_app.tsx',
    '!src/pages/_document.tsx'
  ]
}

module.exports = createJestConfig(customJestConfig)
