module.exports={
    testEnvironment:"jest-environment-jsdom",
    setupFilesAfterEnv:["<rootDir>/.jest/setup-test.js"],
    moduleNameMapper:{
        "\\.(gif|ttf|svg|jpg|jpeg)$":"<rootDir>/.jest/mocks/fileMock.js",
        "\\.(css|less|sass|scss)$":"identity-obj-proxy"
    }
}