'use strict';
require('dotenv');
const connect = require('connectdb');
const queries = require('queries');

exports.handler = (event, context, callback) => {
  
  context.callbackWaitsForEmptyEventLoop = false;
  console.log('=> event: ' + JSON.stringify(event));
  connect.getDBconnection().then((dbConnection) => {
    
    queries.retrieveUser(dbConnection, event.queryStringParameters).toArray((err, user) =>{
      console.log("==> user: " + JSON.stringify(user));
      var response = {};
      var body = {};
      
      if(user == null || err){

        body.found = false;
        body.message = 'Email not found';
        body.error = err;
        body.user = user;

      }
      else{
        
        body.found = true;
        body.message = 'Email found';
        body.error = null;
        body.user = user[0];
        var scrubbedMatchList = [];
        console.log("===> user result: " + JSON.stringify(body.user));
        if(body.user.matchList == null) body.user.matchList = scrubbedMatchList; 
        else{
          body.user.matchList.forEach(element => {
            var match = {
              matchedEmail: element.email,
              matchedFirstName: element.firstname,
              matchedDogName: element.dogs[0].name,
              matchedContactInfo: element.contactInfo
            }
            scrubbedMatchList.push(match)
          });
          body.user.matchList = scrubbedMatchList;
        }
      }
      body.query = event.queryStringParameters;
      response.statusCode = 200;
      response.body = JSON.stringify(body);
      response.isBase64Encoded = false;
      console.log('Response ==> ' + JSON.stringify(response));
      callback (null, response);

    })}).catch(err => {
    
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