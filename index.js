const express = require("express");
const app = express();


                                                                                         
const mysql = require('mysql');
const port = 8000;
var http = require('http');

                                                                                                                     
//var bodyParser= require ('body-parser');
               //192.168.102.1
const db = mysql.createConnection ({
  host: '192.168.102.1',
  user: 'root',
  password: '',
  database: 'users'
     });
                                                                                                                     
// connect to database
db.connect((err) => {
if (err) {
   throw err;
  }
console.log('Connected to database');
});
global.db = db;                                                                                                      
                                                                                                                     
//app.use(bodyParser.json());                                                                                          
//app.use(bodyParser.urlencoded({ extended: true }));


                                                                                                  
// new code added to your Express web server
require('./routes/main')(app);
app.set('views',__dirname + '/views');
//app.set('view engine', 'ejs');
//app.engine('html', require('ejs').renderFile);
                                                  

app.get("/greet", async (req, res) => {
  res.send('hello bro!  ');
  
  });

//app.listen(3000, () => console.log('Node server listening on port 4242!'));
                                                                                                                     
app.listen(port, () => console.log(`Example app listening on port ${port}!`));