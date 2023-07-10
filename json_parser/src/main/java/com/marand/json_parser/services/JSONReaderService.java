package com.marand.json_parser.services;

// import com.google.common.base.Optional;
import com.marand.json_parser.models.Doctor;
import com.marand.json_parser.models.Patient;
import com.marand.json_parser.models.DocumentReport;
import com.marand.json_parser.repositories.DoctorRepository;
import com.marand.json_parser.repositories.PatientRepository;
import com.marand.json_parser.repositories.DocumentReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class JSONReaderService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DocumentReportRepository documentReportRepository;

    public JSONReaderService(PatientRepository patientRepository, DoctorRepository doctorRepository, DocumentReportRepository documentReportRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.documentReportRepository = documentReportRepository;
    }

    public Doctor parse(MultipartFile file) throws IOException {

        // get execution start time
        Timestamp ts = new Timestamp(System.currentTimeMillis());  
        Date time_now = new Date(ts.getTime());  
        String execution_time = time_now.toString();
        System.out.println(time_now.toString());

        // create report object
        DocumentReport report = new DocumentReport();

        //create JsonReader object
        InputStream fis = file.getInputStream();
        JsonReader jsonReader = Json.createReader(fis);

        //get JsonObject from JsonReader
        JsonObject jsonObject = jsonReader.readObject();

        //close IO resource and JsonReader
        jsonReader.close();
        fis.close();

        // error detections list
        List<String> errors = new ArrayList<>();

        //retrieve data from JsonObject and create Doctor bean
        Doctor doctor = new Doctor();            

        // doctor.setDoctorId(jsonObject.getString("id"));
        // String docId = setAttribute(jsonObject, "id", "", errors);
        // doctor.setDoctorId(docId);
        doctor.setDoctorId(setAttribute(jsonObject, "id", "", errors));
        doctor.setDepartment(setAttribute(jsonObject, "department", "", errors));

        //reading inner object from json object
        JsonArray jsonArray = jsonObject.getJsonArray("patients");
        List<Patient> patients = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject innerJsonObject = jsonArray.getJsonObject(i);
            Patient patient = new Patient();

            patient.setPatientId(setAttribute(innerJsonObject, "id", "", errors));
            patient.setFirstName(setAttribute(innerJsonObject, "first_name", "", errors));
            patient.setLastName(setAttribute(innerJsonObject, "last_name", "", errors));

            // check for diseases field
            boolean exists = innerJsonObject.containsKey("diseases");
            if (!exists) {
                patient.setDisease("not specified");
                errors.add("diseases field does not exist");
            } else {
                JsonArray jsonArrayDiseases = innerJsonObject.getJsonArray("diseases");
                List<String> diseases = new ArrayList<>();
                for (JsonValue disease : jsonArrayDiseases) {
                    diseases.add(disease.toString());
                }
                patient.setDisease(diseases.toString());
            }

            patient.setDoctor(doctor);
            patients.add(patient);
        }

        report.setExecution_time(execution_time);
        report.setErrors(errors.toString());
        report.setDoctor(doctor);
        doctor.setPatients(patients);
        doctorRepository.save(doctor);
        patientRepository.saveAll(patients);
        documentReportRepository.save(report);

        System.out.println(errors.toString());

        return doctor;

    }


    private String setAttribute(JsonObject jsonObject, String attribute, String defaultValue, List<String> errors) {

        try {
            String att = jsonObject.getString(attribute.toString());
            if (att.isEmpty()) {
                errors.add(attribute + " field is empty");
                return "empty";
            } else {
                return att;
            }
        } catch (Exception e) {
            errors.add(attribute + " field does not exist");
            return "nonexistent";
        }
    }

}
