/**
 * Created by ruanweibiao on 2016-02-18.
 */
var React = require('react');

var HelloMessage = React.createClass({
    render: function() {
        return <div>Hello boy {this.props.name}</div>;
    }
});

module.exports = HelloMessage;