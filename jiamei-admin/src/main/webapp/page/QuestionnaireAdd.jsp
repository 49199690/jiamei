<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="layout/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
<%@include file="layout/header.jsp" %>
</head>
<body>
<%@include file="layout/navigation.jsp" %>	
<section id="content">
    <section class="vbox">
        <section class="padder">
            <header class="panel-heading">
                <ol class="breadcrumb">
                    <li><a href="#">问卷</a></li>
                    <li class="active">添加</li>
                </ol>
            </header>
            <div class="row">
                <div class="col-sm-2">
                </div>
                <div class="col-sm-8">
                    <form class="form-horizontal" action="${ctx}/mvc/questionnaire/add/save" method="post">
                        <section class="panel panel-default">
                            <header class="panel-heading">
                                <strong>问卷信息：</strong>
                            </header>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="text-danger">*</span>名称：</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" required name="name" maxlength="20"  onblur="trimText(this)"/>
                                    </div>
                                </div>
                                <div class="line line-dashed b-b line-lg pull-in"></div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="text-danger">*</span>描述:</label>
                                    <div class="col-sm-9">
                                        <textarea name="desc" required class="form-control" rows="10" cols="50"></textarea>
                                    </div>
                                </div>
                            </div>
                            <footer class="panel-footer text-right bg-light lter">
                                <button type="submit" class="btn btn-success btn-s-xs">确定</button>
                            </footer>
                        </section>
                    </form>
                </div>
                <div class="col-sm-2">
                </div>
            </div>
        </section>
    </section>
</section>
<%@include file="layout/footer.jsp" %>
</body>
</html>