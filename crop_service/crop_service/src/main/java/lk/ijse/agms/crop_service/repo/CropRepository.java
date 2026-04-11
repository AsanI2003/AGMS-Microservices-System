package lk.ijse.agms.crop_service.repo;

import lk.ijse.agms.crop_service.entity.Crop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropRepository extends MongoRepository<Crop, String> {

}
