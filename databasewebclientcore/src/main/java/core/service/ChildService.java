package core.service;

import core.domain.Child;
import core.domain.Survey;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

public interface ChildService {

    Child getChildById(long id);

    void saveChild(Child child);
}
