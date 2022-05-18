const {resolve} = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const optimizeCssAssetsWebpackPlugin = require('optimize-css-assets-webpack-plugin');
const JavaScriptObfuscator = require('webpack-obfuscator');

module.exports = {
    entry: './start.js',
    output: {
        filename: 'js/built.js',
        path: resolve(__dirname, '../../../../../../../../page/dataStructureAlgorithmVisualization/heap')
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
            template: './heap.html'
        }),
        new MiniCssExtractPlugin({
            //对输出的文件重命名
            filename: 'css/built.css'
        }),
        //压缩css
        new optimizeCssAssetsWebpackPlugin(),
        new JavaScriptObfuscator({
            compact: true,//压缩代码
            controlFlowFlattening: true,//是否启用控制流扁平化(降低1.5倍的运行速度)
            controlFlowFlatteningThreshold: 1,//应用概率;在较大的代码库中，建议降低此值，因为大量的控制流转换可能会增加代码的大小并降低代码的速度。
            deadCodeInjection: true,//随机的死代码块(增加了混淆代码的大小)
            deadCodeInjectionThreshold: 1,//死代码块的影响概率
            debugProtection: true,//此选项几乎不可能使用开发者工具的控制台选项卡
            debugProtectionInterval: true,//如果选中，则会在“控制台”选项卡上使用间隔强制调试模式，从而更难使用“开发人员工具”的其他功能。
            disableConsoleOutput: true,//通过用空函数替换它们来禁用console.log，console.info，console.error和console.warn。这使得调试器的使用更加困难。
            identifierNamesGenerator: 'hexadecimal',//标识符的混淆方式 hexadecimal(十六进制) mangled(短标识符)
            log: false,
            renameGlobals: false,//是否启用全局变量和函数名称的混淆
            rotateStringArray: true,//通过固定和随机（在代码混淆时生成）的位置移动数组。这使得将删除的字符串的顺序与其原始位置相匹配变得更加困难。如果原始源代码不小，建议使用此选项，因为辅助函数可以引起注意。
            selfDefending: true,//混淆后的代码,不能使用代码美化,同时需要配置 cpmpat:true;
            stringArray: true,//删除字符串文字并将它们放在一个特殊的数组中
            stringArrayEncoding: 'rc4',
            stringArrayThreshold: 1,
            transformObjectKeys: true,
            unicodeEscapeSequence: false//允许启用/禁用字符串转换为unicode转义序列。Unicode转义序列大大增加了代码大小，并且可以轻松地将字符串恢复为原始视图。建议仅对小型源代码启用此选项。
        }, [])
    ],
    //生产环境下会自动压缩js代码
    mode: 'production'
}