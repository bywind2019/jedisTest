<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>iphoneXS MAX!!! 一元秒杀!!!</h1>
	<form id ="msform" action="${pageContext.request.contextPath }/doseckill">
		<input type ="hidden" id = "prodid" name ="prodid" value ="1001"/>
		<input type ="button" id ="miaosha_btn" name ="seckill_btn" value ="一元秒杀"/>
	</form>
</body>
<script type="text/javascript" src ="${pageContext.request.contextPath }/script/jquery/jquery-3.1.0.js"></script>
<script type="text/javascript">
$(function(){
	$("#miaosha_btn").click(function(){
		var url = $("#msform").attr("action");
		$.post(url,$("#msform").serialize(),function(data){
			if(data =="false"){
				alert("抢光了!");
				$("miaosha_btn").attr("disabled",true);
			}
		});	
	})
})

</script>
</html>