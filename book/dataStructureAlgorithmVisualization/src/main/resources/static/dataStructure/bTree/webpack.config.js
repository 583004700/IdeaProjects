const {resolve} = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const optimizeCssAssetsWebpackPlugin = require('optimize-css-assets-webpack-plugin');

module.exports = {
    entry: './start.js',
    output: {
        filename: 'js/built.js',
        path: resolve(__dirname, '../../../../../../../page/bTree')
    },
    module: {
        rules: [
            /*
                js兼容性处理：babel-loader  @babel/core  @babel/preset-env
                    1.基本js兼容性处理 --> @babel/preset-env
                      问题：只能转换基本语法，如promise不能转换
                    2.全部js兼容性处理 --> @babel/polyfill
                      问题：我只要解决部分兼容性问题，但是将所有兼容性代码全部引入，体积太大了~
                    3.需要做兼容性处理的就做：按需加载 --> core-js
             */
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel-loader',
                options: {
                    //预设：指示babel做怎么样的兼容性处理
                    presets: [
                        ['@babel/preset-env',
                            {
                                useBuiltIns: 'usage',
                                //指定core-js版本
                                corejs: {
                                    version: 3
                                },
                                //指定兼容性做到哪个版本浏览器
                                targets: {
                                    chrome: '60',
                                    firefox: '60',
                                    safari: '10',
                                    edge: '17'
                                }
                            }
                        ]
                    ]
                }
            },
            {
                test: /\.css$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    'css-loader',
                    {
                        loader: 'postcss-loader',
                        options: {
                            ident: 'postcss',
                            plugins: () => [
                                //postcss的插件
                                require('postcss-preset-env')()
                            ]
                        }
                    }
                ]
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './BTree.html'
        }),
        new MiniCssExtractPlugin({
            //对输出的文件重命名
            filename: 'css/built.css'
        }),
        //压缩css
        new optimizeCssAssetsWebpackPlugin()
    ],
    //生产环境下会自动压缩js代码
    mode: 'production'
}