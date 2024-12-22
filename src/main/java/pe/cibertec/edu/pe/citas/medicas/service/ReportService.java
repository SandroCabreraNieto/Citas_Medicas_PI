package pe.cibertec.edu.pe.citas.medicas.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pe.cibertec.edu.pe.citas.medicas.interfaces.CitasRepository;
import pe.cibertec.edu.pe.citas.medicas.models.Citas;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private CitasRepository citaRepository;

    // Este método genera el JasperPrint con los datos
    public JasperPrint generateJasperPrint(String dniPaciente, Map<String, Object> parameters) throws JRException {
        File jrxmlFile = null;

        try {
            // Cargar el archivo JRXML
            jrxmlFile = new ClassPathResource("reports/reporteCitas.jrxml").getFile();
        } catch (IOException e) {
            throw new JRException("Error al cargar el archivo JRXML", e);
        }

        // Compilar el archivo JRXML
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile.getAbsolutePath());

        // Obtener los datos para el reporte
        List<Citas> citas = citaRepository.findByPacienteDni(dniPaciente);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(citas);

        // Rellenar el reporte con los datos
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }
}
