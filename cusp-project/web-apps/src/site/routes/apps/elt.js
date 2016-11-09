/**
 * Created by ruanweibiao on 2016-02-18.
 */
var express = require('express'), fs = require('fs'), path = require("path");
var router = express.Router();



router.get('/flow', function(req, res) {
    // --- use bae url --
    res.end( fs.readFileSync(path.join(__dirname, '/../../apps/etl/flow.html') ));
});




module.exports = router;