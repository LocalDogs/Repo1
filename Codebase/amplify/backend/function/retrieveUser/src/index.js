'use strict';
require('dotenv');
const connect = require('connectdb');
const queries = require('queries');

exports.handler = (event, context, callback) => {
  
  context.callbackWaitsForEmptyEventLoop = false;
  console.log('=> event: ' + JSON.stringify(event));
  connect.getDBconnection().then((dbConnection) => {
    
    queries.retrieveUser(dbConnection, event.queryStringParameters).then((user) => {
      var response = {};
      var body = {};
      
      if(user == null){

        body.found = false;
        body.message = 'Email not found';
        body.error = 'Email not found';

      }
      else{
        
        body.found = true;
        body.message = 'Email found';
        body.error = null;
    
      }
      body.user = user;
      body.query = event.queryStringParameters;
      response.statusCode = 200;
      response.body = JSON.stringify(body);
      response.isBase64Encoded = false;
      console.log('Response ==> ' + JSON.stringify(response));
      callback (null, response);
        
    }).catch((err) => {

      var body = {};

      body.error = 'Could not query database';
      body.message = 'Probably a malformed query';
      body.user = null;
      body.found = false;
      body.query = event.queryStringParameters;
      callback(null, {

        // error trying to query database after successfully establishing
        // a connection
        statusCode: err.statusCode || 500,
        headers: {
        
          'Content-Type': 'application/json'
        
        },
      
        body: JSON.stringify(body)

        })
  });
  }).catch(err => {
    
    // error trying to connect to database
    // execute callback here at some point to
    // notify app of connection error
    var body = {};
      body.user = null;
      body.error = err.message;
      body.message = 'Could not connect to database';
      body.found = false;
      body.query = event.queryStringParameters;

    callback(null, {

      // error trying to query database after successfully establishing
      // a connection
      statusCode: err.statusCode || 500,
      headers: {
      
        'Content-Type': 'application/json'
      
      },
    
      body: JSON.stringify(body)

      })
    console.log(err);

  });
}