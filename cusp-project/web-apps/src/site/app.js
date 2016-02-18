/**
 * Created by vison on 2016/2/17.
 */
var express = require('express'),
    path = require("path"),
    i18n = require('i18n');

i18n.configure({
    locales:['en-US', 'zh-CN'],  // setup some locales - other locales default to en_US silently
    defaultLocale: 'zh-CN',
    directory: __dirname + '/locales',  // locales 翻译文件目录，我的是 locales， 可以写成其他的。
    updateFiles: false,
    indent: "\t",
    extension: '.js'  // 由于 JSON 不允许注释，所以用 js 会方便一点，也可以写成其他的，不过文件格式是 JSON
});



// --- defined express
var app = express();
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jsx');

var options = {presets: ['react', 'es2015']}
app.engine('jsx', require('express-react-views').createEngine(options));

/**
 * define url mapping  ---
 */
var jsPath = express.static(path.join(__dirname, '/js'));
var resourcesPath = express.static(path.join(__dirname, '/resources'));
var appsPath = express.static(path.join(__dirname, '/apps'));
app.use('/js',jsPath);
app.use('/resources',resourcesPath);
app.use('/apps' , appsPath)


// --- define routes
app.get('/', require('./routes').index);



/**
 * start server
 * @type {*|{remove}}
 */
var server = app.listen(app.get('port'), function () {
    var host = server.address().address;
    var port = server.address().port;
});
