const express = require('express');
const router = express.Router();
const {getUsers, insertUser, updateUser, getUser, deleteUser, verifyUser} = require('./controller');

router.get('/', (req, res) => {
    getUsers().then((data)=>{
        return res.status(200).json({"data": data, "msg": "success"});
    })
    .catch((err) => {
        return res.status(400).json({"data": null, "msg": "error: " + err});
    })
})

router.get('/:id', (req, res) => {
    getUser(req.params['id'])
    .then((doc) => {
        if(doc === null) {
            return res.status(200).json({"data": null, "msg": "User not found"});
        }
        return res.status(200).json({"data": doc, "msg": "success!"});
    })
    .catch((err) => {
        return res.status(500).json({"data": null, "msg": "error: " + err});
    });
});

router.post('/searchByEmail', (req, res) => {
    if (Object.keys(req.body).length === 0 || Object.keys(req.body).indexOf('email') === -1) {
        return res.status(400).json({"msg": "incorrect format", "data": null});
    }
    getUser(req.body['email'], true)
    .then((doc) => {
        if(doc === null) {
            return res.status(200).json({"data": null, "msg": "User not found"});
        }
        return res.status(200).json({"data": doc, "msg": "success!"});
    })
    .catch((err) => {
        return res.status(500).json({"data": null, "msg": "error: " + err});
    });
});

router.post('', (req, res) => {
    insertUser(req.body)
    .then((data)=>{
        const status = data.status;
        return res.status(status).json(data);
    })
    .catch((err) => {
        return res.status(400).json({"data": null, "msg": "error: " + err});
    })
});

router.put('/:id', (req, res) => {
    updateUser(req.params['id'], req.body)
    .then((data)=>{
        const status = data.status;
        return res.status(200).json(data);
    })
    .catch((err) => {
        return res.status(400).json({"data": null, "msg": "error: " + err});
    })
});

router.delete('/:id', (req, res) => {
    deleteUser(req.params['id'])
    .then((data) => {
        if(data.n === 0) {
            return res.status(400).json({"msg": "user not found!", data: null});
        }
        return res.status(200).json({"msg": "user successfully deleted!", data: null});
    })
    .catch((err) => {
        return res.status(500).json({"data": null, "msg": "error: " + err});
    })
});

router.post('/authorize', (req, res) => {
    const body = req.body;
    if(body.email === undefined || body.email === null || body.password === null || body.password === undefined) {
        return res.status(400).json({"msg": "invalid format"});
    }
    const userDetails = {
        email: body.email,
        password: body.password
    };

    verifyUser(userDetails)
    .then((data)=>{
        if(data === null) {
            return res.status(401).json({"msg": "unauthorized"});
        }
        return res.status(201).json({"msg": "authorized!"});
    })
    .catch((err) => {
        return res.status(500).json({"msg": "error " + err});
    })

})

module.exports = router;