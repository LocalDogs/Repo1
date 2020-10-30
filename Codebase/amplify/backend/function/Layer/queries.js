const USERS = process.env.USER_COLL;
const DB = process.env.DB_NAME;

module.exports = {
    retrieveUser(dbConnection, query){
        var options = {
            _id: 1,
            firstname:1,
            lastname: 1,
            dateofbirth: 1,
            email:1
        }
        return dbConnection.db(DB).collection(USERS).findOne(query, options);
    },
    // may need to handle an array of dogs later
    uploadDog(dbConnection, dog){
        console.log("DB USER INSERT ==> " + DB + '.' + USERS);

        var query = {
            "email": dog.owner,
            "dogs": {
                $not: {
                    $size: 10
                }
            }
        };
        var ddata = {
            $push: {
                "dogs": dog
            }
        }
        return dbConnection.db(DB).collection(USERS).updateOne(query, ddata, options);
    },
    uploadUser(dbConnection, user){

        console.log("DB USER INSERT ==> " + DB + '.' + USERS);

        var query = {
            "email": user.email
        };
        var udata = {
            $set: user
        }
        var options = {
            "upsert": true
        }
        return dbConnection.db(DB).collection(USERS).updateOne(query, udata, options);
    }
}

