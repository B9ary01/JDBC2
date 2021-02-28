                                                                                                                 
module.exports = function(app)
{
   app.get('/search',function(req,res){
     res.render('search.html')
     });
                                                                                                                     
  app.get('/register',function(req,res){ res.render('register.html')
 });
                                                                                                                     
//register
app.post('/registered', function (req, res) {
                                                                                                                     
 var password=req.body.password;
var name=req.body.name;
var email=req.body.email;
let sqlquery="SELECT * FROM members WHERE name=?";
                                                                                                                     
db.query(sqlquery,[name],(err, result) => {
   if(err){
     console.log(err); }
if(result && result.length){
    res.send('<a href='+'https://www.doc.gold.ac.uk/usr/602/register'+'>Register page</a>'
+'<br />'+ '<br />'+ 'user already exists in the database.');}
else{
   //insert into table
   db.query("INSERT INTO members (name, password, email) VALUES (?,?,?)",[req.body.name,req.body.password,req.body.email]);
res.send('<a href='+'https://www.doc.gold.ac.uk/usr/602/users'+' >Memberlist page</a>'+
 '<br />' +'<br />'+'  Successful ');}; });
                                                                                                                     
});

                                                                                                                   
//search registered members
app.post('/search-result', function (req, res) {
    //searching in the database
                                                                                                                     
   let sqlquery="SELECT * FROM members WHERE name=?"
                                                                                                                     
db.query(sqlquery,[req.body.name],(err, result) => {
if (err) {
         console.log(err);                                                                                           
           }
if(result && result.length){
res.send('<a href='+'https://www.doc.gold.ac.uk/usr/602/search'+'>Search page</a>'
+ '<br />' +'<br />'+' User found - '+req.body.nam);};
if(result==false){res.send('not found');}
});
                                                                                                                     
  });
                                                                                                                     
//get memberlist 
app.get('/users', function(req, res) {
    let sqlquery = "SELECT * FROM members"; // query database to get all the books
  // execute sql query
  db.query(sqlquery, (err, result) => {
    if (err) {
           res.redirect('./');
           }
     res.render('list.ejs', {availablemembers: result});
         });
      });
                                                                                                                     
                                                                                                                     
};
                           