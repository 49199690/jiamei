<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">佳美管理系统</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
              <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">问卷<span class="caret"></span></a>
                  <ul class="dropdown-menu">
	                 <li><a href="${ctx}/mvc/questionnaire/add">添加</a></li>
	                 <li><a href="${ctx}/mvc/questionnaire/manage">管理</a></li>
                     <li><a href="${ctx}/mvc/answer/manage">答卷</a></li>
                  </ul>
              </li>
              <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">合作<span class="caret"></span></a>
                  <ul class="dropdown-menu">
                     <li><a href="${ctx}/mvc/cooperation/manage">管理</a></li>
                     <li><a href="${ctx}/mvc/cooperation/create">创建</a></li>
                  </ul>
              </li>
              <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">老师<span class="caret"></span></a>
                  <ul class="dropdown-menu">
                     <li><a href="${ctx}/mvc/employee/manage">管理</a></li>
                  </ul>
              </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle bg clear" data-toggle="dropdown">
                        ${au.name}
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight">
                        <li>
                            <a href="#">修改密码</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="${ctx}/mvc/login/logout">退出</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>