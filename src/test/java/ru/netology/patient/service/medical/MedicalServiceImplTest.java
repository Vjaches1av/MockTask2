package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

class MedicalServiceImplTest {

    @Test
    void checkBloodPressure_notNorm() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("c062b1bf-77d4-49ff-9137-a25ad8b4b31e"))
                .thenReturn(new PatientInfo("c062b1bf-77d4-49ff-9137-a25ad8b4b31e",
                        "Иван", "Петров",
                        LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals("Warning, patient with id: c062b1bf-77d4-49ff-9137-a25ad8b4b31e, need help", arg);
            return null;
        }).when(sendAlertService).send(any(String.class));

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure("c062b1bf-77d4-49ff-9137-a25ad8b4b31e", new BloodPressure());
    }

    @Test
    void checkBloodPressure_norm() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("c062b1bf-77d4-49ff-9137-a25ad8b4b31e"))
                .thenReturn(new PatientInfo("c062b1bf-77d4-49ff-9137-a25ad8b4b31e",
                        "Иван", "Петров",
                        LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertNull(arg);
            return null;
        }).when(sendAlertService).send(any(String.class));

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure("c062b1bf-77d4-49ff-9137-a25ad8b4b31e", new BloodPressure(120, 80));
    }

    @Test
    void checkTemperature_notNorm() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("c062b1bf-77d4-49ff-9137-a25ad8b4b31e"))
                .thenReturn(new PatientInfo("c062b1bf-77d4-49ff-9137-a25ad8b4b31e",
                        "Иван", "Петров",
                        LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertEquals("Warning, patient with id: c062b1bf-77d4-49ff-9137-a25ad8b4b31e, need help", arg);
            return null;
        }).when(sendAlertService).send(any(String.class));

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature("c062b1bf-77d4-49ff-9137-a25ad8b4b31e", new BigDecimal("38"));
    }

    @Test
    void checkTemperature_norm_version_1() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("c062b1bf-77d4-49ff-9137-a25ad8b4b31e"))
                .thenReturn(new PatientInfo("c062b1bf-77d4-49ff-9137-a25ad8b4b31e",
                        "Иван", "Петров",
                        LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            assertNull(arg);
            return null;
        }).when(sendAlertService).send(any(String.class));

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature("c062b1bf-77d4-49ff-9137-a25ad8b4b31e", new BigDecimal("36.65"));
    }

    @Test
    void checkTemperature_norm_version_2() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("c062b1bf-77d4-49ff-9137-a25ad8b4b31e"))
                .thenReturn(new PatientInfo("c062b1bf-77d4-49ff-9137-a25ad8b4b31e",
                        "Иван", "Петров",
                        LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.verify(sendAlertService, Mockito.never()).send(any(String.class));

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature("c062b1bf-77d4-49ff-9137-a25ad8b4b31e", new BigDecimal("36.65"));
    }
}
