<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page isELIgnored="false"%>


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
                    <form id="searchForm" action="listComputers" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" value="<c:out value="${ nameSearched }" />" class="form-control" placeholder="Search name" />
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

        <form id="deleteForm" action="listComputers" method="POST">
            <input type="hidden" name="selection" value="">
        

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
	                         <a id="sort"  href="?sort=name">
	                            <span class="glyphicon glyphicon-sort-by-alphabet"></span> 
                            </a>
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
                 <c:forEach items="${ listComputers }" var="computer" varStatus="status">
				    
				      <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="<c:out value="${ computer.id }"/>" >
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
        
        </form>
        
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="/cbd-maven/listComputers?page=1" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <c:forEach var="i" begin="1" end="5" step="1">
              	<c:if test= "${ fn:substringBefore( (pageNumber div 5), '.')*5 +i <= numberOfPages }" >
              		<li><a href="/cbd-maven/listComputers?limitByPages=<c:out value="${ limitByPages }" />&page=<c:out value="${ fn:substringBefore( (pageNumber div 5), '.')*5 +i  }" />"><c:out value="${ fn:substringBefore( (pageNumber div 5), '.')*5 +i }" /></a></li>
			  	</c:if>
			  </c:forEach>
              <li>
                <a href="/cbd-maven/listComputers?limitByPages=<c:out value="${ limitByPages }" />&page=<c:out value="${ numberOfPages }" />" aria-label="Next">
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