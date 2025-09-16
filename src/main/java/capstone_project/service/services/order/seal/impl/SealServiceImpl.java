package capstone_project.service.services.order.seal.impl;

import capstone_project.common.enums.CommonStatusEnum;
import capstone_project.common.enums.ErrorEnum;
import capstone_project.common.exceptions.dto.NotFoundException;
import capstone_project.dtos.response.order.GetOrderForCustomerResponse;
import capstone_project.dtos.response.order.seal.GetSealResponse;
import capstone_project.entity.order.order.SealEntity;
import capstone_project.repository.entityServices.order.order.SealEntityService;
import capstone_project.service.mapper.order.SealMapper;
import capstone_project.service.services.order.seal.SealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.yaml.snakeyaml.nodes.NodeId.sequence;

@Service
@RequiredArgsConstructor
@Slf4j
public class SealServiceImpl implements SealService {
    private final SealEntityService  sealEntityService;
    private final SealMapper sealMapper;

    @Override
    public GetSealResponse createSeal(String description) {
        SealEntity sealEntity = SealEntity.builder()
                .sealCode(generateUniqueSealCode())
                .description(description)
                .status(CommonStatusEnum.ACTIVE.name())
                .build();
        return sealMapper.toGetSealResponse(sealEntity);
    }

    @Override
    public GetSealResponse cancelSeal(String sealId) {
        Optional<SealEntity> sealEntity = sealEntityService.findEntityById(UUID.fromString(sealId));
        if(sealEntity.isEmpty()){
            throw new NotFoundException(ErrorEnum.NOT_FOUND.getMessage() + "Seal không tìm thấy với seal ID này: "+sealId,ErrorEnum.NOT_FOUND.getErrorCode());
        }
        sealEntity.get().setStatus(CommonStatusEnum.INACTIVE.name());
        return sealMapper.toGetSealResponse(sealEntity.get());
    }

    private String generateUniqueSealCode() {
        int maxAttempts = 10;
        for (int i = 0; i < maxAttempts; i++) {
            String code = buildSealCode();
            if (sealEntityService.findBySealCode(code) == null) {
                return code;
            }
        }
        throw new IllegalStateException("Could not generate unique seal code after " + maxAttempts + " attempts");
    }

    private String buildSealCode() {
        String year = String.valueOf(Year.now().getValue()).substring(2); // 25
        int random = ThreadLocalRandom.current().nextInt(0, 999999);
        return String.format("SEA%s-%06d", year, random);
    }


}
