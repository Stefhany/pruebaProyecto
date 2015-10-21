/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daos.DespachosPedidosDAO;
import daos.PedidoSobreOfertaDAO;
import dtos.DespachosPedidosDTO;
import dtos.SolicitudDistribuidorDTO;
import facade.FacadeAportesProductores;
import facade.FacadeConsultas;
import facade.FacadeDespachosPedidos;
import facade.FacadePedidoSobreOferta;
import facade.FacadeSolicitudDistribuidor;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mail.Mail;

/**
 *
 * @author Mona
 */
public class ControllerOrders extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String salida = "";
        FacadeSolicitudDistribuidor facadeRequestDistributor = new FacadeSolicitudDistribuidor();
        FacadePedidoSobreOferta facadePedidoSobreOferta = new FacadePedidoSobreOferta();
        SolicitudDistribuidorDTO solicitud = new SolicitudDistribuidorDTO();
        FacadeDespachosPedidos facadeDespachoPedidos = new FacadeDespachosPedidos();
        FacadeAportesProductores facadeAporte = new FacadeAportesProductores();
        try {
            if (request.getParameter("btnSolicitar") != null && request.getParameter("solicitar").equals("solicitar")) {
                String nombreDistribuidor = request.getParameter("txtNombreDistribuidor").trim();
                int telefonoDistribuidor = Integer.parseInt(request.getParameter("txtTelefonoDistribuidor").trim());
                String direccionDistribuidor = request.getParameter("txtDireccionDistribuidor").trim();
                String nombreProducto = request.getParameter("txtNombreProducto").trim();
                int cantidadSolicitada = Integer.parseInt(request.getParameter("txtCantidadSolicitada").trim());
                String fechaSolicitud = request.getParameter("txtFechaSolicitud").trim();
                String correoProductor = request.getParameter("txtCorreoProductor").trim();
                String correoDistribuidor = request.getParameter("txtCorreoDistribuidor").trim();
                String nombreProductor = request.getParameter("txtNombreProductor").trim();
                int telefonoProductor = Integer.parseInt(request.getParameter("txtTelefonoProductor").trim());
                PedidoSobreOfertaDAO p = new PedidoSobreOfertaDAO();
                int contar = facadePedidoSobreOferta.calcularCantidad(Integer.parseInt(request.getParameter("txtIdOferta")));
                int canasta = contar - cantidadSolicitada;
                Date fechaSolicitada = Date.valueOf(request.getParameter("txtFechaSolicitud").trim());
                Date fechaRegistrada = Date.valueOf(request.getParameter("txtFecha").trim());
                FacadeConsultas facadeConsults = new FacadeConsultas();
                int fechasHabiles = facadeConsults.diferenciasDeFechas(fechaSolicitada, fechaRegistrada);
                if (fechasHabiles > 0) {
                    if (contar >= cantidadSolicitada) {
                        FacadePedidoSobreOferta facadeOrderOffer = new FacadePedidoSobreOferta();
                        String salidaDos = facadeOrderOffer.registrarPedidoSobreOferta(Integer.parseInt(request.getParameter("txtCantidadSolicitada").trim()),
                                Integer.parseInt(request.getParameter("txtIdOferta").trim()), request.getParameter("txtFechaSolicitud").trim());
                        if (salidaDos.equals("ok")) {
                            Mail.sendMail("Noticia de oferta", "Te informamos que el distribuidor " + nombreDistribuidor
                                    + ", le solicito " + cantidadSolicitada + " kilogramos de " + nombreProducto
                                    + ", para la fecha " + fechaSolicitud + ". <br><br>"
                                    + " Si desea comunicarse con el cliente lo puede hacer directamente"
                                    + " a su número teléfonico " + telefonoDistribuidor + " o a su "
                                    + " correo electronico " + correoDistribuidor + ".<br><br>"
                                    + " Recuerde que la mercancía se debe entregar en la " + direccionDistribuidor + "y su "
                                    + " máxima entrega es para el " + fechaSolicitud + "<br><br>"
                                    + " Información: Aún tiene publicado " + canasta + " kilogramos de " + nombreProducto + ".<br><br><br>"
                                    + " Gracias por pertenecer a SIGAA <br>"
                                    + " Persona encargada: Stefhany Alfonso Rincón <br>"
                                    + " Líneas de atención: 3213018539", correoProductor);

                            Mail.sendMail("Confirmación de pedido", "Recuerde que ha solicitado " + cantidadSolicitada + ""
                                    + " kilogramos de " + nombreProducto + ", del usuario " + nombreProductor + ".<br>"
                                    + " Si desea concretar las condiciones del pedido se puede comunicar a su teléfono "
                                    + telefonoProductor + " o al correo electronico " + correoProductor + ".<br><br>"
                                    + "Información: Recuerce tener presentes los términos y condiciones del pedido.<br><br>"
                                    + "Gracias por pertenecer a SIGAA <br>"
                                    + " Persona encargada: Stefhany Alfonso Rincón <br>"
                                    + "Líneas de atención: 3213018539", correoDistribuidor);
                            response.sendRedirect("pages/listarofertasactuales.jsp?msgSalida= <strong>Su solicitud ha sido registrada. Se le enviara"
                                    + " la notificación al productor.</Strong>");
                        }
                    } else {
                        String msj = "No puede ingresar una cantidad mayor a la solicitada";
                        response.sendRedirect("pages/listarofertasactuales.jsp?msgSalida = <strong>No puede ingresar una cantidad mayor a la solicitada</Strong>");
                    }
                } else {
                    String mensaje = "La fecha que selecciono no esta disponible para el termino de esta oferta.";
                    response.sendRedirect("pages/listarofertasactuales.jsp?msgSalida = <strong>" + mensaje + "</Strong>");
                }

            } else if (request.getParameter("btnSolicitarAsociacion") != null && request.getParameter("solicitarAsociacion") != null) {
                out.print("ok");
                solicitud.setCantidadSolicitada(Integer.parseInt(request.getParameter("txtCantidad").trim()));
                solicitud.setFechaSolicitud(request.getParameter("txtFechaSolicitud").trim());
                solicitud.setProductoId(Integer.parseInt(request.getParameter("subcategoria").trim()));
                solicitud.setDistribuidorId(Integer.parseInt(request.getParameter("txtId").trim()));
                String correo = request.getParameter("txtCorreo");
                String nombre = request.getParameter("txtUser");
                salida = facadeRequestDistributor.insertarSolicitudDistribuidor(solicitud);

                if (salida.equals("ok")) {
                    String mensaje = "El pedido a la asociación a sido enviado sactisfactoriamente. Se le enviara un correo de confirmación.";
                    response.sendRedirect("pages/realizarpedidoasociacion.jsp?msgSalida=<strong>" + mensaje + "</Strong>");
                    Mail.sendMail("Confirmación de pedido", ""
                            + " <h1>Confirmación</h1><br>"
                            + " Señor, ra: " + nombre + ""
                            + " Su pedido a sido solicitado satisfactoriamente. ", correo);
                }
            } else if (request.getParameter("btnGenerarPedido") != null && request.getParameter("generarPedido") != null) {

                String fechaEntrega = request.getParameter("txtFechaEntrega").trim();
                int idSolicitud = Integer.parseInt(request.getParameter("txtIdSolicitud"));
                solicitud.setIdSolicitud(Integer.parseInt(request.getParameter("txtIdSolicitud").trim()));
                solicitud.setFechaEntregaInterna(request.getParameter("txtFechaEntrega").trim());

                int fechaEntregaBien = facadeDespachoPedidos.validarFecha(fechaEntrega);

                if (fechaEntregaBien == 1) {
                    int fechaAnticipacion = facadeDespachoPedidos.validarFechaDespacho(fechaEntrega, idSolicitud);
                    if (fechaAnticipacion == 1) {
                        salida = facadeRequestDistributor.modificarSolicitudDistribuidor(solicitud);
                        if (salida.equals("ok")) {
                            facadeDespachoPedidos.cambiarEstadoAProductores(idSolicitud);
                            response.sendRedirect("pages/listarsolicitudesasociaciones.jsp?msgSalida=<strong>El pedido ha sido enviado a los productores.</Strong>");
                        } else {
                            response.sendRedirect("pages/listarsolicitudesasociaciones.jsp?msgSalida=<strong>No se pudo realizar el cambio del estado.</Strong>");
                        }
                    } else {
                        response.sendRedirect("pages/listarsolicitudesasociaciones.jsp?msgSalida=<strong>La fecha de envío a los productores debe ser anterior a la de solicitud.</Strong>");
                    }
                } else {
                    response.sendRedirect("pages/listarsolicitudesasociaciones.jsp?msgSalida=<strong>Esta seleccionando una fecha anterior de la actual.</Strong>");
                }

            } else if (request.getParameter("btnAplicarSolicitud") != null && request.getParameter("aplicarSolicitud") != null) {

                int cantidadPermitida = facadeAporte.calcularCantidad(Integer.parseInt(request.getParameter("txtIdSolicitud").trim()));
                int cantidadAportar = Integer.parseInt(request.getParameter("txtCantidadAportar").trim());
                int idSolicitud = Integer.parseInt(request.getParameter("txtIdSolicitud").trim());

                if (cantidadPermitida >= cantidadAportar || cantidadPermitida > cantidadAportar) {
                    String fechaEntrega = request.getParameter("txtFechaEntrega").trim();
                    int fechaEntregaBien = facadeDespachoPedidos.validarFecha(fechaEntrega);

                    if (fechaEntregaBien == 1) {
                        int fechaAnticipacion = facadeDespachoPedidos.validarFechaAporte(fechaEntrega, idSolicitud);
                        if (fechaAnticipacion == 1) {

                            FacadeAportesProductores facadeContributeProducer = new FacadeAportesProductores();
                            salida = facadeContributeProducer.participarASolicitudAsociacion(request.getParameter("txtFechaEntrega").trim(),
                                    Integer.parseInt(request.getParameter("txtCantidadAportar").trim()),
                                    Integer.parseInt(request.getParameter("txtIdAso").trim()),
                                    Integer.parseInt(request.getParameter("txtIdSolicitud").trim()));

                            response.sendRedirect("pages/listarsolicitudesproductores.jsp?msgSalida= <strong>Su aporte ha sido registrado. Gracias por participar</Strong> ");
                        } else {
                            response.sendRedirect("pages/listarsolicitudesasociaciones.jsp?msgSalida=<strong>La fecha para el envío del producto debe ser anterior a la fecha estipulada por la asociación.</Strong>");
                        }
                    } else {
                        response.sendRedirect("pages/listarsolicitudesasociaciones.jsp?msgSalida=<strong>Esta seleccionando una fecha anterior de la actual.</Strong>");
                    }

                } else {
                    String msj = "No puede ingresar una cantidad mayor a la solicitada";
                    response.sendRedirect("paginas/listarsolicitudesproductores.jsp?msgSalida = <strong>" + msj + " </Strong>");
                }
            } else if (request.getParameter("btnDespacharPedido") != null && request.getParameter("despacharPedido") != null) {

                DespachosPedidosDTO dto = new DespachosPedidosDTO();
                String fechaEnvio = request.getParameter("txtFechaEnvio").trim();
                dto.setDireccionDespacho(request.getParameter("txtDireccion").trim());
                dto.setFechaDespacho(request.getParameter("txtFechaEnvio").trim());
                dto.setObservaciones(request.getParameter("txtObservacion").trim());
                dto.setSolicitudId(Integer.parseInt(request.getParameter("txtSolicitud").trim()));
                dto.setUsuariosId(Integer.parseInt(request.getParameter("txtUser").trim()));

                int idSolicitud = Integer.parseInt(request.getParameter("txtSolicitud").trim());

                int fechaEntregaBien = facadeDespachoPedidos.validarFecha(fechaEnvio);

                if (fechaEntregaBien == 1) {
                    int fechaAnticipacion = facadeDespachoPedidos.validarFechaDespacho(fechaEnvio, idSolicitud);
                    if (fechaAnticipacion == 1) {
                        FacadeDespachosPedidos facadeDispatchOrder = new FacadeDespachosPedidos();
                        salida = facadeDispatchOrder.insertarDespacho(dto);

                        if (salida.equals("ok")) {
                            facadeDespachoPedidos.cambiarEstadoSolicitud(idSolicitud);
                            response.sendRedirect("pages/listardespachos.jsp?msgSalida=<strong>El despacho ha sido realizado.</Strong>");
                        } else {
                            response.sendRedirect("pages/listardespachos.jsp?msgSalida=<strong>No se pudo realizar el cambio del estado.</Strong>");
                        }
                    } else {
                        response.sendRedirect("pages/listardespachos.jsp?msgSalida=<strong>La fecha de despacho debe ser anterior a la de solicitud.</Strong>");
                    }
                } else {
                    response.sendRedirect("pages/listardespachos.jsp?msgSalida=<strong>Esta seleccionando una fecha anterior de la actual.</Strong>");
                }

            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}