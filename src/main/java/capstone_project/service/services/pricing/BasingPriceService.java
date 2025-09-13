package capstone_project.service.services.pricing;


import capstone_project.dtos.request.pricing.BasingPriceRequest;
import capstone_project.dtos.request.pricing.UpdateBasingPriceRequest;
import capstone_project.dtos.response.pricing.BasingPriceResponse;
import capstone_project.dtos.response.pricing.GetBasingPriceResponse;

import java.util.List;
import java.util.UUID;

public interface BasingPriceService {
    List<GetBasingPriceResponse> getBasingPrices();

    GetBasingPriceResponse getBasingPriceById(UUID id);

    BasingPriceResponse createBasingPrice(BasingPriceRequest basingPriceRequest);

    BasingPriceResponse updateBasingPrice(UUID id, UpdateBasingPriceRequest basingPriceRequest);

    void deleteBasingPrice(UUID id);
}
