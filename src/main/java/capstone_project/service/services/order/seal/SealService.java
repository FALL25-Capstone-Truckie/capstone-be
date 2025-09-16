package capstone_project.service.services.order.seal;

import capstone_project.dtos.response.order.seal.GetSealResponse;

public interface SealService {
    GetSealResponse createSeal(String description);

    GetSealResponse cancelSeal(String sealId);

}
