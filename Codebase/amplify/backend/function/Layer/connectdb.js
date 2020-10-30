'use strict';
const mongoDB = require('mongodb').MongoClient;
let connectedDB = null;

module.exports = {
    getDBconnection: function (){
        if(connectedDB){

            console.log('=> using existing connection');
            return Promise.resolve(connectedDB);

        }

        console.log('=> created a new connection')
        return mongoDB.connect(process.env.MONGOADDRESS, { useUnifiedTopology: true }).then((dbConnection) => {

            connectedDB = dbConnection;
            return connectedDB;

        });
    }
}