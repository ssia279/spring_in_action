package sia.tacocloud.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import sia.tacocloud.models.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, String> {
}
