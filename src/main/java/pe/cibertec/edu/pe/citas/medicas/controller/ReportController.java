package pe.cibertec.edu.pe.citas.medicas.controller;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.cibertec.edu.pe.citas.medicas.service.ReportService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/Citas/descargarReporte")
    public ResponseEntity<byte[]> downloadReport(@RequestParam(value = "dniPaciente") String dniPaciente) throws Exception {
        // Parámetros del reporte
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dniPaciente", dniPaciente);

        // Obtener los datos para el reporte (debería ser una lista de citas o los datos correspondientes)
        JasperPrint jasperPrint = reportService.generateJasperPrint(dniPaciente, parameters);

        // Verificar si el JasperPrint fue generado correctamente
        if (jasperPrint == null) {
            return ResponseEntity.status(500).body("Error: No se pudieron generar los datos del reporte.".getBytes());
        }

        // Crear un ByteArrayOutputStream para almacenar el PDF generado en memoria
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Exportar el reporte a PDF y escribirlo en el flujo de salida
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        // Obtener el contenido del ByteArrayOutputStream como un array de bytes
        byte[] pdfBytes = outputStream.toByteArray();

        // Retornar el archivo PDF como respuesta HTTP
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_citas_" + dniPaciente + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}