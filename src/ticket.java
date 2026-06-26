
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class ticket {
   public static void generarTicket(Auto auto, String urlDrive) {
        float anchoTicket = 226f; 
        float altoTicket = 650f;
        
        Rectangle tamañoTicket = new Rectangle(anchoTicket, altoTicket);
        Document documento = new Document(tamañoTicket, 10, 10, 10, 10);

        try {
            String nombreArchivo = "Ticket_" + auto.getPlacas() + ".pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font fuenteSubtitulo = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
            Font fuenteContenido = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
            Font fuenteNegrita = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

            try {
                Image logo = Image.getInstance("src/imagenes/logocar.png");
                logo.setAlignment(Element.ALIGN_CENTER);
              
                logo.scaleToFit(200,200); 
                
                
                logo.setSpacingAfter(20f); 
                
                documento.add(logo);
            } catch (Exception e) {
              
            }

            
            Paragraph titulo = new Paragraph("LAVA AUTOS S.A.\n", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setLeading(14f);
            documento.add(titulo);
            
            Paragraph separador = new Paragraph("--------------------------------------------------", fuenteContenido);
            separador.setAlignment(Element.ALIGN_CENTER);
            documento.add(separador);

            String cliente = (auto.getNombre() != null ? auto.getNombre() : "") + " " + 
                             (auto.getApellidoPaterno() != null ? auto.getApellidoPaterno() : "") + " " + 
                             (auto.getApellidoMaterno() != null ? auto.getApellidoMaterno() : "");
            String telefono = (auto.getTelefono() != null ? auto.getTelefono() : "S/N");
            String tipoVehiculo = (auto.getTipoVehiculo() != null ? auto.getTipoVehiculo() : "No especificado");
            String horaEgreso = (auto.getHoraEgreso() != null && !auto.getHoraEgreso().isEmpty() ? auto.getHoraEgreso() : "En proceso/No registrada");
            String tipoPago = (auto.getTipoPago() != null ? auto.getTipoPago() : "Pendiente");

            Paragraph pCliente = new Paragraph("CLIENTE: " + cliente.trim(), fuenteContenido);
            pCliente.setLeading(11f); 
            documento.add(pCliente);

            Paragraph pTel = new Paragraph("TELÉFONO: " + telefono, fuenteContenido);
            pTel.setLeading(11f);
            documento.add(pTel);
            
            documento.add(separador);
            
            Paragraph pPlacas = new Paragraph("PLACAS: " + auto.getPlacas(), fuenteNegrita);
            pPlacas.setLeading(11f);
            documento.add(pPlacas);

            Paragraph pVehiculo = new Paragraph("VEHÍCULO: " + tipoVehiculo, fuenteContenido);
            pVehiculo.setLeading(11f);
            documento.add(pVehiculo);

            Paragraph pModelo = new Paragraph("MODELO: " + auto.getModelo() + " | COLOR: " + auto.getColor(), fuenteContenido);
            pModelo.setLeading(11f);
            documento.add(pModelo);
            
            documento.add(separador);

           
            Paragraph pFIn = new Paragraph("FECHA INGRESO: " + auto.getFechaIngreso(), fuenteContenido);
            pFIn.setLeading(11f);
            documento.add(pFIn);

            Paragraph pHIn = new Paragraph("HORA INGRESO: " + auto.getHoraIngreso(), fuenteContenido);
            pHIn.setLeading(11f);
            documento.add(pHIn);

            Paragraph pHEg = new Paragraph("HORA EGRESO: " + horaEgreso, fuenteContenido);
            pHEg.setLeading(11f);
            documento.add(pHEg);
            
            documento.add(separador);

            Paragraph pServ = new Paragraph("SERVICIO: " + auto.getTipoServicio(), fuenteContenido);
            pServ.setLeading(11f);
            documento.add(pServ);

            Paragraph pPago = new Paragraph("PAGO: " + tipoPago, fuenteContenido);
            pPago.setLeading(11f);
            documento.add(pPago);

            Paragraph pObs = new Paragraph("OBSERVACIONES: " + (auto.getObservaciones() != null ? auto.getObservaciones() : "Ninguna"), fuenteContenido);
            pObs.setLeading(11f);
            documento.add(pObs);
            
            documento.add(separador);

            Paragraph total = new Paragraph("TOTAL A PAGAR: $" + auto.getCosto(), fuenteTitulo);
            total.setAlignment(Element.ALIGN_CENTER);
            total.setLeading(14f);
            documento.add(total);
            documento.add(separador);

            
            Paragraph txtQr = new Paragraph("MAS INFORMACION EN:", fuenteSubtitulo);
            txtQr.setAlignment(Element.ALIGN_CENTER);
            txtQr.setLeading(11f);
            txtQr.setSpacingAfter(5f);
            documento.add(txtQr);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(urlDrive, BarcodeFormat.QR_CODE, 100, 100);
            
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] bytesQr = pngOutputStream.toByteArray();

            Image imagenQr = Image.getInstance(bytesQr);
            imagenQr.setAlignment(Element.ALIGN_CENTER);
            documento.add(imagenQr);

            documento.close();
            JOptionPane.showMessageDialog(null, "Ticket PDF generado correctamente.");

        } catch (DocumentException | IOException | com.google.zxing.WriterException e) {
            JOptionPane.showMessageDialog(null, "Error al generar ticket: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
