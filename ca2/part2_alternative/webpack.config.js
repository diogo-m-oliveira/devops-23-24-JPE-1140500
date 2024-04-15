const path = require('path');

module.exports = {
    entry: './src/main/js/app.js',
    devtool: 'source-map',
    cache: true,
    mode: 'development', // Or 'production' for optimized builds
    output: {
        path: path.resolve(__dirname, './src/main/resources/static/built'),
        filename: 'bundle.js',
    },
    devServer: {
        hot: true, // Enable hot module replacement
        open: true, // Automatically open the browser
        historyApiFallback: true,

    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env', '@babel/preset-react']
                    }
                }]
            },
            {
                test: /\.css$/, // Add a rule for CSS (optional)
                use: ['style-loader', 'css-loader']
            }
        ]
    }

};
