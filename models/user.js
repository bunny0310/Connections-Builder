const {mongoose_connection} = require("../config");

const mongoose = mongoose_connection();

const schema = new mongoose.Schema({
    firstName: {type: String, required: true},
    lastName: {type: String, required: true},
    email: {type: String, required: true, unique: true},
    password: {type: String, required: true},
    createdAt: {type: Date, default: Date.now()},
    updatedAt: {type: Date, default: Date.now()},
})

const User = new mongoose.model('User', schema);

module.exports = {User}