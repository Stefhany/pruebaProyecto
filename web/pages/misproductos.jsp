<%-- 
    Document   : misproductos
    Created on : 19-oct-2015, 18:54:10
    Author     : Mona
--%>

<%@page import="dtos.RolesUsuariosDTO"%>
<%@page import="java.util.LinkedList"%>
<%@page import="facade.FacadeRolesUsuarios"%>
<%@page import="facade.FacadeConsultas"%>
<%@page import="dtos.ProductosAsociadosUsuariosDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="facade.FacadeProductosAsociadosUsuarios"%>
<%@page import="dtos.UsuariosDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Mis Productos - SIGAA</title>

        <!-- Bootstrap Core CSS -->
        <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Estilos para mensajes -->
        <link href="../css/estilos.css" rel="stylesheet">

        <!-- MetisMenu CSS -->
        <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

        <!-- Timeline CSS -->
        <link href="../dist/css/timeline.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

        <!-- Morris Charts CSS -->
        <link href="../bower_components/morrisjs/morris.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- Favicon de SIGAA -->
        <link rel="icon" href="../img/portfolio/favicon.ico" type="image/x-ico"/>

        <script>
            function confirmar() {
                if (confirm('¿Esta seguro de deshabiliar este producto?')) {
                    return true;
                } else {
                    return false;
                }
            }
        </script>

        <%
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
        %>

        <%
            HttpSession miSesion = request.getSession(false);
            if (miSesion.getAttribute("usr") != null) {
                UsuariosDTO userdto = (UsuariosDTO) miSesion.getAttribute("usr");
                String menu = (String) miSesion.getAttribute("mp");
                //int idUsuario = userdto.getIdUsuarios();
                //int id = Integer.parseInt(request.getParameter("idProductor"));
                FacadeProductosAsociadosUsuarios facadeProAso = new FacadeProductosAsociadosUsuarios();

                ArrayList<ProductosAsociadosUsuariosDTO> productos = new ArrayList();
                //productos = padao.listarProductosByUser(3);
                productos = facadeProAso.listarProductosByUser(userdto.getIdUsuarios());

                if (productos != null) {
        %>
    </head>

    <body>

        <div id="wrapper">

            <!-- Navegación -->
            <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand page-scroll" href="profile.jsp" style="color:#16700C;"><i>SIGAA</i></a>
                </div>
                <!-- /.navbar-header -->

                <ul class="nav navbar-top-links navbar-right">
                    <!-- /.dropdown -->
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#" style="color:#16700C;">
                            <p>
                                <%
                                    int idUsuario = userdto.getIdUsuarios();
                                    FacadeRolesUsuarios facadeRoles = new FacadeRolesUsuarios();
                                    LinkedList<RolesUsuariosDTO> u = new LinkedList();
                                    u = facadeRoles.mostrarRol(idUsuario);
                                    for (RolesUsuariosDTO roles : u) {
                                        out.print(roles.getRolId().getNombre());
                                    }
                                %> 
                                <i class="fa fa-user fa-fw" ></i> <i class="fa fa-caret-down"></i></p>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="modificarusuario.jsp"><i class="fa fa-user fa-fw"></i> Datos Personales</a>
                            </li>
                            <li><a href="cambiarclave.jsp"><i class="fa fa-gear fa-fw"></i> Cambiar contraseña</a>
                            </li>
                            <li class="divider"></li>
                            <li><a href="logout.jsp"><i class="fa fa-sign-out fa-fw"></i> Cerrar Sesión</a>
                            </li>
                        </ul>
                        <!-- /.dropdown-user -->
                    </li>
                    <!-- /.dropdown -->
                </ul>
                <!-- /.navbar-top-links -->

                <div class="navbar-default sidebar" role="navigation">
                    <div class="sidebar-nav navbar-collapse">
                        <ul class="nav" id="side-menu">
                            <li class="sidebar-search">
                                <div class="input-group custom-search-form">
                                    <button class="btn btn-default" type="button">
                                        <%
                                                                                                  if (userdto.getGenero() == 1) {%>
                                        <i style="width:50px; height:50px; margin-left: 5%;"><img src="../img/iconos/mujer.png" alt="Usuario: <%if (userdto != null) {
                                                                                                      out.print(userdto.getNombres() + " " + userdto.getApellidos());
                                                                                                  }%>" 
                                                                                                  title="Eres: <%if (userdto != null) {
                                                                                                      out.print(userdto.getNombres() + " " + userdto.getApellidos());
                                                                                                  }%>"></i>
                                            <% } else {%>
                                        <i style="width:50px; height:50px;"><img src="../img/iconos/hombre.png" alt="Usuario: <%if (userdto != null) {
                                                                                         out.print(userdto.getNombres() + " " + userdto.getApellidos());
                                                                                     }%>" 
                                                                                 title="Eres: <%if (userdto != null) {
                                                                                         out.print(userdto.getNombres() + " " + userdto.getApellidos());
                                                                                     }%>"></i>
                                            <%}
                                            %>
                                    </button>
                                    </span>
                                </div>
                                <!-- /input-group -->
                            </li>
                        </ul>
                        <ul style="margin-left: 1,5%;">
                            <li>
                                <%
                                    out.print(menu);
                                %>
                            </li>
                        </ul>
                    </div>
                    <!-- /.sidebar-collapse -->
                </div>
                <!-- /.navbar-static-side -->
            </nav>

            <div id="page-wrapper">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Productos</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <div class="row" >
                    <div class="col-lg-12">
                        <div style="margin-top: 0%;">
                            <%
                                String tipo = "";
                                String mensaje = "";
                                if (request.getParameter("msg") != null && request.getParameter("tipo") != null) {
                                    tipo = request.getParameter("tipo");
                                    mensaje = request.getParameter("msg");
                            %>
                            <jsp:include page="msg.jsp" flush="true">
                                <jsp:param name="tipo" value="<%=tipo%>" /> 
                                <jsp:param name="sal" value="<%=mensaje%>" /> 
                            </jsp:include>

                            <%}%>
                        </div>
                        <div class="panel panel-success">
                            <div class="panel-heading">
                                <i class="fa fa-calendar-o fa-fw"></i>Mis Productos
                            </div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class="dataTable_wrapper">
                                    <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                        <thead>
                                            <tr>
                                                <th>Cod</th>        
                                                <th>Categoría</th>
                                                <th>Nombre producto</th>
                                                <th>Unidad</th>
                                                <th>Ofertar</th>
                                                <th>Eliminar</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for (ProductosAsociadosUsuariosDTO pro : productos) {
                                            %>
                                            <tr>
                                                <td><%=pro.getIdProductosAsociadosUsuarios()%></td>
                                                <td><%=pro.getProducto().getCategoriaId().getNombre()%></td>
                                                <td><%=pro.getProducto().getNombre()%></td>
                                                <!--<td>//pro.getEstado()%></td>-->
                                                <td>Kilogramos</td>
                                                <td><%
                                                    if (pro.getEstado() == 1) {%>
                                                    <a href="../pages/registrarmioferta.jsp?idProAsoOfertar=<%=pro.getIdProductosAsociadosUsuarios()%>">
                                                        <img src="../img/prueba/ofertar.png" alt="Ofertar producto" title="Ofertar producto"</a></td>
                                                        <%} else if (pro.getEstado() == 2) {%>
                                                    <img src="../img/prueba/ofertaInhabilitada.png" alt="No puede ofertar este producto, 
                                                         ya encuentra con una publicación de ese mismo producto." title="No puede ofertar este producto, ya hizo una publicación de ese mismo producto."</a></td>
                                                    <%} else {
                                                            out.print("No se que paso");
                                                        }
                                                    %>


                                                    <td>
                                                        <%
                                                            if (pro.getEstado() == 1) {%>
                                                    <a href="../co?idProAso=<%=pro.getIdProductosAsociadosUsuarios()%>"><img src="../img/prueba/deleteNew.png" 
                                                                                                                     alt="Deshabilitar el producto: <%=pro.getIdProductosAsociadosUsuarios()%>" 
                                                                                                                     title="Deshabilitar el producto: <%=pro.getProducto().getNombre()%>" 
                                                                                                                     onclick="return confirmar();">
                                                <%} else if (pro.getEstado() == 2) {%>
                                                <img src="../img/prueba/noDelete.png" alt="El producto: <%=pro.getIdProductosAsociadosUsuarios()%> no se puede deshabilitar porque tiene ofertas asociadas." 
                                                     title="El producto: <%=pro.getProducto().getNombre()%> no se puede deshabilitar porque tiene ofertas asociadas." 
                                                     disabled="true">
                                                <%} else {
                                                    }%>
                                                </td>
                                                </tr>
                                                <%
                                                        }
                                                    } else {
                                                        out.print("No se encuentran registros");
                                                    }

                                                %>
                                                </tbody>
                                                </table>
                                                </div>
                                                <div style="cursor: pointer; text-align:center;" id="pageNavPosition"></div>
                                                </div>        

                                                </div>

                                                <!-- /.list-group -->
                                                </div>
                                                <!-- /.panel-body -->
                                                </div>
                                                <!-- /.panel -->
                                                </div>
                                                <!-- /.col-lg-4 -->
                                                </div>

                                                <%             } else {
                                                        response.sendRedirect("../index.jsp");
                                                    }
                                                %>
                                                <div class="panel-footer">
                                                    <p style="color: green"><a style="color: green" id="sena" href="http://www.sena.edu.co/">Sena</a>          |   Stefhany Alfonso Rincón                |
                                                        Última actualización: 
                                                        <%
                                                            FacadeConsultas facadeConsults = new FacadeConsultas();
                                                            out.println(facadeConsults.consultarFecha());%>         |  <a href="http://www.minagricultura.gov.co/" style="color:green;">MinAgricultura</a></p>
                                                </div>

                                                <!-- /#page-wrapper -->

                                                <!-- jQuery -->
                                                <script src="../bower_components/jquery/dist/jquery.min.js"></script>
                                                <script src="../bower_components/jquery/dist/jquery.js"></script>
                                                <script src="../bower_components/jquery/dist/jquery.validate.js"></script>
                                                <script src="../bower_components/jquery/dist/additional-methods.js"></script>
                                                <script src="../bower_components/jquery/dist/paging.js" type="text/javascript"></script>

                                                <!-- Bootstrap Core JavaScript -->
                                                <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

                                                <!-- Metis Menu Plugin JavaScript -->
                                                <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

                                                <!-- Morris Charts JavaScript -->
                                                <script src="../bower_components/raphael/raphael-min.js"></script>
                                                <script src="../bower_components/morrisjs/morris.min.js"></script>
                                                <script src="../js/morris-data.js"></script>

                                                <!-- Custom Theme JavaScript -->
                                                <script src="../dist/js/sb-admin-2.js"></script>

                                                <script type="text/javascript"> Cufon.now();</script>

                                                <script type="text/javascript">
                                                    <!--
                                                    var pager = new Pager('dataTables-example', 5);
                                                    pager.init();
                                                    pager.showPageNav('pager', 'pageNavPosition');
                                                    pager.showPage(1);
                                                    //--></script>
                                                </body>

                                                </html>
