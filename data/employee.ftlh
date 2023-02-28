<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Employee</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</head>
<body>
<table class="table table-striped">
  <thead>
  <tr>
    <th scope="col">Name</th>
    <th scope="col">Surname</th>
    <th scope="col">Email</th>
    <th scope="col">Reading</th>
    <th scope="col">Read</th>
  </tr>
  </thead>
  <tfoot>
  <tr>
       <th scope="col">Name</th>
       <th scope="col">Surname</th>
       <th scope="col">Email</th>
       <th scope="col">Reading</th>
       <th scope="col">Read</th>
  </tr>
  </tfoot>
  <tbody>
  <#list employees as employee>
  <tr>
    <td>${employee.name}</td>
    <td>${employee.surname}</td>
    <td>${employee.email}</td>

    <td>
     <#list employee.reading as reading>
         ${reading.name}
     </#list>
    </td>

   <td>
    <#list employee.read as read>
        ${read.name}
    </#list></td>

  </tr>
  </#list>
  </tbody>
</table>
</body>
</html>