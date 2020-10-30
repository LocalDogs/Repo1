'use strict';
require('dotenv');
const connect = require('connectdb');
const queries = require('queries');

exports.handler = (event, context, callback) => {
  context.callbackWaitsForEmptyEventLoop = false;
  connect.getDBconnection().then((dbConnection) => {

    console.log('Event ==> ' +  JSON.stringify(event));

    queries.updateUser(dbConnection, JSON.parse(event.body)).then((result) => {
    
        console.log('Mongodb response ==> ' + JSON.stringify(result));
        var response = {};
        var body = {};
        response.statusCode = 200;
        response.headers = {
            
            'Content-Type': 'application/json'
            
        };
        body.message = 'Update was successful';
        body.updated = true;
        body.payloadid = event.email;
        response.body = JSON.stringify(body);
        // send query results back to app
        callback (null, response);
      
    }).catch(err => {
      console.log("Querying Database Erro: " + err.message);
      var body = {};
      // atm, don't know what might be a common error here
      // and therefore don't know what to parse for
      body.error = err.message;
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
    console.log("Database Connection Error" + err);
      callback(null, {

        // error trying to query database after successfully establishing
        // a connection
        statusCode: err.statusCode || 500,
        headers: {
          
          'Content-Type': 'application/json'
          
        },
        
        body: '{"message": "Could not connect to database"}'
      });
  });
  
}