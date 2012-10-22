<%@ include file="../init.jspf"%>


<div class="moli-subpages-graphic-links" style="">

    <c:forEach items="${subpages}" var="subpage">
    
        <a href="${subpage.link}"><span class="envelope">
            <span class="content-box">
                <c:choose>
                    <c:when test="${subpage.iconURL!=null}">
                        <img src="${subpage.iconURL}" alt="${subpage.name}" />
                    </c:when>
                    <c:otherwise>
                        <span class="no-image">
                            Please, set the page icon.
                        </span>
                    </c:otherwise>
                </c:choose>
            </span><h3>${subpage.name}</h3></span></a>
    
    </c:forEach>
    
</div>
