const path = require('path');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  outputDir: path.resolve(__dirname, "./src/main/resources/static"),
  pages: {
    index: {
      entry: "./client/src/main.js",
      template: "./client/public/index.html"
    }
  },
  configureWebpack: {
    devServer: {
      proxy: {
        '/api': { target: 'http://localhost:3000' },
        '/uploads': { target: 'http://localhost:3000' }
      }
    },
    plugins: [
      new CopyWebpackPlugin({
        patterns: [{
          from: path.join(__dirname, "./client/public"),
          toType: "dir"
        }]
      })
    ]
  }
}
