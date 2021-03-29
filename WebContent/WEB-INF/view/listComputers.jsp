<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="listComputers"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${ numberOfComputers }" /> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer"  href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                 <c:forEach items="${ listComputers.computerDTOs }" var="computer" varStatus="status">
				    
				      <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="/cbd-maven/editComputer?computer=<c:out value="${ computer.name }"/>" onclick=""><c:out value="${ computer.name }" /></a>
                        </td>
                         <td><c:out value="${ computer.introduced }" /></td>
                        <td> <c:out value="${ computer.discontinued }" /> </td>
                        <td> <c:out value="${ computer.getCompany() }" /> </td> 
                    </tr>
				    
				</c:forEach> 

                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="/cbd-maven/listComputers?page=1" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <c:forEach var="i" begin="0" end="4" step="1">
              	<li><a href="/cbd-maven/listComputers?page=<c:out value="${ pageNumber+i }" />"><c:out value="${ pageNumber+i }" /></a></li>
			  </c:forEach>
<%--               <li><a href="/cbd-maven/listComputers?page=<c:out value="${ pageNumber }" />"><c:out value="${ pageNumber }" /></a></li>
              <li><a href="/cbd-maven/listComputers?page=2"><c:out value="${ pageNumber+1 }" /></a></li>
              <li><a href="/cbd-maven/listComputers?page=3"><c:out value="${ pageNumber+2 }" /></a></li>
              <li><a href="#"><c:out value="${ pageNumber+3 }" /></a></li>
              <li><a href="#"><c:out value="${ pageNumber+4 }" /></a></li> --%>
              <li>
                <a href="/cbd-maven/listComputers?page=" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>

        <div class="btn-group btn-group-sm pull-right" role="group">
         <form id="limitByPages" action="" method="GET">
	            <button type="submit" class="btn btn-default" name="limitByPages" value="10">10</button>
	            <button type="submit" class="btn btn-default" name="limitByPages" value="50">50</button>
	            <button type="submit" class="btn btn-default" name="limitByPages" value="100">100</button>
            </form>
        </div>

    </footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>