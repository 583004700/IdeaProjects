const {resolve} = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const optimizeCssAssetsWebpackPlugin = require('optimize-css-assets-webpack-plugin');
const JavaScriptObfuscator = require('webpack-obfuscator');
const TerserWebpackPlugin = require('terser-webpack-plugin');

module.exports = {
    entry: {
        'dataStructure/aVLTree': './dataStructure/aVLTree/start.js',
        'dataStructure/bpTree': './dataStructure/bpTree/start.js',
        'dataStructure/bTree': './dataStructure/bTree/start.js',
        'dataStructure/heap': './dataStructure/heap/start.js',
        'dataStructure/rbTree': './dataStructure/rbTree/start.js',
        'dataStructure/hfmTree': './dataStructure/hfmTree/start.js',
        /**
         * 算法
         */
        'algorithm/dfs': './algorithm/dfs/start.js',
        'algorithm/bfs': './algorithm/bfs/start.js',
        'algorithm/bigInt': './algorithm/bigInt/start.js',
        'algorithm/bigFloat': './algorithm/bigFloat/start.js'
    },
    output: {
        filename: '[name]/js/bundle.js',
        path: resolve(__dirname, '../../../../../../page/dataStructureAlgorithmVisualization')
    },
    optimization: {
        splitChunks: {
            cacheGroups: {
                //打包公共模块
                commons: {
                    chunks: 'initial', //initial表示提取入口文件的公共部分
                    minChunks: 2, //表示提取公共部分最少的文件数
                    minSize: 0, //表示提取公共部分最小的大小
                    name: 'commons' //提取出来的文件命名
                }
            }
        },
        minimizer: [
            // 配置生产环境的压缩方案：js和css
            new TerserWebpackPlugin({
                // 开启缓存
                cache: true,
                // 开启多进程打包
                parallel: true,
                // 启用source-map
                sourceMap: true
            })
        ]
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
        new HtmlWebpackPlugin(
            {
                template: './dataStructure/aVLTree/AVLTree.html',
                chunks: ['commons','dataStructure/aVLTree'],
                filename: 'dataStructure/aVLTree/index.html'
            }
        ),
        new HtmlWebpackPlugin(
            {
                template: './dataStructure/bpTree/BPTree.html',
                chunks: ['commons','dataStructure/bpTree'],
                filename: 'dataStructure/bpTree/index.html'
            }
        ),
        new HtmlWebpackPlugin(
            {
                template: './dataStructure/bTree/BTree.html',
                chunks: ['commons','dataStructure/bTree'],
                filename: 'dataStructure/bTree/index.html'
            }
        ),
        new HtmlWebpackPlugin(
            {
                template: './dataStructure/heap/heap.html',
                chunks: ['commons','dataStructure/heap'],
                filename: 'dataStructure/heap/index.html'
            }
        ),
        new HtmlWebpackPlugin(
            {
                template: './dataStructure/rbTree/rbTree.html',
                chunks: ['commons','dataStructure/rbTree'],
                filename: 'dataStructure/rbTree/index.html'
            }
        ),
        new HtmlWebpackPlugin(
            {
                template: './dataStructure/hfmTree/hfmTree.html',
                chunks: ['commons','dataStructure/hfmTree'],
                filename: 'dataStructure/hfmTree/index.html'
            }
        ),

        /**
         * 算法
         */
        new HtmlWebpackPlugin(
            {
                template: './algorithm/dfs/DFS.html',
                chunks: ['commons','algorithm/dfs'],
                filename: 'algorithm/dfs/index.html'
            }
        ),
        new HtmlWebpackPlugin(
            {
                template: './algorithm/bfs/BFS.html',
                chunks: ['commons','algorithm/bfs'],
                filename: 'algorithm/bfs/index.html'
            }
        ),
        new HtmlWebpackPlugin(
            {
                template: './algorithm/bigInt/bigint.html',
                chunks: ['commons','algorithm/bigInt'],
                filename: 'algorithm/bigInt/index.html'
            }
        ),
        new HtmlWebpackPlugin(
            {
                template: './algorithm/bigFloat/bigFloat.html',
                chunks: ['commons','algorithm/bigFloat'],
                filename: 'algorithm/bigFloat/index.html'
            }
        ),

        new MiniCssExtractPlugin({
            //对输出的文件重命名
            filename: '[name]/css/bundle.css'
        }),
        //压缩css
        new optimizeCssAssetsWebpackPlugin(),
        new JavaScriptObfuscator({
            domainLock:["zhu_weibin.gitee.io"],
            compact: true,//压缩代码
            controlFlowFlattening: false,//是否启用控制流扁平化(降低1.5倍的运行速度)
            controlFlowFlatteningThreshold: 1,//应用概率;在较大的代码库中，建议降低此值，因为大量的控制流转换可能会增加代码的大小并降低代码的速度。
            deadCodeInjection: false,//随机的死代码块(增加了混淆代码的大小)
            deadCodeInjectionThreshold: 1,//死代码块的影响概率
            debugProtection: true,//此选项几乎不可能使用开发者工具的控制台选项卡
            debugProtectionInterval: false,//如果选中，则会在“控制台”选项卡上使用间隔强制调试模式，从而更难使用“开发人员工具”的其他功能。
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
    mode: 'production',
    //mode: 'development',
    //devtool: 'source-map'
}

//webpack serve --mode=development //开启服务器  要先安装 webpack-cli，命令为：npm install -g webpack-cli@4.5.0
//npx webpack //打包