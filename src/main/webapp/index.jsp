<%
//가상경로 /board/ 실제는 존재하지 않지만 있는것처럼 경로를 지정(보안때문에)
  response.sendRedirect(request.getContextPath()+"/board/list.do");
%>
