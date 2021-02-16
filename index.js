const express = require("express");
const cors = require("cors");
//npm packages - import libraries
const body_parser = require("body-parser");
const passport =require("passport");
const path = require("path");
let PORT = process.env.PORT||3000;
const cmdPort = process.argv[2] ? process.argv[2].slice(process.argv[2].length - 4, process.argv[2].length) : null;
if (!(cmdPort === undefined || cmdPort === null)) {
  PORT = cmdPort;
}
const session = require("express-session");
const request = require("request");

// create a new express application
const app = express();
const router = require('./routes');

// cors configuration
const validOrigins = [
];

app.use(cors({
    origin: (origin, callback) => {
        if (validOrigins.indexOf(origin) === -1) {
            callback(null, true)
          } else {
            callback(new Error('Not allowed by CORS'))
          }
    },
    credentials: true,
    allowedHeaders: 'Content-Type,Authorization'
  }));

// Origin verification generator
app.use(session({
    secret: 'secrettexthere',
    saveUninitialized: true,      
    resave: true,
    cookie : {
    sameSite: 'none',
    secure: true
  }
}));

//set application configuration settings
app.use(body_parser.json())
app.use(express.static(path.join(__dirname,'/public')));
app.use(passport.initialize());
app.use(passport.session());
app.set('trust proxy', 1);
app.use('/api/v1/users', router);

// listen 
app.listen(PORT, ()=>{
    console.log("Running on port ", PORT);
});