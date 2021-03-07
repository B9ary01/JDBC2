const express = require("express");
const app = express();
var uuid=require('uuid');
var bodyParser = require('body-parser')

const mysql = require('mysql');
const port = 3000;
var http = require('http');

                                                                                                                     
//var bodyParser= require ('body-parser');
const con = mysql.createConnection ({
  host: '127.0.0.1',
  user: 'root',
  password: 'YourRootPassword',
  database: 'mydb'
     });
                                                                                                                     
// connect to database
con.connect((err) => {
if (err) {
   throw err;
  }
console.log('Connected to database');
});
global.con = con;
                                                                                                                     
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

                                                                                                  
// new code added to your Express web server
//require('./routes/main')(app);
app.set('views',__dirname + '/views');
//app.set('view engine', 'ejs');
//app.engine('html', require('ejs').renderFile);
                                                  

app.get("/grade", async (req, res) => {
  res.send('skills opens ! ');
  
  });



app.get("/", async (req, res) => {
  res.send('hey there  ! ');

  });


app.get('/student',(req,res)=>{
	let sql="select * from student";
	let query=con.query(sql,(err,result)=>
	{
		if(err)throw err;
		res.send(JSON.stringify({"status":200, "error":null, "response":result}));
	});
});

app.get('/test9',(req,res)=>{
	let sql="select * from test9";
	let query=con.query(sql,(err,result)=>
	{
		if(err)throw err;
		res.send(JSON.stringify({"status":200, "error":null, "response":result}));
	});
});



app.get('/naka',(req,res)=>{
	let sql="select * from naka";
	let query=con.query(sql,(err,result)=>
	{
		if(err)throw err;
		res.send(JSON.stringify({"status":200, "error":null, "response":result}));
	});
});

app.get('/testJs',(req,res)=>{
	let sql="select * from testJs";
	let query=con.query(sql,(err,result)=>
	{
		if(err)throw err;
		res.send(JSON.stringify({"status":200, "error":null, "response":result}));
	});
});

app.post('/register/',(req,res,next)=>{

var uid=uuid.v4();
var password=req.body.password;
var name=req.body.name;
var email=req.body.email;

con.query('SELECT * FROM test9 where email=?',[email],function(err,result,fields){
con.on('error',function(err){
console.log('[mysql err]',err);
});

});
if(result && result.length)
res.json('user already exists...');
else{

con.query('INSERT INTO test9 SET ?', req.body,function(err,result)
	{
		if(err) throw err;
		res.json({'status':'success', id:result.insertId });
	});

 res.json('Registration success');
 };

});
//INSERT INTO `naka`(`id`, `fullname`, `username`, `password`, `email`)
//VALUES('klpo Ma', 'pol90h','lsps99999', 'ja89@gmail.com');

//app.listen(3000, () => console.log('Node server listening on port 4242!'));


app.post('/login',(req,res)=>{
	var email=req.body.email;
	var name=req.body.name;
	var password=req.body.password;

	let sql="SELECT * FROM test9 where name=?";

	let query=con.query(sql,[name],(err,result)=>
	{
		if(err)throw err;
		res.send(JSON.stringify({"status":200, "error":null, "response":result}));
	});
});


app.post('/addstudent', function(req,res,next){
	var name=req.body.name;
	var course=req.body.course;

	//var sql='insert into student(name,course) values("${name}","${course}")';
	con.query('INSERT INTO test9 SET ?', req.body,function(err,result)
	{
		if(err) throw err;
		res.json({'status':'success', id:result.insertId });
	});
});

                                                                                                                     
app.listen(port, () => console.log(`Example app listening on port ${port}!`));