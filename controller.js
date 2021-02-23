const {User} = require('./models/user');
const {verifyFormat} = require('./helper');

const getUsers = async () => {
    const users = await User.find({});

    return users;
}

const getUser = async (key,  isEmail = false) => {
    if (isEmail) {
        const user = await User.findOne({'email': key});
        return user;
    }

    const user = await User.findOne({'_id': key});
    return user;
}

const insertUser = async (body) => {
    if (!verifyFormat(body)) {
        return {
            "msg": "error, incorrect format",
            "status": 400,
            "data": null
        }
    }
    const user = new User(body);
    let result = {};
    await user.save()
    .then((res) => {
        result = {
            "msg": "user successfully added!",
            "status": 200,
            "data": user            
        }
    })
    .catch((err) => {
        result = {
            "msg": "couldn't add user! Error: " + err,
            "status": 500,
            "data": null            
        }   
    });
    return result;
}

const updateUser = async (id, body) => {
    if (!verifyFormat(body)) {
        return {
            "msg": "error, incorrect format",
            "status": 400,
            "data": null
        }
    }

    let user = await User.findOne({'_id': id});
    if(user === null || user === undefined) {
        return {
            "msg": "error, user not found",
            "status": 400,
            "data": null
        }
    }
    for(const key of Object.keys(body)) {
        user[key] = body[key];
    }
    await user.save()
    .then((res) => {
        result = {
            "msg": "user successfully updated!",
            "status": 200,
            "data": user            
        }
    })
    .catch((err) => {
        result = {
            "msg": "couldn't update user! Error: " + err,
            "status": 500,
            "data": null            
        }   
    });
    return result;
}

const deleteUser = async (id) => {
    const deletedUser = await User.deleteOne({'_id': id});
    return deletedUser;
}

const verifyUser = async (user) => {
    const userObj = await User.findOne({'email': user.email, 'password': user.password});
    return userObj;
}

module.exports = {getUsers, insertUser, updateUser, getUser, deleteUser, verifyUser};