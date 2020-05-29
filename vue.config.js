const path = require('path');

module.exports = {
  outputDir: path.resolve(__dirname, "./src/main/resources/static"),
  pages: {
    index: {
      entry: "./client/src/main.js",
      template: "./client/public/index.html",
      filename: "index.html"
    }
  },
  configureWebpack: {
    devServer: {
      proxy: {
        '/api': { target: 'http://localhost:3000' },
        '/uploads': { target: 'http://localhost:3000' }
      }
    }
  }
}
