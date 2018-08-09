<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
   <head>
      <title>
         <c:out value="${message}"></c:out>
      </title>
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
         <h1>Bullhorn API</h1>
      </div>
      <div class="container">
         <div class="row">
            <h5>Current Credentials</h5>
            <ul class="list-group">
               <li class="list-group-item">access_token: ${bhSession.accessToken}</li>
               <li class="list-group-item">refresh_token: ${bhSession.refreshToken}</li>
               <li class="list-group-item">BhRestToken: ${bhSession.bhRestToken}</li>
               <li class="list-group-item">restUrl: ${bhSession.restUrl}</li>
               <li class="list-group-item">Time created: ${bhSession.createdTime}</li>
            </ul>
         </div>
         <div class="row">
            <form class="form-horizontal" method="post" modelAttribute="formData" action="/config">
               <div class="form-group">
                  <div class="input-group">
                     <label for="name" class="control-label">Username</label>
                     <input type="text" class="form-control" name="username" id="username" value="${bhSession.username}"/>
                  </div>
               </div>
               <div class="form-group">
                  <div class="input-group">
                     <label for="name" class="control-label">Password</label>
                     <input type="text" class="form-control" name="password" id="password" value="${bhSession.password}"/>
                  </div>
               </div>
               <div class="form-group">
                  <div class="input-group">
                     <label for="name" class="control-label">CLIENT ID</label>
                     <input type="text" class="form-control" name="clientId" id="clientId" value="${bhSession.clientId}"/>
                  </div>
               </div>
               <div class="form-group">
                  <div class="input-group">
                     <label for="name" class="control-label">CLIENT SECRET</label>
                     <input type="text" class="form-control" name="clientSecret" id="clientSecret" value="${bhSession.clientSecret}"/>
                  </div>
               </div>
               <div class="form-group">
                  <div class="input-group">
                     <input type="submit" class="btn btn-primary" type="submit"/>
                  </div>
               </div>
            </form>
         </div>
      </div>
   </body>
</html>