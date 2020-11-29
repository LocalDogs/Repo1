'use strict';
require('dotenv');
const connect = require('connectdb');
const queries = require('queries');

exports.handler = (event, context, callback) => {
  context.callbackWaitsForEmptyEventLoop = false;
  connect.getDBconnection().then((dbConnection) => {

    console.log('Event ==> ' +  JSON.stringify(event));

    queries.uploadUser(dbConnection, JSON.parse(event.body)).then((result) => {
    
        console.log('Mongodb response ==> ' + JSON.stringify(result));
        var response = {};
        var body = {};
        response.statusCode = 200;
        response.headers = {
            
            'Content-Type': 'application/json'
            
        };
        body.message = 'Insert was successful';
        body.inserted = true;
        body.duplicate = false;
        body.error = null;
        body.payloadid = event.email;
        body.user = result.ops[0];
        body.user.matchList = [];
        response.body = JSON.stringify(body);
        // send query results back to app
        callback (null, response);
      
    }).catch(err => {
      console.log("Querying Database Error: " + err.message);
      var body = {};
      // this should never actually make it back to the app, because 
      // the duplicate email will be caught already by the AWS Cognito
      // registration process
      if(err.message.includes("E11000")){
        body.error = "Duplicate Email";
        body.duplicate = true;
      }
      else{
        body.error = "Unknown";
        body.duplicate = false;
      }
      body.user = null;
      body.payloadid = event.email; // might be null?
      body.inserted = false;
      callback(null, {

        // error trying to query database after successfully establishing
        // a connection
        statusCode: err.statusCode || 500,
        headers: {
        
          'Content-Type': 'application/json'
        
        },
      
        body: JSON.stringify(body)

      });
      
    });
  }).catch(err => {
    // error trying to connect to database
    // execute callback here at some point to
    // notify app of connection error
    var body = {};
    body.user= null;
    body.error = err.message;
    body.payloadid = event.email; // might be null?
    body.inserted = false;
    console.log("Database Connection Error" + err);
      callback(null, {

        // error trying to query database after successfully establishing
        // a connection
        statusCode: err.statusCode || 500,
        headers: {
          
          'Content-Type': 'application/json'
          
        },
        
        body: JSON.stringify(body)

      });
  });
  
}