<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de Disciplina</title>
<script
	src="js/jquery.js"></script>
<link rel="stylesheet" href="css/style.css" type="text/css" />
<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />
</head>
<body>
	<c:set var="nome" value="Renan" />
	Enter Chat and press enter
	<div>
		<input id=input placeholder="Seu Texto" /><input type="button"
			class="btn error" onclick="$('#box').empty();" value="Limpar" />
		<input type="hidden" name="hr_init" id="hr_init" value="10/30/2012 11:00:50">
		<input type="hidden" name="hr_end" id="hr_end" value="11/30/2012 15:40:50">
	</div>
	<div id=box></div>
	<div id=pubnub pub-key=demo sub-key=demo></div>
	<script src=js/pubnub-3.3.js></script>
	<script>
	    function validaData()
	    {
			var hr_init = new Date($('#hr_init').val());
			var hr_end = new Date($('#hr_end').val());
			var now = new Date();
			
			if(now.getTime() > hr_init.getTime() && now.getTime() < hr_end.getTime())
			{
				return true;
			}
			
			return false;
	    }
	    
		(function() {
			var box = PUBNUB.$('box'), input = PUBNUB.$('input'), channel = 'chat2';
			PUBNUB.subscribe({
				channel : channel,
				callback : function(text) {
					box.innerHTML = ('' + text).replace(/[<>]/g, '')+ '<br>'+ box.innerHTML;
				}
			});
			PUBNUB.bind('keyup', input, function(e) {
				var date = new Date();
				var date_str=('0'+date.getHours()).substr(-2,2)+':'+('0'+date.getMinutes()).substr(-2,2)+':'+('0'+date.getSeconds()).substr(-2,2);
				(e.keyCode || e.charCode) === 13 && validaData() != 'not'
						&& PUBNUB.publish({
							channel : channel,
							message : date_str+' ${nome}: ' + input.value,
							x : (input.value = '')
						});
			});
		})();
	</script>
	<div>
		<c:if test="${empty chatOK or not chatOK}">
			<input type="hidden" name="chatok" id="chatok" value="not">
		</c:if>
	</div>
</body>
</html>