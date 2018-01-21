<div class="bs-example">
	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid navbar-border">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#navbarCollapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="FrontControllerServlet?paramActionCommand=Dashboard">
					<img src="resources/images/logo.png" style="width : 150px" title="BooksStore" alt="BooksStore">
				</a>
			</div>
			
			<!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="navbarCollapse">
				<ul class="nav navbar-nav">
					<li class="active"><a
						href="FrontControllerServlet?paramActionCommand=Dashboard">Home</a></li>
					<li><a href="#">Books</a></li>
					<li class="dropdown"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#">Category <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#">Java</a></li>
							<li><a href="#">JavaME</a></li>
							<li><a href="#">JavaEE</a></li>
							<li><a href="#">JDBC</a></li>
						</ul></li>
					<li><a href="#">About</a></li>
				</ul>
		    
               <ul class="nav navbar-nav navbar-right">
                  <li class="dropdown"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#">Account <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#">Register</a></li>
							<li><a href="#">Sign in</a></li>
						</ul></li>
                  <li>
                  	<a href="#"> <span class="glyphicon glyphicon-shopping-cart fa-lg"></span><span>5</span></a>
				  </li>
               </ul>
		    </div>
				
		</div>
	</nav>
</div>