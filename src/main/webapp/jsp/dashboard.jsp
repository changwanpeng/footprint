<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
  <title><c:out value="${message}"></c:out></title>
  
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
  <!-- Bootstrap CSS -->
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <!-- jQuery library -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <!-- Latest compiled JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      
  <!-- Customized JavaScript and CSS -->
  <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<div class="jumbotron text-center">
<h1>Client Login</h1>
<p>Welcome back!</p> 
</div>
<div class="container">
<div class="row">
   <div class="col-xs-12 col-sm-12 col-md-3" id="side-menu">
   <div class="row"></div>
   <div class="row">
      <div class="list-group">
         <a class="list-group-item active" href="dashboard/"><span class="glyphicon glyphicon-dashboard"></span> Dashboard</a>
         <a class="list-group-item" href="#/jobs"><span class="glyphicon glyphicon-briefcase"></span> Jobs</a>
         <a class="list-group-item"><span class="glyphicon glyphicon-off"></span>Logout</a>
      </div>
    </div>
   </div>
   <!-- end of "side-menu" -->
   <div class="col-md-9" id="main-menu">
      <h1>Dashboard</h1>
      <div class="row">
         <div class="col-sm-6">
            <div class="alert-dashboard" tabindex="0">
               <span class="text-success glyphicon glyphicon-briefcase"></span> ${client.openedJobOrderSize}  open jobs
               <div class="pull-right">
                  <span class="glyphicon glyphicon-circle-arrow-right"></span>
               </div>
            </div>
         </div>
         <div class="col-sm-6">
            <div class="alert-dashboard" tabindex="0">
               <span class="text-danger glyphicon glyphicon-briefcase"></span> ${client.closedJobOrderSize} closed jobs
               <div class="pull-right">
                  <span class="glyphicon glyphicon-circle-arrow-right"></span>
               </div>
            </div>
         </div>
      </div>
      <!-- end of "row" -->
      <div class="row">
         <div class="col-sm-6">
            <h4>Open jobs by status</h4>
            <table class="table table-striped">
               <tbody>
                  <tr>
                     <th>Status</th>
                     <th>Number</th>
                  </tr>
                  <c:forEach var="entry" items="${client.jobOrdersByStatus}">
	              <tr>
	                 <td>${entry.key}</td>
	                 <td>${entry.value}</td>
	              </tr>
                  </c:forEach>
               </tbody>
            </table>
         </div>
         <div class="col-sm-6">
            <h4>Open jobs by type</h4>
            <table class="table table-striped">
               <tbody>
                  <tr>
                     <th>Type</th>
                     <th>Number</th>
                  </tr>
                  <c:forEach var="entry" items="${client.jobOrdersByType}">
	              <tr>
	                 <td>${entry.key}</td>
	                 <td>${entry.value}</td>
	              </tr>
              </c:forEach>
               </tbody>
            </table>
         </div>
      </div>
      <!-- end of "row" -->
   </div>
   <!-- end of "main-menu" -->
</div>
<!-- end of "row" -->
</div>
<!-- end of "container" -->

</body>

</html>