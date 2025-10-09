package capstone_project.service.services.order.seal;

import capstone_project.dtos.response.order.seal.GetSealResponse;

import java.util.List;

public interface SealService {
    GetSealResponse createSeal(String description);

    GetSealResponse createSealWithCode(String sealCode, String description);

    List<GetSealResponse> getAllSeals();

}
