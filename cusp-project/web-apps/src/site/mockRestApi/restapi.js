/**
 * Created by ruanweibiao on 2016-02-18.
 */
var express = require('express'), fs = require('fs'), path = require("path");
var router = express.Router();

var charset = 'utf-8';

router.get('/system/nav-menus', function(req, res) {
    // --- use bae url --
    res.writeHead(200, {
        "Content-Type":"text/json;charset:"+charset
    });
    res.end( fs.readFileSync(path.join(__dirname, './system/nav-menus') ));
});



// --- user mapping ---
router.put('/system/user/:id', function(req, res) {
    // --- use bae url --
    res.writeHead(200, {
        "Content-Type":"text/json;charset:"+charset
    });
    res.end( fs.readFileSync(path.join(__dirname, './system/user/create') ));
});


// --- user mapping ---
router.get('/system/users', function(req, res) {
    // --- use bae url --
    res.writeHead(200, {
        "Content-Type":"text/json;charset:"+charset
    });
    res.end( fs.readFileSync(path.join(__dirname, './system/users') ));
});


module.exports = router;