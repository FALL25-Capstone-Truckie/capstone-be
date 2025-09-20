package capstone_project.dtos.response.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Enhanced driver response with full information for staff
 */
public record StaffDriverResponse(
    UUID id,
    String fullName,
    String phoneNumber,
    String email,
    String imageUrl,
    Boolean gender,
    LocalDate dateOfBirth,
    String identityNumber,
    String driverLicenseNumber,
    String cardSerialNumber,
    String placeOfIssue,
    LocalDateTime dateOfIssue,
    LocalDateTime dateOfExpiry,
    String licenseClass,
    LocalDateTime dateOfPassing,
    String status,
    String address,
    LocalDateTime createdAt
) {}
