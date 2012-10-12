<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<div class="pagination">
<input type="hidden" name="page" id="page" value="${page}" />
	<ul>
		<li><a href="javascript:$('#page').val(${page - 1});$('input[name=btnFiltro]').click();">Anterior</a></li>
			<c:if test="${qtdpag > 0}">
				<c:set var="init" value="${page - 5}" />
				<c:if test="${init < 1}">
					<c:set var="init" value="${1}" />
				</c:if>
				<c:set var="end" value="${init + 5}" />
				<c:if test="${end > qtdpag}">
					<c:set var="end" value="${qtdpag}" />
				</c:if>
				<c:if test="${qtdpag > 5}"></c:if>
				<c:forEach begin="${init}" end="${end}" var="it">
					<li <c:if test="${it eq page}"> class="active" </c:if>><a
						href="javascript:$('#page').val(${it});$('input[name=btnFiltro]').click();">${it}</a></li>
				</c:forEach>
			</c:if>
			<li><a href="javascript:$('#page').val(${page + 1});$('input[name=btnFiltro]').click();">Próximo</a></li>
	</ul>
</div>
