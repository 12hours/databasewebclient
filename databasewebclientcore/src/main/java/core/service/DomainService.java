package core.service;

import core.dao.DiagnosisRepository;
import core.domain.Diagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DomainService {

    @Autowired
    DiagnosisRepository diagnosisRepository;

    public Diagnosis findDiagnosisById(long id){

        return diagnosisRepository.findOne(id);
    }

}
