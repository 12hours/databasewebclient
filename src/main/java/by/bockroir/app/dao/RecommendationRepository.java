package by.bockroir.app.dao;

import by.bockroir.app.domain.Recommendation;
import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepository extends CrudRepository<Recommendation, Long>{
}
